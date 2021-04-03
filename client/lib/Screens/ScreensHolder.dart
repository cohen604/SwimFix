import 'package:client/Domain/Feedback/FeedBackVideoStreamer.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/WebAboutScreen.dart';

import 'Arguments/CameraScreenArguments.dart';
import 'Arguments/ResearcherScreenArguments.dart';
import 'Arguments/SwimmerScreenArguments.dart';
import 'Arguments/UploadScreenArguments.dart';
import 'Arguments/VideoScreenArguments.dart';
import 'Arguments/WelcomeScreenArguments.dart';
import 'package:client/Screens/WebLoginScreen.dart';
import 'package:client/Screens/WebResearcherScreen.dart';
import 'package:client/Screens/WebSwimmerScreen.dart';
import 'package:client/Screens/WebUploadScreen.dart';
import 'package:client/Screens/WebWelcomeScreen.dart';
import 'package:flutter/cupertino.dart';

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

}