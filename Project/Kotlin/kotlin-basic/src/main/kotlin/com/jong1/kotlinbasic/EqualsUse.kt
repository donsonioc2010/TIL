package com.jong1.kotlinbasic

/**
 * 객체의 동일성 관련해서 Equals재정의 방법
 */
fun main() {
    val person1 = Person3("종원", 30)
    val person2 = Person3("종원", 30)

    // toString을 재정의 하지 않으면 객체의 클래스 정보와 해쉬코드가 출력되지만, 정의하는 경우에는 toString의 문구가 출력된다
    println(person1)
    println(person2)

    println("동일한 객체인가? ${person1 == person2}")
    person1.hobby = "코딩"
    println(person1.hobby)
    println(person2.hobby)
    println(person1.hobby2)


    // toString의 정의없이, 내용이 출력된다.
    println()
    val person3 = Person4("종원", 30)
    val person4 = Person4("종원", 30)

    println("data클래스~")
    println(person3)
    println(person4)

    // equals의 정의없이, 내부적으로 정의하여 판단한다
    println("동일한 객체인가? ${person3 == person4}")
}
class Person3(
    val name: String,
    val age: Int
) {

    // 인스턴스변수, static이 아니다, 접근제어자는 public이다
    var hobby : String = "hobby"
    // 인스턴스변수, 접근제어자가 private이기 때문에 외부에서 호출이 불가능하다, 아래의 코드는 외부에서 호출만 가능하게 get()을 추가만 한것
    var hobby2: String = "hobby"
        private set
        get() = "취미 : ${field}"

    init {
        println("생성자~")
    }
    override fun toString(): String {
        return "Person3(name='$name', age=$age)"
    }

    //단순하게 Data만 주고받는 경우에는 이런 Equals와 Hashcode를 구현하지 않는다.
    /*override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person3

        if (name != other.name) return false
        if (age != other.age) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        return result
    }*/
}

/**
 * data클래스도 {}를 열어서 기능의 정의가 가능하다
 */
data class Person4(
    val name: String,
    val age: Int
)