import 'package:client/Domain/Feedback/FeedBackVideoStreamer.dart';
import 'Screens/Arguments/CameraScreenArguments.dart';
import 'Screens/Arguments/ResearcherScreenArguments.dart';
import 'Screens/Arguments/SwimmerScreenArguments.dart';
import 'Screens/Arguments/UploadScreenArguments.dart';
import 'Screens/Arguments/WelcomeScreenArguments.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'Screens/Arguments/VideoScreenArguments.dart';
import 'Screens/ScreensHolder.dart';

/// if running from web:localhost add to project arguments --web-host 5000
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {

  ScreenHolder _screenHolder = new ScreenHolder();

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
          return _screenHolder.getAboutScreen();
        },
        '/login': (context) {
          return _screenHolder.getLoginScreen();
        },
        '/welcome': (context) {
          WelcomeScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getWelcomeScreen(args);
        },
        '/swimmer': (context) {
          SwimmerScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getSwimmerScreen(args);
        },
        '/researcher': (context) {
          ResearcherScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getResearcherScreen(args);
        },
        '/upload': (context) {
          UploadScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getUploadScreen(args);
        },
      },
      debugShowCheckedModeBanner: false,
    );
  }
}