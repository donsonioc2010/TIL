package com.jong1.kotlinbasic

/**
 * 타입 체크와 캐스팅의 방법
 */
fun main() {
    val dog: Animal2 = Dog2()
    val cat = Cat2()


    // 타입체크
    if (dog is Animal2) {
        println("동물")
    }

    if (dog is Dog2) {
        println("왈왈")
    }

    if (dog is Cat2) {
        println("야옹")
    }

    // 타입 캐스팅
    cat as Dog2

}
class TypeCasting {
}