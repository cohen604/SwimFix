import 'package:client_application/Screens/Arguments/CameraScreenArguments.dart';
import 'package:client_application/Screens/Arguments/UploadScreenArguments.dart';
import 'package:client_application/Screens/CameraScreen.dart';
import 'package:client_application/Screens/LoginScreen.dart';
import 'package:client_application/Screens/UploadScreen.dart';
import 'package:client_application/Screens/WelcomeScreen.dart';
import 'package:flutter/cupertino.dart';

import 'Arguments/WelcomeScreenArguments.dart';

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
    return CameraScreen(args);
  }

  Widget getFeedbacksScreen() {
    return null;
  }

  Widget getFeedbackScreen() {
    return null;
  }

}