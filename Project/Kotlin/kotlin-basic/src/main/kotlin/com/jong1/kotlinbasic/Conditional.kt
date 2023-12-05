package com.jong1.kotlinbasic

/**
 * 조건문
 */

fun main() {
    exampleWhen(5)
    exampleWhen(7)
    exampleWhen(11)
}

fun useIf() {
    println("IF문")
    val a = 10
    val b = 20

    if (a > b) {
        println("a > b")
    } else if (a < b) {
        println("a < b")
    } else {
        println("a == b")
    }

    val max = if (a > b) a else b
    println(max)
}

// If문을 코틀린으로
fun useWhen() {
    println("When")

    val a = 10
    val b = 20

    // 한줄~
    when {
        a > b -> println("a > b")
        a < b -> {
            println("a < b")
        }
        else -> {
            println("a == b")
        }
    }
}

// 이 코드는 If에서도 사용이 가능함.
fun exampleWhen(i : Int) {
    val result : String = when{
        i > 10 -> "10초과"
        i > 5 -> "5초과 10이하"
        else -> "5이하"
    }

    println(result)
}

/**
 * Java처럼 삼항을 사용하고 싶은 경우
 */
fun useSamhang() {
    var i: Int = 5
    var result = if(i > 5) true else false
}