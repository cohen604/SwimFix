import 'package:client_application/Screens/Arguments/CameraScreenArguments.dart';
import 'package:client_application/Screens/Arguments/HistoryScreenArguments.dart';
import 'package:client_application/Screens/Arguments/PoolsScreenArguments.dart';
import 'package:client_application/Screens/Arguments/UploadScreenArguments.dart';
import 'package:client_application/Screens/CameraScreen.dart';
import 'package:client_application/Screens/FeedbackScreen.dart';
import 'package:client_application/Screens/LoginScreen.dart';
import 'package:client_application/Screens/UploadScreen.dart';
import 'package:client_application/Screens/WelcomeScreen.dart';
import 'package:flutter/cupertino.dart';

import '../Arguments/FeedbackScreenArguments.dart';
import '../Arguments/WelcomeScreenArguments.dart';
import '../PoolsScreen.dart';
import '../HistoryScreen.dart';

class ScreensHolders {

  Widget getLoginScreen() {
    return new LoginScreen();
  }

  Widget getWelcomeScreen(WelcomeScreenArguments args) {
    return new WelcomeScreen(args);
  }

  Widget getUploadScreen(UploadScreenArguments args) {
    return new UploadScreen(args);
  }

  Widget getCameraScreen(CameraScreenArguments args) {
    return new CameraScreen(args);
  }

  Widget getPoolsScreen(PoolsScreenArguments args) {
    return new PoolsScreen(args);
  }

  Widget getFeedbackScreen(FeedbackScreenArguments args) {
    return new FeedbackScreen(args);
  }

  Widget getHistoryScreen(HistoryScreenArguments args) {
    return new HistoryScreen(arguments: args,);
  }

}