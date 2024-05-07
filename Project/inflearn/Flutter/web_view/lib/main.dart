import 'package:flutter/material.dart';
import 'package:web_view/screen/home_screen.dart';

void main() {
  // 플러터 프레임워크가 실행할 준비가 될떄까지 기다리는 기능
  WidgetsFlutterBinding.ensureInitialized();

  runApp(
    MaterialApp(
      home: HomeScreen(),
    )
  );
}
