import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/ScreenArguments/CameraScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/ResearcherScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/SwimmerScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/UploadScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/VideoScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/WelcomeScreenArguments.dart';
import 'package:client/Domain/Swimmer.dart';
import 'package:client/Web/Screens/WebLoginScreen.dart';
import 'package:client/Web/Screens/WebResearcherScreen.dart';
import 'package:client/Web/Screens/WebSwimmerScreen.dart';
import 'package:client/Web/Screens/WebUploadScreen.dart';
import 'package:client/Web/Screens/WebWelcomeScreen.dart';
import 'package:flutter/cupertino.dart';

/// if(kIsWeb) {
///   Do something
/// }
class ScreenHolder {

  Widget getLoginScreen() {
      return new WebLoginScreen();
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

  Widget getVideoPreviewScreen(FeedbackVideoStreamer streamer) {
    return null;
  }

  Widget getVideosScreen(VideoScreenArguments args) {
    return null;
  }

  Widget getCameraScreen(CameraScreenArguments args) {
    return null;
  }

}