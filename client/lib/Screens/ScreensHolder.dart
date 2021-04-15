import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/UserPermissions.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/WebAboutScreen.dart';
import 'Arguments/ResearcherScreenArguments.dart';
import 'Arguments/SwimmerScreenArguments.dart';
import 'Arguments/UploadScreenArguments.dart';
import 'Arguments/WelcomeScreenArguments.dart';
import 'package:client/Screens/WebReportScreen.dart';
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
    args = new SwimmerScreenArguments(
        new WebUser(
            new Swimmer('uid', 'email', 'name'),
            new UserPermissions(true, true, true, true)));
    return new WebSwimmerScreen(arguments: args,);
  }

  Widget getReportScreen(ReprotScreenArguments args) {
    return new WebReportScreen(args: args);
  }

  Widget getUploadScreen(UploadScreenArguments args) {
    return new WebUploadScreen(args: args,);
  }

}