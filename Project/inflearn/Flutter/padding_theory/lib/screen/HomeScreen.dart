import 'package:flutter/material.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(
          color: Colors.red,
          child: Padding(
            // padding: EdgeInsets.all(10.0),
            // padding: EdgeInsets.symmetric(
            //     horizontal: 10.0,
            //     vertical: 5.0
            // ),
            // padding: EdgeInsets.only(
            //   left: 10.0,
            //   top: 5.0,
            //   // right: 10.0,
            //   bottom: 5.0
            // ),
            padding: EdgeInsets.fromLTRB(32.0, 64.0, 16.0, 8.0),
            child: Container(
              color: Colors.blue,
              width: 50.0,
              height: 50.0,
            ),
          ),
        ),
      )
    );
  }
}
