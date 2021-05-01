import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/UserPermissions.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/Arguments/AboutScreenArguments.dart';
import 'package:client/Screens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'package:client/Screens/Arguments/ViewFeedbackArguments.dart';
import 'package:client/Screens/MobileAboutScreen.dart';
import 'package:client/Screens/MobileDownloadScreen.dart';
import 'package:client/Screens/WebSwimmerHistoryScreen.dart';
import 'package:client/Screens/Arguments/CoachScreenArguments.dart';
import 'package:client/Screens/Arguments/MultiReportScreenArguments.dart';
import 'package:client/Screens/Arguments/ResearcherScreenArguments.dart';
import 'package:client/Screens/WebAboutScreen.dart';
import 'package:client/Screens/WebCoachScreen.dart';
import 'package:client/Screens/WebDownloadScreen.dart';
import 'package:client/Screens/WebMultiReportsScreen.dart';
import 'package:client/Screens/WebResearcherScreen.dart';
import 'package:flutter/foundation.dart';
import 'Arguments/ReportScreenArguments.dart';
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

  Widget getAboutScreen(AboutScreenArguments args) {
    if(args == null) {
      args = new AboutScreenArguments();
    }
    if(isUserViewWebFromMobile()) {
      return new MobileAboutScreen(args);
    }
    return new WebAboutScreen(args);
  }

  Widget getDownloadsScreen() {
    if(isUserViewWebFromMobile()) {
      return new MobileDownloadScreen();
    }
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
    return new WebResearcherScreen(args);
  }

  Widget getReportScreen(ReportScreenArguments args) {
    return new WebReportScreen(args: args);
  }

  Widget getMultiReportScreen(MultiReportScreenArguments args) {
     return new WebMultiReportsScreen(args);
  }

  Widget getCoachScreen(CoachScreenArguments args) {
    return new WebCoachScreen(args);
  }


  Widget getSwimmerHistoryScreen(SwimmerScreenArguments args) {
    return new WebSwimmerHistoryScreen(arguments: args,);
  }

  Widget getSwimmerHistoryDayScreen(SwimmerHistoryPoolsArguments args) {
    return new WebSwimmerHistoryDayScreen(arguments: args,);
  }

  Widget getViewFeedbackScreen(ViewFeedBackArguments args) {
    return new WebViewFeedbackScreen(arguments: args,);
  }

  bool isUserViewWebFromMobile() {
    return (defaultTargetPlatform == TargetPlatform.iOS
        || defaultTargetPlatform == TargetPlatform.android);
  }

}