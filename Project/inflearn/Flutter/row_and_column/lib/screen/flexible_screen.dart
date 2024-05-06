import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:row_and_column/const/colors.dart';

class FlexibleScreen extends StatelessWidget {
  const FlexibleScreen({super.key});

  @override
  Widget build(BuildContext context) {
    /// 최근 디바이스의 UI를 고려한 배치
    return Scaffold(
        body: SafeArea(
          child: Container(
            color: Colors.black,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              mainAxisSize: MainAxisSize.max,
              children: [
                Flexible(
                  flex: 2,
                  fit: FlexFit.tight,
                  child: Container(
                    height: 50.0,
                    width: 50.0,
                    color: Colors.red,
                  ),
                ),
                Expanded(
                  child: Container(
                    height: 50.0,
                    width: 50.0,
                    color: Colors.orange,
                  ),
                ),
                Expanded(
                  child: Container(
                    height: 50.0,
                    width: 50.0,
                    color: Colors.yellow,
                  ),
                ),
              ]
            ),
          ),
        )
    );
  }
}
