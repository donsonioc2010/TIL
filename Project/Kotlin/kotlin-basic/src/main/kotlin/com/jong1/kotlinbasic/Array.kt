package com.jong1.kotlinbasic

/**
 * 배열
 */
fun main() {
    val items  = arrayOf(1,2,3)
    println("배열은 ${items[0]} 처럼 쓴다")

    items[0] = 10
    print("수정은 이렇게 하며, 값은 ${items[0]} ")

    //OutofBound
//    items[4] = 5
}