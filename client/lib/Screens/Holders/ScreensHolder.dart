import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/AdminScreens/Arguments/AddAdminsScreenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/AddResearcherScreenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/AdminSrceenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/StatisticsScreenArguments.dart';
import 'package:client/Screens/AdminScreens/WebAddAdminsScreen.dart';
import 'package:client/Screens/AdminScreens/WebAddResearchersScreen.dart';
import 'package:client/Screens/AdminScreens/WebAdminScreen.dart';
import 'package:client/Screens/AdminScreens/WebStatisticsScreen.dart';
import 'package:client/Screens/Arguments/AboutScreenArguments.dart';
import 'package:client/Screens/Arguments/NoPermissionScreenArguments.dart';
import 'package:client/Screens/Arguments/ReLoginScreenArguments.dart';
import 'package:client/Screens/Arguments/WelcomeScreenArguments.dart';
import 'package:client/Screens/CoachScreens/Arguments/CoachScreenArguments.dart';
import 'package:client/Screens/CoachScreens/Arguments/CoachSwimmerScreenArguments.dart';
import 'package:client/Screens/CoachScreens/WebCoachScreen.dart';
import 'package:client/Screens/CoachScreens/WebCoachSwimmerScreen.dart';
import 'package:client/Screens/MobileAboutScreen.dart';
import 'package:client/Screens/MobileDownloadScreen.dart';
import 'package:client/Screens/ResearcherScreens/Arguments/MultiReportScreenArguments.dart';
import 'package:client/Screens/ResearcherScreens/Arguments/ReportScreenArguments.dart';
import 'package:client/Screens/ResearcherScreens/Arguments/ResearcherScreenArguments.dart';
import 'package:client/Screens/ResearcherScreens/WebMultiReportsScreen.dart';
import 'package:client/Screens/ResearcherScreens/WebReportScreen.dart';
import 'package:client/Screens/ResearcherScreens/WebResearcherScreen.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/HistoryScreenArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/InvitationHistoryArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/MyTeamArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/PendingInvitationsArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/SwimmerOpenTeamArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/SwimmerScreenArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/UploadScreenArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/ViewFeedbackArguments.dart';
import 'package:client/Screens/SwimmersScreens/WebInvitationHistoryScreen.dart';
import 'package:client/Screens/SwimmersScreens/WebMyTeamScreen.dart';
import 'package:client/Screens/SwimmersScreens/WebPendingInvitationsScreen.dart';
import 'package:client/Screens/SwimmersScreens/WebSwimmerHistoryDayScreen.dart';
import 'package:client/Screens/SwimmersScreens/WebSwimmerHistoryScreen.dart';
import 'package:client/Screens/SwimmersScreens/WebSwimmerOpenTeamScreen.dart';
import 'package:client/Screens/SwimmersScreens/WebSwimmerScreen.dart';
import 'package:client/Screens/SwimmersScreens/WebUploadScreen.dart';
import 'package:client/Screens/SwimmersScreens/WebViewFeedbackScreen.dart';
import 'package:client/Screens/WebNoPermissionScreen.dart';
import 'package:client/Screens/WebReLoginScreen.dart';
import 'package:client/Screens/WebAboutScreen.dart';
import 'package:client/Screens/WebDownloadScreen.dart';
import 'package:flutter/foundation.dart';
import 'package:client/Screens/WebWelcomeScreen.dart';
import 'package:flutter/cupertino.dart';

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
     if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/welcome');
    }
    return new WebWelcomeScreen(args: args);
  }

  Widget getSwimmerScreen(SwimmerScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/swimmer');
    }
    if(!args.user.permissions.isSwimmer) {
      return getNoPermissionScreen(args.user);
    }
    return new WebSwimmerScreen(arguments: args,);
  }

  Widget getUploadScreen(UploadScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/upload');
    }
    if(!args.user.permissions.isSwimmer) {
      return getNoPermissionScreen(args.user);
    }
    return new WebUploadScreen(args: args,);
  }

  Widget getResearcherScreen(ResearcherScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/researcher');
    }
    if(!args.user.permissions.isResearcher) {
      return getNoPermissionScreen(args.user);
    }
    return new WebResearcherScreen(args);
  }

  Widget getReportScreen(ReportScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/researcher/report');
    }
    if(!args.user.permissions.isResearcher) {
      return getNoPermissionScreen(args.user);
    }
    return new WebReportScreen(args: args);
  }

  Widget getMultiReportScreen(MultiReportScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/researcher/multireport');
    }
    if(!args.user.permissions.isResearcher) {
      return getNoPermissionScreen(args.user);
    }
    return new WebMultiReportsScreen(args);
  }

  Widget getCoachScreen(CoachScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/coach');
    }
    if(!args.user.permissions.isCoach) {
      return getNoPermissionScreen(args.user);
    }
    return new WebCoachScreen(args);
  }

  Widget getSwimmerHistoryScreen(HistoryScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/history');
    }
    if(!args.user.permissions.isSwimmer) {
      return getNoPermissionScreen(args.user);
    }
    return new WebSwimmerHistoryScreen(arguments: args,);
  }

  Widget getSwimmerHistoryDayScreen(SwimmerHistoryPoolsArguments args) {
    if(args == null || args.webUser == null || args.webUser.swimmer == null) {
      return getReLoginScreen('/history');
    }
    if(!args.webUser.permissions.isSwimmer) {
      return getNoPermissionScreen(args.webUser);
    }
    return new WebSwimmerHistoryDayScreen(arguments: args,);
  }

  Widget getViewFeedbackScreen(ViewFeedBackArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/history');
    }
    if(!args.user.permissions.isSwimmer) {
      return getNoPermissionScreen(args.user);
    }
    return new WebViewFeedbackScreen(arguments: args,);
  }

  Widget getAdminScreen(AdminScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/admin');
    }
    if(!args.user.permissions.isAdmin) {
      return getNoPermissionScreen(args.user);
    }
    return new WebAdminScreen(args);
  }

  Widget getAddAdminScreen(AddAdminsScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/admin');
    }
    if(!args.user.permissions.isAdmin) {
      return getNoPermissionScreen(args.user);
    }
    return new WebAddAdminsScreen(args);
  }

  Widget getAddResearcherScreen(AddResearcherScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/admin');
    }
    if(!args.user.permissions.isAdmin) {
      return getNoPermissionScreen(args.user);
    }
    return new WebAddResearchersScreen(args);
  }

  Widget getStatisticsScreen(StatisticsScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/admin');
    }
    if(!args.user.permissions.isAdmin) {
      return getNoPermissionScreen(args.user);
    }
    return new WebStatisticsScreen(args);
  }

  Widget getSwimmerOpenTeamScreen(SwimmerOpenTeamArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/swimmer');
    }
    if(!args.user.permissions.isSwimmer) {
      return getNoPermissionScreen(args.user);
    }
    return new WebSwimmerOpenTeamScreen(args);
  }

  Widget getPendingInvitationsScreen(PendingInvitationsArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/swimmer');
    }
    if(!args.user.permissions.isSwimmer) {
      return getNoPermissionScreen(args.user);
    }
    return new WebPendingInvitationsScreen(args);
  }

  Widget getInvitationsHistoryScreen(InvitationHistoryArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/swimmer');
    }
    if(!args.user.permissions.isSwimmer) {
      return getNoPermissionScreen(args.user);
    }
    return new WebInvitationHistoryScreen(args);
  }

  Widget getMyTeamScreen(MyTeamArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/swimmer');
    }
    if(!args.user.permissions.isSwimmer) {
      return getNoPermissionScreen(args.user);
    }
    return new WebMyTeamScreen(args);
  }


  Widget getCoachSwimmerScreen(CoachSwimmerScreenArguments args) {
    if(args == null || args.user == null || args.user.swimmer == null) {
      return getReLoginScreen('/coach');
    }
    if(!args.user.permissions.isCoach) {
      return getNoPermissionScreen(args.user);
    }
    return new WebCoachSwimmerScreen(args);
  }

  Widget getReLoginScreen(String desPath) {
    ReLoginScreenArguments args = new ReLoginScreenArguments(desPath);
    return new WebReLoginScreen(args: args);
  }

  Widget getNoPermissionScreen(WebUser user) {
    NoPermissionScreenArguments args = new NoPermissionScreenArguments(user);
    return new WebNoPermissionScreen(args: args,);
  }

  bool isUserViewWebFromMobile() {
    return (defaultTargetPlatform == TargetPlatform.iOS
        || defaultTargetPlatform == TargetPlatform.android);
  }

}