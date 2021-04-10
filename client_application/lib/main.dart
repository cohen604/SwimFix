import 'package:client_application/Screens/Arguments/UploadScreenArguments.dart';
import 'package:client_application/Screens/ScreensHolder.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';

import 'Screens/Arguments/WelcomeScreenArguments.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
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
          WelcomeScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screensHolders.getWelcomeScreen(args);
        },
        '/upload': (context) {
          UploadScreenArguments args =  ModalRoute.of(context).settings.arguments;
          return _screensHolders.getUploadScreen(args);
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