package com.example.processor

import com.example.Get
import com.example.io.ClassBuilder
import com.example.io.MethodBuilder
import com.squareup.javapoet.JavaFile
import com.example.io.FileBuilder
import com.example.reflect.ReflectUtil
import com.squareup.javapoet.TypeName

import java.io.IOException
import java.util.HashMap

import javax.annotation.processing.Filer
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement


/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-11
 * Time: 22:35
 *
 */
class ProcessorUtil {
    companion object {  // static
        protected val TAG = "ProcessorUtil: "
        val CLASS_GET: Class<Get> = Get::class.java

        /**
         * 向 outputFiler 写入代码
         * @param roundEnv
         * @param outputFiler
         */
        fun writeTo(roundEnv: RoundEnvironment, outputFiler: Filer) {
            println(TAG + "------- begin write -------")
            println("\n\n")

            // 获取所有标有 @Get 的项: Set<Element>
            roundEnv.getElementsAnnotatedWith(CLASS_GET)
                    // 过滤条件: element 必须是 method, 且 element 定义在 interface 中
                    .filter (fun (it: Element): Boolean{
                        var valid = it is ExecutableElement
                                && ReflectUtil.isInterface(it.enclosingElement.asType())
                        if(!valid){
                            println("   Ignore ${it.enclosingElement.simpleName}.${it.simpleName}() !!! :\n" +
                                    "       Annotated element [ ${it.simpleName}() ] must be a method, \n" +
                                    "       and it's holder [ ${it.enclosingElement.simpleName} ] must be an interface !!!")
                        }
                        return valid
                    })
                    // 收集: 按照 method 所在 class 分组
                    .groupBy { it.enclosingElement }        // HashMap<class, List<method>>
                    // 转换: HashMap<class, List<method>> => List<ClassBuilder>
                    .map {
                        ClassBuilder(//
                                it.key as TypeElement, // Element => TypeElement
                                it.value.map { MethodBuilder(it as ExecutableElement) } // List<Element> => List<MethodBuilder>
                        )
                    }
                    // 转换: List<ClassBuilder> => List<FileBuilder>
                    .map { FileBuilder(it) }
                    // 写入: outputFiler
                    .forEach { it.build().writeTo(outputFiler) }

            println("\n\n")
            println(TAG + "------- end write -------")
        }
    }
}