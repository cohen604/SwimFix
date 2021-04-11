import 'package:client_application/Screens/Arguments/CameraScreenArguments.dart';
import 'package:client_application/Screens/Arguments/PoolsScreenArguments.dart';
import 'package:client_application/Screens/Arguments/UploadScreenArguments.dart';
import 'package:client_application/Screens/ColorsHolder.dart';
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
  ColorsHolder _colorsHolder = new ColorsHolder();
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SwimFix',
      initialRoute: '/',
      theme: ThemeData(
        primaryColor: _colorsHolder.getBackgroundForI1(),
        primarySwatch: _colorsHolder.createMaterialColor(
            _colorsHolder.getBackgroundForI1()
        ),
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
        '/film': (context) {
          CameraScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screensHolders.getCameraScreen(args);
        },
        '/pools': (context) {
          PoolsScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screensHolders.getPoolsScreen(args);
        }

      },
      debugShowCheckedModeBanner: false,
    );
  }
}