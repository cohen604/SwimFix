import 'package:client_application/Screens/LoginScreen.dart';
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

  Widget getUploadScreen() {
    return null;
  }

  Widget getCameraScreen() {
    return null;
  }

  Widget getFeedbacksScreen() {
    return null;
  }

  Widget getFeedbackScreen() {
    return null;
  }

}