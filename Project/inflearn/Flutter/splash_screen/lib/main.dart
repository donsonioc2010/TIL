import 'package:flutter/material.dart';

void main() {
  runApp(
    MaterialApp(
      home: HomeScreen(),
    ),
  );
}

/// StateessWidget
class HomeScreen extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // 335CB0
        backgroundColor: Color(0xFF335CB0),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Image.asset(
              'asset/img/logo.png',
            ),
            CircularProgressIndicator(
              color: Colors.white,
            ),
          ],
        )
    );
  }
}
