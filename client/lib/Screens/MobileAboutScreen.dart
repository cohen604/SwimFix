import 'package:client/Components/AboutScreenMenuBar.dart';
import 'package:client/Components/MobileAboutScreenMenuBar.dart';
import 'package:client/Components/SimpleVideoPlayer.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/WebColors.dart';
import 'package:client/Services/GoogleAuth.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

import 'Arguments/AboutScreenArguments.dart';
import 'Arguments/WelcomeScreenArguments.dart';
import 'PopUps/MessagePopUp.dart';

class MobileAboutScreen extends StatefulWidget {

  AboutScreenArguments args;

  MobileAboutScreen(this.args);

  @override
  _MobileAboutScreenState createState() => _MobileAboutScreenState(
      args.videoOn, args.loginOn, args.aboutOn);

}

class _MobileAboutScreenState extends State<MobileAboutScreen> {

  WebColors _webColors;
  ScreenState state;

  _MobileAboutScreenState(bool videoOn, bool loginOn, bool aboutOn) {
    _webColors = new WebColors();
    if(videoOn && !loginOn) {
      state = ScreenState.Video;
    }
    else {
      state = ScreenState.Login;
    }
  }

  @override
  void initState() {
    super.initState();
    // _animationController = new AnimationController(
    //   duration: Duration(seconds: 2, milliseconds: 30),
    //   vsync: this,
    // );
  }

  @override
  void dispose() {
    super.dispose();
    // _animationController.dispose();
  }

  void onSignUp() {
    this.setState(() {
      state = ScreenState.Login;
    });
  }

  void onLogo() {
    this.setState(() {
      state = ScreenState.Video;
    });
  }

  void onLogin() {
    this.setState(() {
      state = ScreenState.Login;
    });
  }

  Function onDownload(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/downloads',);
        });
      });
    };
  }

  Widget buildText(BuildContext context,
      String text,
      double fontSize,
      { Color color = Colors.white,
        FontWeight fontWeight = FontWeight.normal}) {
    return Text(text,
        textAlign: TextAlign.left,
        style: TextStyle(
            fontSize: fontSize * MediaQuery.of(context).textScaleFactor,
            color: color,
            fontWeight: fontWeight,
            decoration: TextDecoration.none
        ));
  }

  Widget buildDesArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      padding: EdgeInsets.only(left: 30),
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage('assets/images/about_screen_background.png'),
          fit: BoxFit.cover,
          colorFilter: ColorFilter.mode(Colors.black.withAlpha(120), BlendMode.darken),
        ),
      ),
      child: Column(
        children: [
          Flexible(
              fit: FlexFit.tight,
              child: Container(
                  alignment: Alignment.bottomLeft,
                  child: buildText(context, "Swim Analytics", 84,
                      fontWeight: FontWeight.bold))
          ),
          Container(
              alignment: Alignment.centerLeft,
              child: buildText(
                context, "Swimming training solutions", 36,
              )
          ),
          Expanded(
            child: Container(
              alignment: Alignment.topLeft,
              child: buildText(context,"The best platform to improve your swimming abilities.\n"
                  "The platform allows any swimmer to record, view, train,\n"
                  "receive swimming videos, feedback's and many more.\n", 21,),
            ),
          ),
        ],
      ),
    );
  }

  Widget buildVideoArea(BuildContext context) {
    return Container(
      // width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage('assets/images/about_screen_background.png'),
          fit: BoxFit.cover,
          colorFilter: ColorFilter.mode(Colors.black.withAlpha(120), BlendMode.darken),
        ),
      ),
      child: Container(
        margin: EdgeInsets.all(20.0),
        child: SimpleVideoPlayer('assets/videos/intro.mp4')),
    );
  }

  Widget buildSupportArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height / 4,
      color: _webColors.getBackgroundForI3(),
      child: Row(
        children: [
          Flexible(
              fit: FlexFit.tight,
              child: Center(
                  child: buildText(context, "Web", 36,
                      color: Colors.black,
                      fontWeight: FontWeight.bold)
              )
          ),
          Flexible(
              fit: FlexFit.tight,
              child: Center(
                  child: buildText(context, "Mobile", 36,
                      color: Colors.black,
                      fontWeight: FontWeight.bold)
              )
          ),
        ],
      ),
    );
  }

  Widget buildAreas(BuildContext context) {
    return Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        color: _webColors.getBackgroundForI6(),
        child: Scrollbar(
          child: SingleChildScrollView(
            child: Column(
              children: [
                buildDesArea(context),
                buildVideoArea(context),
                buildSupportArea(context),
              ],
            ),
          ),
        )
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
            MobileAboutScreenMenuBar(
              onLogo: onLogo,
              onDownload: onDownload(context),
            ),
            Expanded(
                child: buildAreas(context)
            ),
          ],
        ),
      ),
    );
  }

}

enum ScreenState {
  Video,
  Login,
  About,
}