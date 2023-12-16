package com.jong1.kotlinbasic

/**
 * Null에서 안전하게 개발하기 위한 챕터
 */
fun main() {

    // Null을 변수에 탐을 수 있는 방법은 ?를 써야한다.
    var nullAbleName: String?  = null
    nullAbleName = "종원"
    nullAbleName = null

    var notNullAbleName: String = ""

    /*
    ?가 달려있는 타입과, 달려있지 않는 타입은 서로 String을 담는다 해도 다른 타입이기에, Null체크를 해야함

    //불가능
    notNullAbleName = nullAbleName
    */

    // 가능
    if (nullAbleName != null) {
        notNullAbleName = nullAbleName
    }
    // 가능, 임의로개발자가 지정한 것이기 떄문에 실제 Null인 경우 NPE발생함
    //notNullAbleName = nullAbleName!!

    // null이 아닌경우, it으로 let 안에서 접근이 가능하다
    nullAbleName?.let {
        notNullAbleName = it
    }
}