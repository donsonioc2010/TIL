package com.jong1.kotlinbasic

fun main() {
    // 추론기능으로 사실상 동일
//    val box1 = Box<Int>(10)
    val box1 = Box(10)
    val box2 = Box2(Cat2())
}


class Box<T>(val value: T) {

}
class Box2<T: Animal2>(val value: T) {

}