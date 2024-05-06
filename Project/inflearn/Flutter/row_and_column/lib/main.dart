import 'package:flutter/material.dart';
import 'package:row_and_column/screen/challenge_screen.dart';
import 'package:row_and_column/screen/flexible_screen.dart';
import 'package:row_and_column/screen/home_screen.dart';
import 'package:row_and_column/screen/home_screen2.dart';

void main() {
  runApp(
    MaterialApp(
      // home: HomeScreen(),
      // home: HomeScreen2(),
      // home: FlexibleScreen(),
      home: ChallengeScreen(),
    ),
  );
}

