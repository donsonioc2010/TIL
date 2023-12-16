package com.jong1.kotlinbasic

/**
 * 함수에 대한 기록
 */
fun main() {
    println(sum(10, 20))
    // 함수지정, 순서변경등이 자유로움
    println(sum(a = 30, b = 50))
    println(sum(b = 100, c = 50, a = 40))

}

/**
 * 최상단에서 작성된 함수를 TopLevel함수라고 칭하며, 어느 장소, 파일에서건 사용이 가능하다
 */
/*fun sum(a: Int, b: Int): Int {
    return a + b
}*/
// 코틀린스럽게~
//fun sum(a: Int, b: Int)  = a + b

// 오버로드 하지 않고 활용하는 방법(기본값을 지정)
fun sum(a: Int, b: Int, c: Int = 0) = a + b + c