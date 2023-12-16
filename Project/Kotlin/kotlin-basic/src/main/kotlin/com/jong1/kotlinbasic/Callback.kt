package com.jong1.kotlinbasic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 콜백함수의 활용법
 */
fun main() {
    myFunc() { println("콜백함수 호출") }
    println()
    myFunc2(5) { it -> println("$it 을 받은 콜백함수 호출") }
    println()
    println(
        myFunc3(5) { it ->
            println("$it 을 받은 콜백함수 호출후 리턴")
            return@myFunc3 it * 5
        }
    )
    println()
    GlobalScope.launch {
        myFunc4(5) { it ->
            println("$it 을 받은 콜백함수 호출후 리턴")
        }
    }
}

// 함수는 Input과 Output을 정의하면 되며, 인풋이 없으면 ()로, 있으면 파라미터를 넣으면 된다
// Output이 존재하지 않는 경우에는 Unit으로 정의하면 된다.
fun myFunc(callBack: () -> Unit) {
    println("myFunc 호출")
    callBack()
    println("myFunc 호출 끝")
}

fun myFunc2(a: Int, callBack: (a: Int) -> Unit) {
    println("myFunc 호출")
    callBack(a)
    println("myFunc 호출 끝")
}

fun myFunc3(a: Int, callBack: (a: Int) -> Int): Int {
    println("myFunc 호출")
    val value = callBack(a)
    println("myFunc 호출 끝")
    return value
}
// suspend는 해당 함수가 호출이 되고 종료될 떄까지 대기를 해야하는 경우에 사용한다
// suspend는 코루틴에서만 사용하거나 suspend함수에서 다른 suspend함수의 사용이 가능하다
suspend fun myFunc4(a: Int, callBack: (a: Int) -> Unit) {
    println("Suspend myFunc4 호출")
    val value = callBack(a)
    println("Suspend myFunc4 호출 끝")
    return value
}
