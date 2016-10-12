package com.example.io

import com.example.Get
import com.example.reflect.ReflectUtil
import com.example.rxjava.ObservableUtil
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName

import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-11
 * Time: 00:53
 * MethodSpec: method 生成相关配置
 */
class MethodBuilder(executableElement: ExecutableElement) : Builder<MethodSpec> {
    protected val TAG = this.javaClass.simpleName + ": "

    private val mReturnType: TypeMirror
    private val mArguments: List<VariableElement>
    private val mMethodName: String
    private val mModifiers: Set<Modifier>

    private val mUrl: String
    private val mClazz: TypeMirror?

    init {
        this.mReturnType = executableElement.returnType
        this.mArguments = executableElement.parameters
        this.mMethodName = executableElement.simpleName.toString()
        this.mModifiers = executableElement.modifiers

        val annotationGet = executableElement.getAnnotation(Get::class.java)
        this.mUrl = annotationGet.url
        this.mClazz = ReflectUtil.getTypeMirror(annotationGet)
    }

    override fun build(): MethodSpec {
        println(TAG + mMethodName)

        return MethodSpec.methodBuilder(mMethodName)
                .addAnnotation(Override::class.java)
                .addModifiers(mModifiers.filter { it != Modifier.ABSTRACT })    // 实现方法不加 abstract 关键字
                .returns(TypeName.get(mReturnType))
                .addParameters(convertToParameterSpec(mArguments))
                .addStatement(
                        "return \$T.newInstance(new \$T<\$T>() {\n" +
                        "   @\$T\n" +
                        "   public \$T loadData() {\n" +
                        "       return HttpUtils.getNetWorkBitmap(\$S);\n" +
                        "   }\n" +
                        "})",
                        ObservableUtil::class.java, ObservableUtil.OnLoadData::class.java, mClazz,
                        Override::class.java,
                        mClazz,
                        mUrl)
                .build()
    }

    private fun convertToParameterSpec(arguments: List<VariableElement>): Iterable<ParameterSpec> {
        return arguments.map{
                ParameterSpec.builder(
                        TypeName.get(it.asType()),  // TypeMirror => TypeName
                        it.simpleName.toString(),
                        *it.modifiers.toTypedArray()
                ).build()
        }
    }
}