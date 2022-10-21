// ignore_for_file: prefer_const_constructors, prefer_const_literals_to_create_immutables

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(const MyApp());

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  static const Channel = MethodChannel('com.example.snackbar_demo');

  ImageProvider logo = AssetImage("assets/images/onboarding_carousel_2.png");

  @override
  void didChangeDependencies() {
    precacheImage(logo, context);
    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
          fontFamily: 'Quicksand'
      ),
      home: Scaffold(
        backgroundColor: Colors.white,
        body: Center(
          child: Column(
            children: [
              SizedBox(
                height: 85,
              ),
              Text(
                "OMNI WALLET",
                style: TextStyle(
                    color: Colors.black,
                    fontSize: 18
                ),
              ),
              SizedBox(
                height: 24,
              ),
              Text(
                "Chào mừng bạn đến với\nOmni Wallet",
                style: TextStyle(
                    color: Colors.black,
                    fontSize: 18,
                    fontWeight: FontWeight.bold
                ),
                textAlign: TextAlign.center,
              ),
              SizedBox(
                height: 16,
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 24),
                child: Text(
                  "Omni Wallet là một ví an toàn được hàng triệu người tin dùng, giúp tất cả mọi người tiếp cận dễ dàng với thế giới web3.",
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 15,
                  ),
                  textAlign: TextAlign.center,
                ),
              ),
              SizedBox(
                height: 24,
              ),
              Expanded(
                  child: Image(
                      image: logo,
                  )
              ),
              SizedBox(
                height: 24,
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 24),
                child: ElevatedButton(
                    onPressed: () {
                      _showToast();
                    },
                    style: ElevatedButton.styleFrom(
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(25)
                      ),
                      minimumSize: Size.fromHeight(50),
                    ),
                    child: Text(
                      'Bắt đầu',
                      style: TextStyle(
                        fontSize: 15
                      ),
                    )
                ),
              ),
              SizedBox(
                height: 24,
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _showToast() async {
    final int showtoast = await Channel.invokeMethod(
        'showtoast', <String, String>{
      'msg': 'This is a toast from Flutter to Android Native'
    });
  }
}
