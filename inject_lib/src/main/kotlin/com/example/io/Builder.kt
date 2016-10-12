package com.example.io

/**
 * Created by apple on 16-10-11.
 * 用于 file, class, method ...各级别相关配置的分层
 */
interface Builder<R> {
    fun build(): R
}
