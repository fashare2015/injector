package com.example.io

import com.squareup.javapoet.JavaFile
import javax.lang.model.element.PackageElement

/**
 * Created by apple on 16-10-12.
 * JavaFile: file 路径相关配置
 */
class FileBuilder(private val mClazzBuilder: ClassBuilder): Builder<JavaFile> {

    override fun build(): JavaFile {
        return JavaFile.builder(
                mClazzBuilder.typeElement
                        .let { it.enclosingElement as PackageElement }  // 获取整个 PackageElement
                        .let { it.qualifiedName.toString() },           // 获取 packageName
                mClazzBuilder.build()    // 递归配置 mClazzBuilder
        ).build()
    }
}
