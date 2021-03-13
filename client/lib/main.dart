import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/ScreenArguments/CameraScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/WelcomeScreenArguments.dart';
import 'package:client/Screens/CameraScreen.dart';
import 'package:client/Screens/LoginScreen.dart';
import 'package:client/Screens/UploadScreen.dart';
import 'package:client/Screens/VideosScreen.dart';
import 'package:client/Screens/WelcomeScreen.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'Domain/ScreenArguments/VideoScreenArguments.dart';
import 'Domain/Swimer.dart';
import 'Screens/ScreensHolder.dart';
import 'Screens/VideoPreviewScreen.dart';

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
      initialRoute: '/login',
      theme: ThemeData(
        primarySwatch: Colors.blue, //main color (defualt color).
      ),
      routes: {
        '/login': (context) => _screenHolder.getLoginScreen(),
        '/welcome': (context) {
          WelcomeScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getWelcomeScreen(args);
        },
        '/upload': (context) => new UploadScreen(),
        '/videoPreview': (context) {
          FeedbackVideoStreamer streamer = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getVideoPreviewScreen(streamer);
        },
        '/videos': (context) {
          VideoScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getVideosScreen(args);
        },
        '/camera': (context) {
          CameraScreenArguments args = ModalRoute.of(context).settings.arguments; /// necessary?
          return _screenHolder.getCameraScreen(args);
        }
      },
      debugShowCheckedModeBanner: false,
    );
  }
}