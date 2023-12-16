package com.jong1.kotlinbasic

import java.io.BufferedReader

/**
 * Try Catch에 대한 할용
 */
fun main() {
    try {
        val item = arrayOf(1,2,3)[9999]
    } catch (e : IndexOutOfBoundsException) {
        println("응 익셉션~")
    }finally {
        println("이제 가라")
    }

    /*
    Java의 Try With Resource문과 동일하나, 모든 객체가 다 use가 존재하지는 않는다
    BufferedReader(System.`in`).use {
        val name = it.readLine()
        println("Hello, $name")
    }
    */
}