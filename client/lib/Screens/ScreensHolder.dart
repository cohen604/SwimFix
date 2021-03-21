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
import 'package:flutter/foundation.dart';

import 'CameraScreen.dart';
import 'LoginScreen.dart';
import 'UploadScreen.dart';
import 'VideoPreviewScreen.dart';
import 'VideosScreen.dart';
import 'WelcomeScreen.dart';

class ScreenHolder {

  Widget getLoginScreen() {
      if(kIsWeb) {
        ///web
        return new WebLoginScreen();
      }
      ///mobile TODO change this screen
      return new LoginScreen();
  }

  Widget getWelcomeScreen(WelcomeScreenArguments args) {
    if(kIsWeb) {
      ///web
      return new WebWelcomeScreen(arguments: args);
    }
    ///mobile TODO change this screen
    return new WelcomeScreen(swimmer: args.swimmer);
  }


  Widget getSwimmerScreen(SwimmerScreenArguments args) {
    if(kIsWeb) {
      //TODO delete this
      if(args == null) {
        args = new SwimmerScreenArguments(new Swimmer("uid", "email", "name"));
      }
      return new WebSwimmerScreen(arguments: args,);
    }
    return null;
  }

  Widget getResearcherScreen(ResearcherScreenArguments args) {
    if(kIsWeb) {
      //TODO delete this
      if(args == null) {
        String uid = "MZogz1uG95TCkIhDCoAiyYg9QnH2";
        String email = "avrahamcalev2@gmail.com";
        String name = "אברהם כלב";
        args = new ResearcherScreenArguments(new Swimmer(uid, email, name));
      }
      return new WebResearcherScreen(args: args);
    }
    return null;
  }

  Widget getUploadScreen(UploadScreenArguments args) {
    if(kIsWeb) {
      //TODO delete this
      if(args == null) {
        String uid = "MZogz1uG95TCkIhDCoAiyYg9QnH2";
        String email = "avrahamcalev2@gmail.com";
        String name = "אברהם כלב";
        args = new UploadScreenArguments(new Swimmer(uid, email, name));
      }
      return new WebUploadScreen(args: args,);
    }
    ///mobile TODO change this screen
    return new UploadScreen();
  }

  Widget getVideoPreviewScreen(FeedbackVideoStreamer streamer) {
    if(kIsWeb) {
      ///web TODO
    }
    ///mobile TODO change this screen
    return new VideoPreviewScreen(feedbackVideoStreamer: streamer);
  }

  Widget getVideosScreen(VideoScreenArguments args) {
    if(kIsWeb) {
      ///web TODO
    }
    ///mobile TODO change this screen
    return new VideosScreen( videos: args.videos,
        futureVideos: args.futureVideos);
  }

  Widget getCameraScreen(CameraScreenArguments args) {
    if(kIsWeb) {
      ///web TODO
    }
    ///mobile TODO change this screen
    return new CameraScreen(args);
  }

}