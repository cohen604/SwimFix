import 'package:client_application/Screens/ScreensHolder.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {

  ScreensHolders _screensHolders = new ScreensHolders();

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
        '/': (context) {
          return _screensHolders.getLoginScreen();
        },
        '/welcome': (context) {
          // WelcomeScreenArguments args = ModalRoute
          //     .of(context)
          //     .settings
          //     .arguments;
          return _screensHolders.getWelcomeScreen();
        },
        '/upload': (context) {
          return _screensHolders.getUploadScreen();
        },
        '/camera': (context) {
          return _screensHolders.getCameraScreen();
        },
        '/feedbacks': (context) {
          return _screensHolders.getFeedbacksScreen();
        },
        'feedback': (context) {
          return _screensHolders.getFeedbackScreen();
        }
      },
      debugShowCheckedModeBanner: false,
    );
  }
}