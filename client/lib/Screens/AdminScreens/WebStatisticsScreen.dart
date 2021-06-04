import 'package:client/Components/Buttons/IconCardButton.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Summaries/Summary.dart';
import 'package:client/Screens/AdminScreens/Arguments/AddAdminsScreenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/AddResearcherScreenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/StatisticsScreenArguments.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

import 'Arguments/AdminSrceenArguments.dart';

class WebStatisticsScreen extends StatefulWidget {

  StatisticsScreenArguments args;

  WebStatisticsScreen(this.args);

  @override
  _WebStatisticsScreenState createState() => _WebStatisticsScreenState();
}

class _WebStatisticsScreenState extends State<WebStatisticsScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;
  LogicManager _logicManager;
  ScreenState _screenState;
  Summary _summary;

  _WebStatisticsScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _logicManager = LogicManager.getInstance();
  }


  @override
  void initState() {
    super.initState();
    _screenState = ScreenState.Loading;
    _logicManager.getSummary(this.widget.args.user.swimmer).then(
        (Summary summary) {
          if(summary == null) {
            this.setState(() {
              _screenState = ScreenState.Error;
            });
          }
          else {
            this.setState(() {
              _summary = summary;
              _screenState = ScreenState.View;
            });
          }
        }
    );
  }

  Widget buildText(
      BuildContext context,
      String text,
      int size,
      Color color,
      FontWeight fontWeight,
      {textAlign = TextAlign.left}) {
    return Text(text,
      textAlign: textAlign,
      style: TextStyle(
          fontSize: size * MediaQuery.of(context).textScaleFactor,
          color: color,
          fontWeight: fontWeight,
          decoration: TextDecoration.none
      ),
    );
  }

  Widget buildLoadingState(BuildContext context) {
    return Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            CircularProgressIndicator(),
            SizedBox(height: 5.0,),
            buildText(context, 'Loading summary...', 24, Colors.black, FontWeight.normal),
          ],
        )
    );
  }

  Widget buildErrorState(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Icon(
          Icons.warning,
          size: 35,
          color: Colors.red,
        ),
        SizedBox(height: 5,),
        buildText(context, 'Something broken.\n'
            'Maybe you don\'t have permessions to that action or the servers are down.\n'
            'For more information contact swimanalytics@gmail.com', 24, Colors.black, FontWeight.normal),
        SizedBox(height: 5,),
      ],
    );
  }

  String getDisplayInt(int number) {
    if(number == null) {
      return 'Undefined';
    }
    return '$number';
  }

  String getDisplayDouble(double number) {
    if(number == null) {
      return 'Undefined';
    }
    return '$number';
  }

  Widget buildUsersSummary(BuildContext context, String title, String total, String online, String offline) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        buildText(context, '$title: $total', 24, Colors.black, FontWeight.normal),
        SizedBox(width: 10,),
        Icon(
          Icons.circle,
          size: 24,
          color: Colors.green,
        ),
        buildText(context, '$online', 24, Colors.black, FontWeight.normal),
        SizedBox(width: 5,),
        Icon(
          Icons.circle,
          size: 24,
          color: Colors.red,
        ),
        buildText(context, '$offline', 24, Colors.black, FontWeight.normal),
      ],
    );
  }

  Widget buildFeedbackSummary(BuildContext context) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        buildText(context, 'Feedbacks: ${getDisplayInt(_summary.feedbacks)},', 24, Colors.black, FontWeight.normal),
        SizedBox(width: 5,),
        buildText(context, 'per swimmer ${getDisplayDouble(_summary.feedbacksPerSwimmer())}', 24, Colors.black, FontWeight.normal),
      ],
    );
  }

  Widget buildViewState(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          buildText(context, 'Summary', 36, _webColors.getBackgroundForI2(), FontWeight.normal),
          buildUsersSummary(context, 'Users',
            getDisplayInt(_summary.users),
            getDisplayInt(_summary.loggedUsers),
            getDisplayInt(_summary.offlineUsers())),
          buildUsersSummary(context, 'Swimmers',
              getDisplayInt(_summary.swimmers),
              getDisplayInt(_summary.loggedSwimmers),
              getDisplayInt(_summary.offlineSwimmers())),
          buildUsersSummary(context, 'Coaches',
              getDisplayInt(_summary.coaches),
              getDisplayInt(_summary.loggedCoaches),
              getDisplayInt(_summary.offlineCoaches())),
          buildUsersSummary(context, 'Researchers',
              getDisplayInt(_summary.researchers),
              getDisplayInt(_summary.loggedResearchers),
              getDisplayInt(_summary.offlineResearchers())),
          buildUsersSummary(context, 'Admins',
              getDisplayInt(_summary.admins),
              getDisplayInt(_summary.loggedAdmins),
              getDisplayInt(_summary.offlineAdmins())),
          buildFeedbackSummary(context),
        ],
      ),
    );
  }

  Widget buildState(BuildContext context) {
    if(_screenState == ScreenState.Loading) {
      return buildLoadingState(context);
    }
    else if(_screenState == ScreenState.Error) {
      return buildErrorState(context);
    }
    else if(_screenState == ScreenState.View) {
      return buildViewState(context);
    }
    return Container();
  }

  Widget buildMainArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getBackGroundImage()),
          fit: BoxFit.fill,
        ),
      ),
      child: buildState(context),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Column(
          children: [
            MenuBar(user: this.widget.args.user,),
            Expanded(
                child: buildMainArea(context)
            ),
          ],
        ),
      ),
    );
  }
}

enum ScreenState {
  Loading,
  Error,
  View
}
