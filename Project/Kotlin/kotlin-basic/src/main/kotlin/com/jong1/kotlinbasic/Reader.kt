package com.jong1.kotlinbasic

import java.util.Scanner

fun main() {
    javaScanner()
}

/**
 * Java의 Scanner를 활용하는 방법
 *
 * in이 `in`으로 선언된 이유는, 코틀린에서 in은 사용할수 없는 예약어 이기 때문
 */
fun javaScanner() {
    // Scanner
    val scanner = Scanner(System.`in`)
    val name = scanner.nextLine()
    println("Hello, $name")

}