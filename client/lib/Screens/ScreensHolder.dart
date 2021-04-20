import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'package:client/Screens/WebSwimmerHistoryScreen.dart';
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

import 'WebSwimmerHistoryDayScreen.dart';

/// if(kIsWeb) {
///   Do something
/// }
class ScreenHolder {

  Widget getAboutScreen() {
    return new WebAboutScreen();
  }

  Widget getWelcomeScreen(WelcomeScreenArguments args) {
    return new WebWelcomeScreen(arguments: args);
  }

  Widget getSwimmerScreen(SwimmerScreenArguments args) {
    //TODO delete this
    if(args == null) {
      args = new SwimmerScreenArguments(new Swimmer("uid", "email", "name"));
    }
    return new WebSwimmerScreen(arguments: args,);
  }

  Widget getResearcherScreen(ResearcherScreenArguments args) {
    //TODO delete this
    if(args == null) {
      String uid = "MZogz1uG95TCkIhDCoAiyYg9QnH2";
      String email = "avrahamcalev2@gmail.com";
      String name = "אברהם כלב";
      args = new ResearcherScreenArguments(new Swimmer(uid, email, name));
    }
    return new WebResearcherScreen(args: args);
  }

  Widget getUploadScreen(UploadScreenArguments args) {
    //TODO delete this
    if(args == null) {
      String uid = "MZogz1uG95TCkIhDCoAiyYg9QnH2";
      String email = "avrahamcalev2@gmail.com";
      String name = "אברהם כלב";
      args = new UploadScreenArguments(new Swimmer(uid, email, name));
    }
    return new WebUploadScreen(args: args,);
  }


  Widget getSwimmerHistoryScreen(SwimmerScreenArguments args) {
    //TODO delete this
    if(args == null) {
      String uid = "L0kjX9wZlsXJctETI9AzRfoej2s2";
      String email = "nivshir@post.bgu.ac.il";
      String name = "Niv Shirazi";
      args = new SwimmerScreenArguments(new Swimmer(uid, email, name));
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
      args = new SwimmerHistoryPoolsArguments(new Swimmer(uid, email, name), date);
    }
    return new WebSwimmerHistoryDayScreen(arguments: args,);
  }

}