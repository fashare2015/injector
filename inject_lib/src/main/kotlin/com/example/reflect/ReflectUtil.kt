package com.example.reflect

import com.example.Get
import javax.lang.model.element.ElementKind
import javax.lang.model.type.DeclaredType

import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeMirror

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-10
 * Time: 21:31
 *
 */
object ReflectUtil {
    fun getTypeMirror(annotation: Get): TypeMirror? {
        try {
            annotation.clazz // this should throw
        } catch (mte: MirroredTypeException) {
            return mte.typeMirror
        }
        return null // can this ever happen ??
    }

    fun isInterface(typeMirror: TypeMirror): Boolean {
        if (typeMirror !is DeclaredType) {
            return false
        }
        return typeMirror.asElement().getKind() === ElementKind.INTERFACE
    }
}