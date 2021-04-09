import 'package:client_application/Screens/Arguments/UploadScreenArguments.dart';
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

  Widget getUploadScreen(UploadScreenArguments arguments) {
    return new UploadScreen(arguments);
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