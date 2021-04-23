import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/UserPermissions.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'package:client/Screens/Arguments/ViewFeedbackArguments.dart';
import 'package:client/Screens/WebSwimmerHistoryScreen.dart';
import 'package:client/Screens/Arguments/CoachScreenArguments.dart';
import 'package:client/Screens/WebAboutScreen.dart';
import 'package:client/Screens/WebCoachScreen.dart';
import 'package:client/Screens/WebViewFeedbackScreen.dart';
import 'Arguments/ResearcherScreenArguments.dart';
import 'Arguments/SwimmerScreenArguments.dart';
import 'Arguments/UploadScreenArguments.dart';
import 'Arguments/WelcomeScreenArguments.dart';
import 'package:client/Screens/WebReportScreen.dart';
import 'package:client/Screens/WebSwimmerScreen.dart';
import 'package:client/Screens/WebUploadScreen.dart';
import 'package:client/Screens/WebWelcomeScreen.dart';
import 'package:flutter/cupertino.dart';
import 'WebSwimmerHistoryDayScreen.dart';


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

  Widget getReportScreen(ReprotScreenArguments args) {
    return new WebReportScreen(args: args);
  }

  Widget getUploadScreen(UploadScreenArguments args) {
    return new WebUploadScreen(args: args,);
  }

  Widget getCoachScreen(CoachScreenArguments args) {
    return new WebCoachScreen(args);
  }


  Widget getSwimmerHistoryScreen(SwimmerScreenArguments args) {
    //TODO delete this
    if(args == null) {
      String uid = "L0kjX9wZlsXJctETI9AzRfoej2s2";
      String email = "nivshir@post.bgu.ac.il";
      String name = "Niv Shirazi";
      args = new SwimmerScreenArguments(new WebUser(
          new Swimmer(uid, email, name),
          new UserPermissions(true, false, false, false)));
    }
    return new WebSwimmerHistoryScreen(arguments: args,);
  }

  Widget getSwimmerHistoryDayScreen(SwimmerHistoryPoolsArguments args) {
    //TODO delete this
    if(args == null) {
      String uid = "L0kjX9wZlsXJctETI9AzRfoej2s2";
      String email = "nivshir@post.bgu.ac.il";
      String name = "Niv Shirazi";
      String date = '2021-04-09';
      args = new SwimmerHistoryPoolsArguments(new WebUser(
          new Swimmer(uid, email, name),
          new UserPermissions(true, false, false, false)), date);
    }
    return new WebSwimmerHistoryDayScreen(arguments: args,);
  }

  Widget getViewFeedbackScreen(ViewFeedBackArguments args) {
    //TODO delete this
    if(args == null) {
      String uid = "L0kjX9wZlsXJctETI9AzRfoej2s2";
      String email = "nivshir@post.bgu.ac.il";
      String name = "Niv Shirazi";
      String path = "clients\\nivshir@post.bgu.ac.il\\feedbacks\\2021-04-09-17-57-48.mp4";
      args = new ViewFeedBackArguments(new WebUser(
          new Swimmer(uid, email, name),
          new UserPermissions(true, false, false, false)), path);
    }
    return new WebViewFeedbackScreen(arguments: args,);
  }

}