import 'package:flutter/material.dart';

/// 무조건 시작되는 시작점 : main.dart 의 main메소드
void main() {
  /// 플러터 앱 실행
  runApp(
    /// MaterialApp은 항상 최상위에 위치한다
    /// Scaffold는 바로 아래에 위치한다.
    /// 아래의 MaterialApp, home, body, child등을 Widget이라고 한다.
    MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        backgroundColor: Colors.black,
        body: Center(
          child: Text(
            'Hello, World!',
            style: TextStyle(
              color: Colors.white,
              fontSize: 24,
            ),
          ),
        ),
      ),
    ),
  );
}
