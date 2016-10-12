package com.example.io

import com.example.InjectFactory
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec

import java.util.ArrayList
import javax.lang.model.element.Modifier

import javax.lang.model.element.TypeElement

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-11
 * Time: 00:53
 * TypeSpec: class 生成相关配置
 */
class ClassBuilder @JvmOverloads constructor(
        val typeElement: TypeElement,
        private val mMethodBuilderList: List<MethodBuilder> = ArrayList<MethodBuilder>()
) : Builder<TypeSpec> {
    protected val TAG = this.javaClass.simpleName + ": "

    override fun build(): TypeSpec {
        println(TAG + TypeName.get(typeElement.asType()))

        return TypeSpec.classBuilder(typeElement.simpleName.toString() + InjectFactory.INJECT_POSTFIX) // 指定实现类的名字: InterfaceA$$Impl
                .addModifiers(*typeElement.modifiers
                        .filter { it != Modifier.ABSTRACT }             // 实现类不加 abstract 关键字
                        .toTypedArray())
                .addSuperinterface(TypeName.get(typeElement.asType()))  // 使得生成的 InterfaceA$$Impl 类 implements InterfaceA
                .addMethods(
                        // map: List<MethodBuilder> => List<MethodSpec>
                        mMethodBuilderList.map { it.build() }   // 递归配置 MethodBuilder
                ).build()
    }
}