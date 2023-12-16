package com.jong1.kotlinbasic

fun main() {
    useForToInt()
//    useFor()
//    useForEachStream()
    ListExample().getListToListPlus()
}

fun useForToInt() {
    var items: List<Int> = ListExample.getImmutableList()
    println("indices : ${items.indices}, size : ${items.size}")

    // 0 ~ 5 까지 실행한다. [0,1,2,3,4,5]
    for(i in 0..5) {
        print("${i} ,")
    }
    println()

    // 5 ~ 0 까지 실행한다. [5,4,3,2,1,0]
    for(i in 5 downTo 0) {
        print("${i} ,")
    }
    println()

    // 0 ~ 4, 0.1.2.3.4
    for(i in items.indices) {
        print("${items[i]} ,")
    }
    println()

    // 0 ~ 4, 0.1.2.3.4
    for(i in items.indices) {
        print("${items.get(i)} ,")
    }
    println()
}

fun useFor() {
    var items: List<Int> = ListExample.getImmutableList()
    for(item in items) {
        print("${item} ,")
    }
    println()
}

//아래 Stream은 두개모두 실행되고 동일함, 근데 Java도 Stream ForEach 위처럼 쓰잖아? 줄여
fun useForEachStream() {
    var items: List<Int> = ListExample.getImmutableList()

    items.forEach { item -> print("${item} ,") }
    //items.stream().forEach{item -> print("${item} ,")}

    println()
}

/**
 * List 선언 및 사용방법
 * listOf의 경우에는 객체가 생성된 뒤 불변객체임
 */
class ListExample {

    //Static Method
    companion object {
        var staticInt: Int = 5

        @JvmStatic
        fun getImmutableList(): List<Int> {
            return listOf(1, 2, 3, 4, 5)
        }

        // plus, minus등이 실행이 가능함
        @JvmStatic
        fun <T> getMutableList(): MutableList<Int> {
            return mutableListOf(1, 2, 3, 4, 5)
        }
    }

    // ArrayList도 Immutable List라서 plus를 해도 적용이 안됨
    fun getListToListPlus(): List<Int> {
        val items: List<Int> = ArrayList<Int>()
        items.plus(1)
        items.plus(2)
        items.plus(3)
        items.plus(4)
        items.plus(5)
        items.minus(3)
        println(items.toString())
        return items
    }
}
