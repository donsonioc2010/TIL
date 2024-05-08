import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

final homeUrl = Uri.parse('https://devjong12.tistory.com');

class HomeScreen extends StatelessWidget {
  /// final controller = WebViewController();  컨트롤러가 주입됨
  /// final request = WebViewController().loadRequest(); // loadRequest의 결과값이 주입됨
  /// final result = WebViewController()..loadRequest(); // loadRequest를 실행하고, 컨트롤러가 주입됨.
  WebViewController controller = WebViewController()
  ..loadRequest(homeUrl);

  HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
            style: TextStyle(
              color: Colors.white,
            ),
            'Web View Title'
        ),
        backgroundColor: Colors.orange,
        centerTitle: true,
        actions: [
          IconButton(
            onPressed: (){
              controller.loadRequest(homeUrl);
            },
            icon: Icon(
              Icons.home
            ),
          )
        ],
      ),
      body: WebViewWidget(
        controller: controller,
      ),
    );
  }
}

