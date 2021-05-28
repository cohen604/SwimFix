import 'package:client/Screens/AdminScreens/Arguments/AddAdminsScreenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/AddResearcherScreenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/StatisticsScreenArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/InvitationHistoryArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/PendingInvitationsArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/SwimmerOpenTeamArguments.dart';
import 'Screens/AdminScreens/Arguments/AdminSrceenArguments.dart';
import 'Screens/Arguments/AboutScreenArguments.dart';
import 'Screens/CoachScreens/Arguments/CoachScreenArguments.dart';
import 'Screens/SwimmersScreens/Arguments/HistoryScreenArguments.dart';
import 'Screens/ResearcherScreens/Arguments/MultiReportScreenArguments.dart';
import 'Screens/ResearcherScreens/Arguments/ReportScreenArguments.dart';
import 'Screens/ResearcherScreens/Arguments/ResearcherScreenArguments.dart';
import 'Screens/SwimmersScreens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'Screens/SwimmersScreens/Arguments/SwimmerScreenArguments.dart';
import 'Screens/SwimmersScreens/Arguments/UploadScreenArguments.dart';
import 'Screens/Arguments/WelcomeScreenArguments.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'Screens/Holders/ScreensHolder.dart';
import 'Screens/Holders/WebColors.dart';
import 'Screens/SwimmersScreens/Arguments/ViewFeedbackArguments.dart';

/// if running from web:localhost add to project arguments --web-host 5000
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {

  ScreenHolder _screenHolder = new ScreenHolder();
  WebColors _webColors = new WebColors();


  MyApp() {
    _screenHolder = new ScreenHolder();
    _webColors = WebColors.getInstance();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SwimFix',
      initialRoute: '/', //
      theme: ThemeData(
        primarySwatch: _webColors.createMaterialColor(
            _webColors.getBackgroundForI1()
        ),
      ),
      routes: {
        '/': (context) {
          AboutScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getAboutScreen(args);
        },
        '/downloads': (context) {
          return _screenHolder.getDownloadsScreen();
        },
        '/welcome': (context) {
          WelcomeScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getWelcomeScreen(args);
        },
        '/swimmer': (context) {
          SwimmerScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getSwimmerScreen(args);
        },
        '/upload': (context) {
          UploadScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getUploadScreen(args);
        },
        '/history': (context) {
          HistoryScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getSwimmerHistoryScreen(args);
        },
        '/history/day': (context) {
          SwimmerHistoryPoolsArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getSwimmerHistoryDayScreen(args);
        },
        '/viewFeedback': (context) {
          ViewFeedBackArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getViewFeedbackScreen(args);
        },
        '/swimmer/open/team': (context) {
          SwimmerOpenTeamArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getSwimmerOpenTeamScreen(args);
        },
        '/swimmer/invitations': (context) {
          PendingInvitationsArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getPendingInvitationsScreen(args);
        },
        '/swimmer/invitations/history': (context) {
          InvitationHistoryArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getInvitationsHistoryScreen(args);
        },
        '/researcher': (context) {
          ResearcherScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getResearcherScreen(args);
        },
        '/researcher/report': (context) {
          ReportScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getReportScreen(args);
        },
        '/researcher/multireport': (context) {
          MultiReportScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getMultiReportScreen(args);
        },
        '/coach': (context) {
          CoachScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getCoachScreen(args);
        },
        '/admin': (context) {
          AdminScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getAdminScreen(args);
        },
        '/admin/add/admins': (context) {
          AddAdminsScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getAddAdminScreen(args);
        },
        '/admin/add/researchers': (context) {
          AddResearcherScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getAddResearcherScreen(args);
        },
        '/admin/statistics': (context) {
          StatisticsScreenArguments args = ModalRoute.of(context).settings.arguments;
          return _screenHolder.getStatisticsScreen(args);
        }
      },
      debugShowCheckedModeBanner: false,
    );
  }
}