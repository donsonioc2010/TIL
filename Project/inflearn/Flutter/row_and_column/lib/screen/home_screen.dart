import 'package:flutter/material.dart';
import 'package:row_and_column/const/colors.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    /// 최근 디바이스의 UI를 고려한 배치
    return Scaffold(
        body: SafeArea(
          child: Container(
            color: Colors.black,
            height: double.infinity,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.start,
              mainAxisSize: MainAxisSize.max,
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: colors
                  .map(
                    (e) => Container(
                      height: 50.0,
                      width: 50.0,
                      color: e,
                    ),
                  )
                  .toList(),
            ),
          ),
        )
    );
  }
}
