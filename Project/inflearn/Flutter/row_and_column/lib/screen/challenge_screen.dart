import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:row_and_column/const/colors.dart';

class ChallengeScreen extends StatelessWidget {
  const ChallengeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    /// 최근 디바이스의 UI를 고려한 배치
    return Scaffold(
        body: SafeArea(
          child: Container(
            height: double.infinity,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: colors
                      .map(
                        (e) => Container(
                      height: 50.0,
                      width: 50.0,
                      color: e,
                    ),
                  ).toList(),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(
                      height: 50.0,
                      width: 50.0,
                      color: Colors.orange,
                    ),
                  ],
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: colors
                      .map(
                        (e) => Container(
                      height: 50.0,
                      width: 50.0,
                      color: e,
                    ),
                  ).toList(),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(
                      height: 50.0,
                      width: 50.0,
                      color: Colors.green,
                    ),
                  ]
                )
              ]
            ),
          ),
        )
    );
  }
}
