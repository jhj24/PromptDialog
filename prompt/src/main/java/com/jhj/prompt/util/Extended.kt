package com.jhj.prompt.util

/**
 * 扩展工具
 * Created by jhj on 2018-3-27 0027.
 */


fun Int.notMinusOne(block: (Int) -> Unit) {
    if (this != -1) {
        block(this)
    }
}

fun Float.notMinusOne(block: (Float) -> Unit) {
    if (this != -1f) {
        block(this)
    }
}

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}

fun <T> List<T>.toChildArrayList(): ArrayList<ArrayList<T>> {
    val list = ArrayList<ArrayList<T>>()
    list.add(this.toArrayList())
    return list
}



