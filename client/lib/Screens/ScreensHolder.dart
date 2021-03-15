import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/ScreenArguments/CameraScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/VideoScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/WelcomeScreenArguments.dart';
import 'package:client/Domain/Swimer.dart';
import 'package:client/Web/Screens/WebLoginScreen.dart';
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
      ///mobile
      return new LoginScreen();
  }

  Widget getWelcomeScreen(WelcomeScreenArguments args) {
    if(kIsWeb) {
      ///web
      /// TODO delete this if
      if(args == null) {
        args = new WelcomeScreenArguments(new Swimmer("uid", "email", "name"));
      }
      return new WebWelcomeScreen(arguments: args);
    }
    ///mobile
    return new WelcomeScreen(swimmer: args.swimmer);
  }

  Widget getUploadScreen() {
    if(kIsWeb) {
      ///web
    }
    ///mobile
    return new UploadScreen();
  }

  Widget getVideoPreviewScreen(FeedbackVideoStreamer streamer) {
    if(kIsWeb) {
      ///web
    }
    ///mobile
    return new VideoPreviewScreen(feedbackVideoStreamer: streamer);
  }

  Widget getVideosScreen(VideoScreenArguments args) {
    if(kIsWeb) {
      ///web
    }
    ///mobile
    return new VideosScreen( videos: args.videos,
        futureVideos: args.futureVideos);
  }

  Widget getCameraScreen(CameraScreenArguments args) {
    if(kIsWeb) {
      ///web
    }
    ///mobile
    return new CameraScreen(args);
  }

}