import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/UserPermissions.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/Arguments/AboutScreenArguments.dart';
import 'package:client/Screens/Arguments/CoachScreenArguments.dart';
import 'package:client/Screens/Arguments/MultiReportScreenArguments.dart';
import 'package:client/Screens/Arguments/ResearcherScreenArguments.dart';
import 'package:client/Screens/WebAboutScreen.dart';
import 'package:client/Screens/WebCoachScreen.dart';
import 'package:client/Screens/WebDownloadScreen.dart';
import 'package:client/Screens/WebMultiReportsScreen.dart';
import 'package:client/Screens/WebResearcherScreen.dart';
import 'Arguments/ReportScreenArguments.dart';
import 'Arguments/SwimmerScreenArguments.dart';
import 'Arguments/UploadScreenArguments.dart';
import 'Arguments/WelcomeScreenArguments.dart';
import 'package:client/Screens/WebReportScreen.dart';
import 'package:client/Screens/WebSwimmerScreen.dart';
import 'package:client/Screens/WebUploadScreen.dart';
import 'package:client/Screens/WebWelcomeScreen.dart';
import 'package:flutter/cupertino.dart';

class ScreenHolder {

  Widget getAboutScreen(AboutScreenArguments args) {
    if(args == null) {
      args = new AboutScreenArguments();
    }
    return new WebAboutScreen(args);
  }

  Widget getDownloadsScreen() {
    return new WebDownloadScreen();
  }

  Widget getWelcomeScreen(WelcomeScreenArguments args) {
    return new WebWelcomeScreen(args: args);
  }

  Widget getSwimmerScreen(SwimmerScreenArguments args) {
    return new WebSwimmerScreen(arguments: args,);
  }

  Widget getUploadScreen(UploadScreenArguments args) {
    return new WebUploadScreen(args: args,);
  }

  Widget getResearcherScreen(ResearcherScreenArguments args) {
    // args = new ResearcherScreenArguments(
    //     new WebUser(
    //         new Swimmer('uid', 'email', 'name'),
    //         new UserPermissions(true, true, true, true)));
    return new WebResearcherScreen(args);
  }

  Widget getReportScreen(ReportScreenArguments args) {
    return new WebReportScreen(args: args);
  }

  Widget getMultiReportScreen(MultiReportScreenArguments args) {
    args = new MultiReportScreenArguments(
        new WebUser(
            new Swimmer('uid', 'email', 'name'),
            new UserPermissions(true, true, true, true)));
    return new WebMultiReportsScreen(args);
  }

  Widget getCoachScreen(CoachScreenArguments args) {
    return new WebCoachScreen(args);
  }
}