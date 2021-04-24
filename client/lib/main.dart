import 'package:client/Screens/WebColors.dart';
import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Screens/Arguments/ViewFeedbackArguments.dart';
import 'Screens/Arguments/AboutScreenArguments.dart';
import 'Screens/Arguments/CoachScreenArguments.dart';
import 'Screens/Arguments/MultiReportScreenArguments.dart';
import 'Screens/Arguments/ReportScreenArguments.dart';
import 'Screens/Arguments/ResearcherScreenArguments.dart';
import 'Screens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'Screens/Arguments/SwimmerScreenArguments.dart';
import 'Screens/Arguments/UploadScreenArguments.dart';
import 'Screens/Arguments/WelcomeScreenArguments.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'Screens/ScreensHolder.dart';

/// if running from web:localhost add to project arguments --web-host 5000
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {

  ScreenHolder _screenHolder = new ScreenHolder();
  WebColors _webColors = new WebColors();

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SwimFix',
      initialRoute: '/',
      theme: ThemeData(
        primarySwatch: _webColors.createMaterialColor(
            _webColors.getBackgroundForI1()
        ),
      ),
      routes: {
        '/': (context) {
          AboutScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getAboutScreen(args);
        },
        '/downloads': (context) {
          return _screenHolder.getDownloadsScreen();
        },
        '/welcome': (context) {
          WelcomeScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getWelcomeScreen(args);
        },
        '/swimmer': (context) {
          SwimmerScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getSwimmerScreen(args);
        },
        '/upload': (context) {
          UploadScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getUploadScreen(args);
        },
        '/researcher': (context) {
          ResearcherScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getResearcherScreen(args);
        },
        '/researcher/report': (context) {
          ReportScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getReportScreen(args);
        },
        '/researcher/multireport': (context) {
          MultiReportScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getMultiReportScreen(args);
        },
        '/coach': (context) {
          CoachScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getCoachScreen(args);
        },
        '/history': (context) {
          SwimmerScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getSwimmerHistoryScreen(args);
        },
        '/historyDay': (context) {
          SwimmerHistoryPoolsArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getSwimmerHistoryDayScreen(args);
        },
        '/viewFeedback': (context) {
          ViewFeedBackArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getViewFeedbackScreen(args);
        },
      },
      debugShowCheckedModeBanner: false,
    );
  }
}