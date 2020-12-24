import 'package:client/Domain/ScreenArguments/WelcomeScreenArguments.dart';
import 'package:client/Screens/LoginScreen.dart';
import 'package:client/Screens/UploadScreen.dart';
import 'package:client/Screens/VideosScreen.dart';
import 'package:client/Screens/WelcomeScreen.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'Domain/ScreenArguments/VideoScreenArguments.dart';
import 'Domain/Swimer.dart';
import 'Screens/VideoPreviewScreen.dart';


//if running from web:localhost add to project arguments --web-host 5000
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {

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
        '/login': (context) => new LoginScreen(),
        '/welcome': (context) {
          WelcomeScreenArguments args = ModalRoute.of(context).settings.arguments;
          Swimmer swimmer = new Swimmer("uid", "email", "name");
          return new WelcomeScreen(swimmer: swimmer);
        },
        '/upload': (context) => new UploadScreen(),
        '/videoPreview': (context) => new VideoPreviewScreen(
          feedbackVideoStreamer: ModalRoute.of(context).settings.arguments,),
        '/videos': (context) {
          VideoScreenArguments args = ModalRoute.of(context).settings.arguments;
          return new VideosScreen(
            videos: args.videos,
            futureVideos: args.futureVideos,
          );
        },
      },
      debugShowCheckedModeBanner: false,
    );
  }
}