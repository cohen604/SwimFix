import 'package:client/Screens/LoginScreen.dart';
import 'package:client/Screens/UploadScreen.dart';
import 'package:client/Screens/VideosScreen.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'Domain/ScreenArguments/VideoScreenArguments.dart';
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
      initialRoute: '/upload',
      theme: ThemeData(
        primarySwatch: Colors.blue, //main color (defualt color).
      ),
      routes: {
        '/login': (context) => new LoginScreen(),
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