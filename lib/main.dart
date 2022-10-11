// ignore_for_file: prefer_const_constructors

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

  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        backgroundColor: Colors.white,
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("Call Native Code"),
              ElevatedButton(onPressed: (){
                _showToast();
              }, child: Text('Start'))
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _showToast() async{
    final int showtoast = await Channel.invokeMethod('showtoast', <String, String>{
      'msg':'This is a toast from Flutter to Android Native'
    });
  }
}
