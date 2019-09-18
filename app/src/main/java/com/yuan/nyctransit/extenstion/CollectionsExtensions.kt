package com.yuan.nyctransit.extenstion

fun IntArray.asString(): String =
    joinToString(separator = ", ", prefix = "[", postfix = "]")

fun <T> Array<T>.asString(): String =
    joinToString(separator = ", ", prefix = "[", postfix = "]")