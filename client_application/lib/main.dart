import 'package:client_application/Screens/Arguments/CameraScreenArguments.dart';
import 'package:client_application/Screens/Arguments/FeedbackScreenArguments.dart';
import 'package:client_application/Screens/Arguments/HistoryInvitationsScreenArguments.dart';
import 'package:client_application/Screens/Arguments/HistoryScreenArguments.dart';
import 'package:client_application/Screens/Arguments/InvitationsScreenArguments.dart';
import 'package:client_application/Screens/Arguments/MyTeamScreenArguments.dart';
import 'package:client_application/Screens/Arguments/PoolsScreenArguments.dart';
import 'package:client_application/Screens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'package:client_application/Screens/Arguments/UploadScreenArguments.dart';
import 'package:client_application/Screens/Arguments/ViewFeedbackArguments.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'Screens/Arguments/WelcomeScreenArguments.dart';
import 'Screens/Holders/ScreensHolder.dart';
import 'Screens/Holders/ColorsHolder.dart';

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
        },
        '/feedback': (context) {
          FeedbackScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screensHolders.getFeedbackScreen(args);
        },
        '/history': (context) {
          HistoryScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screensHolders.getHistoryScreen(args);
        },
        '/history/day': (context) {
          HistoryDayScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screensHolders.getHistoryDayScreen(args);
        },
        '/history/day/feedback': (context) {
          HistoryFeedBackArguments args = ModalRoute.of(context).settings.arguments;
          return _screensHolders.getHistoryFeedbackScreen(args);
        },
        '/invitations': (context) {
          InvitationsScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screensHolders.getInvitationsScreen(args);
        },
        '/invitations/history': (context) {
          HistoryInvitationsScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screensHolders.getInvitationHistoryScreen(args);
        },
        '/team': (context) {
          MyTeamScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screensHolders.getMyTeamScreen(args);
        }
      },
      debugShowCheckedModeBanner: false,
    );
  }
}