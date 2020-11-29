import 'package:client/Screens/UploadScreen.dart';
import 'package:flutter/material.dart';
import 'Services/connectionHandler.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SwimFix',
      initialRoute: '/',
      theme: ThemeData(
        primarySwatch: Colors.blue, //main color (defualt color).
      ),
      routes: {
        '/': (context) => new UploadScreen(),
      },
      debugShowCheckedModeBanner: false,
    );
  }
}