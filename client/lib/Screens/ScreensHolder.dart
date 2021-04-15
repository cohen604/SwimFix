import 'package:client/Screens/WebAboutScreen.dart';
import 'Arguments/ResearcherScreenArguments.dart';
import 'Arguments/SwimmerScreenArguments.dart';
import 'Arguments/UploadScreenArguments.dart';
import 'Arguments/WelcomeScreenArguments.dart';
import 'package:client/Screens/WebResearcherScreen.dart';
import 'package:client/Screens/WebSwimmerScreen.dart';
import 'package:client/Screens/WebUploadScreen.dart';
import 'package:client/Screens/WebWelcomeScreen.dart';
import 'package:flutter/cupertino.dart';

class ScreenHolder {

  Widget getAboutScreen() {
    return new WebAboutScreen();
  }

  Widget getWelcomeScreen(WelcomeScreenArguments args) {
    return new WebWelcomeScreen(args: args);
  }

  Widget getSwimmerScreen(SwimmerScreenArguments args) {
    return new WebSwimmerScreen(arguments: args,);
  }

  Widget getResearcherScreen(ResearcherScreenArguments args) {
    return new WebResearcherScreen(args: args);
  }

  Widget getUploadScreen(UploadScreenArguments args) {
    return new WebUploadScreen(args: args,);
  }

}