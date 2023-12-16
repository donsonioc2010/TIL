package com.jong1.kotlinbasic


/**
 * 인터페이스의 활용법
 */
interface Drawable {
    fun draw()
}

abstract class Animal2 {
    open fun move() {
        println("이동")
    }
}

class Dog2 : Animal2(), Drawable {
    override fun draw() {
        println("개 그리기")
    }
}

class Cat2 : Animal2(), Drawable {
    override fun draw() {
        println("고양이 그리기")
    }

}