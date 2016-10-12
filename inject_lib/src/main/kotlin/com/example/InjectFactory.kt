package com.example

/**
 * newObservablesImpl
 */
class InjectFactory {
    companion object {
        var INJECT_POSTFIX = "$\$Impl"  // 实现类的后缀

        fun <T> newObservablesImpl(type: Class<T>): T? {
            val name = type.canonicalName + INJECT_POSTFIX
            var obj: T? = null
            try {
                obj = Class.forName(name).newInstance() as T
            } catch (e: ClassNotFoundException) {
            } catch (e: IllegalAccessException) {
            } catch (e: InstantiationException) {
            }
            return obj
        }
    }
}