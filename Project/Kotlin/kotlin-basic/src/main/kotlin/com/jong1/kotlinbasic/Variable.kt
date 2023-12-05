package com.jong1.kotlinbasic


/**
 * https://kimch3617.tistory.com/entry/Kotlin%EC%97%90%EC%84%9C-val-%EC%99%80-const-val-%EC%9D%98-%EC%B0%A8%EC%9D%B4
 * Top Level 상수 != Java public static final
 * Java 의 public static final로 선언을 시키고 싶으면, Class의 companion object안에 상수로 선언해야함
 *
 * const val은 컴파일 시간에 결정이 되는 상수로, 런타임에 할당되는 val과 는 다르게 컴파일 시간 동안에 할당이 된다.
 * const는 함수, 어떤 클래스의 생성자에게도 결코 할당이 불가능하고, 기본 자료형만 할당가능하다.
 */

const val foo = "abc"

fun main() {
    /**
     * Primitive Type의 경우에도 타입은 대문자로 시작함
     * Dobule, Float, Int, String, Boolean, Char, Short, Byte 등등
     */
    // 타입 추론기능이 존재함.
    var ageToDobule = 28.3

    // 타입을 명시적으로 지정할 수 있음.
    var ageToInt : Int = 28
    var name : String = "Jong1"


    // Const
    val num = 20
    // num = 5 Error
}

fun boxing() {
    var i = 10
    var l = 20L

    // Java의 변환, int -> long
    //l = i;, l = (long)i; 다 안됨
    l = i.toLong() // Kotlin의 변환, int -> long
    i = l.toInt()

    var name = ""
    name = i.toString()
    name = "10"
    i = name.toInt()
}