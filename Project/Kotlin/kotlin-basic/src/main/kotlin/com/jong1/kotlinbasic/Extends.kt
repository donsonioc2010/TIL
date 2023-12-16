package com.jong1.kotlinbasic

fun main() {
    val dog: Animal = Dog()
    val cat: Animal = Cat()

    println("개의 시간")
    dog.move()
    dog.move2()

    println()
    println("고양이의 시간")
    cat.move()
    cat.move2()
}

/**
 * 상속의 사용방법을 기록합니다.
 * open을 적지않으면 override가 불가능하다
 */
abstract  class Animal {
    abstract fun move()

    open fun move2() {
        println("최상위!")
    }

}

class Dog : Animal() {
    override fun move() {
        println("필수 구현해야하는!")
    }

    override fun move2() {
        //super.move2()
        println("바꿨지롱~")
    }
}

class Cat : Animal() {
    override fun move() {
        println("필수 구현해야하는!")
    }
}


/*
일반 클래스의 경우 아래처럼 작성시에는 상속이 불가능하다
class Person5
class SuperMan : Person5()
*/

// 일반클래스는 open이 적혀있어야 상속이 가능하다
open class Person5
class SuperMan : Person5()