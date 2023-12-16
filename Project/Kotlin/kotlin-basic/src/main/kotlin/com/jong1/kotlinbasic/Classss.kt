package com.jong1.kotlinbasic

/**
 * Class
 */
fun main() {
    val person = Person("종원", 30)
    println(person.name)
    println(person.age)

    //person.name="종원2"     // 수정 불가 (val이기 떄문)
    //person.age = 21       // 수정 가능 (var이기 떄문)

    println()
    val person2 = Person("종원")
    println(person2.name)
    println(person2.age)
}

/**
 * Java에서의 Class 생성의 공통 방식
 */
/*
class Person {
    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }

    val name: String
    val age: Int

}
*/
/**
 * Kotlin스럽게 클래스 생성방식
 * class Person(~~)만으로도 끝낼 수 있으며, 로직을 제작해야 하는 경우 괄호를 열어서 기능의 제작이 가능해진다
 */
class Person(
    val name: String,
    var age: Int,
) {
    // 기본 생성자
    init {
        println("Hello, $name")
    }

    // 보조 생성자
    constructor(name: String) : this(name, 0) {
        println("보조 생성자")
    }
}

/**
 * Default의 접근 제어자는 public이다
 */
class Person2(
    val name: String,
    private var age: Int,
)