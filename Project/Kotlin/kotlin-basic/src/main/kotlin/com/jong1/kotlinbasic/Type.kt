package com.jong1.kotlinbasic

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

fun main() {
    typeString()
    mathUtils()
    randomUtil()
}

/**
 * java에서 문자열을 결합하는 방식인 Concat, + 등의 연산자 대신에 JavaScript처럼 ${}, $var이름 표현이 가능하다
 */
fun typeString() {
    println("[Type String] Start")

    val name : String = "heLLo"
    println(name)
    println(name.uppercase())
    println(name.lowercase())
    for (i in name.indices) {
        println("Char at $i is ${name[i]}")
    }
    name.toCharArray().forEach { c ->  println("Stream char : $c, ") }
}

/**
 * Math클래스에 대한 정의
 */
fun mathUtils() {
    println("[Math Utils] Start")

    var i = 10
    var j = 20
    println("max() ${max(i, j)}")
    println("kotlin.math.max() ${kotlin.math.max(i, j)}")
    println("min() ${min(i, j)}")
    println("kotlin.math.min() ${kotlin.math.min(i, j)}")
}

/**
 * Random
 */
fun randomUtil() {
    println("[Random Utils] Start")

    var randomNumber : Int = Random.nextInt()
    println(randomNumber)
    println(Random.nextInt(100))
    println(Random.nextInt(0, 100)) // 0 ~ 99
    println(Random.nextFloat())
}