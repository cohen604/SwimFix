import 'package:client/Components/AboutScreenMenuBar.dart';
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

class WebAboutScreen extends StatefulWidget {

  AboutScreenArguments args;

  WebAboutScreen(this.args);

  @override
  _WebAboutScreenState createState() => _WebAboutScreenState(
    args.videoOn, args.loginOn, args.aboutOn);

}

class _WebAboutScreenState extends State<WebAboutScreen> {

  WebColors _webColors;
  ScreenState state;

  _WebAboutScreenState(bool videoOn, bool loginOn, bool aboutOn) {
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

  void signInWithGoogle() async {
    GoogleAuth googleAuth = new GoogleAuth();
    User user = await googleAuth.signIn();
    String name = user.displayName;
    String email = user.email;
    String uid = user.uid;
    Swimmer swimmer = new Swimmer(uid, email, name);
    LogicManager logicManager = LogicManager.getInstance();
    logicManager.login(swimmer).then((logged) {
      if (logged) {
        logicManager.getPermissions(swimmer).then((permissions) {
          if(permissions != null) {
            this.setState(() {
              WelcomeScreenArguments args = new WelcomeScreenArguments(
                  new WebUser(swimmer, permissions));
              Navigator.pushNamed(context, "/welcome",  arguments: args);
            });
          }
          else {

            showDialog(
              context: context,
              builder: (BuildContext context) {
                return new MessagePopUp('User don\'t have permissions.\n'
                    'For more information please contact help@swimanalytics.com');
              },
            );
          }
        });
      }
      else {
        showDialog(
          context: context,
          builder: (BuildContext context) {
            return new MessagePopUp('Something is broken.\n'
                'Maybe Your Credentials aren\'t correct or the servers are down.\n'
                'For more information contact swimAnalytics@gmail.com');
          },
        );
      }
    });
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

  Widget buildLeftLoginArea(BuildContext context) {
    return Container(
      // width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      padding: EdgeInsets.only(left: 30),
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


  Widget buildRightLoginArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: AnimatedCrossFade(
        crossFadeState: state == ScreenState.Login ? CrossFadeState.showSecond:  CrossFadeState.showFirst,
        duration: Duration(seconds: 1),
        firstChild: buildRightLoginArea1(context),
        secondChild: buildRightLoginArea2(context),
      ),
    );
  }

  Widget buildSignUpButton(BuildContext context) {
    if(state == ScreenState.Login) {
      return Container();
    }
    return Container(
        padding: EdgeInsets.only(right:20),
        alignment: Alignment.topRight,
        child: ElevatedButton(
          style: ButtonStyle(
            shadowColor: MaterialStateColor.resolveWith((states) =>Colors.black),
            backgroundColor: MaterialStateColor.resolveWith((states) => _webColors.getBackgroundForI1()),
            shape: MaterialStateProperty.resolveWith((states) => RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(30.0),
            )),
          ),
          onPressed: onSignUp,
          child: Container(
              padding: EdgeInsets.all(5),
              child: buildText(context, 'Sign Up', 28,
                  fontWeight: FontWeight.bold)
          ),
        )
    );
  }

  Widget buildRightLoginArea1(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Column(
        children: [
          Flexible(
            flex: 4,
            child: Container(
              margin: EdgeInsets.all(20.0),
              child: SimpleVideoPlayer('assets/videos/intro.mp4')),
          ),
          Flexible(
            child: buildSignUpButton(context),
          )
        ],
      ),
    );
  }

  Widget buildLoginWithGoogle(context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Center(
        child: Card(
          color: _webColors.getBackgroundForI7(),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(20.0),
          ),
          child: InkWell(
            borderRadius: BorderRadius.circular(20.0),
            splashColor: Theme.of(context).splashColor,
            onTap: signInWithGoogle,
            child: Padding(
              padding: const EdgeInsets.all(10.0),
              child: ListTile(
                title: buildText(context, "Sign In with Google", 21,
                    color: Colors.black),
                leading: Image(image: AssetImage("assets/google_logo.png")),
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget buildRightLoginArea2(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(left: 50 ,right: 50),
      padding: EdgeInsets.all(20),
      child: buildLoginWithGoogle(context),
    );
  }

  Widget buildLoginArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage('assets/images/about_screen_background.png'),
          fit: BoxFit.cover,
          colorFilter: ColorFilter.mode(Colors.black.withAlpha(120), BlendMode.darken),
        ),
      ),
      child: Row(
        children: [
          Flexible(
            child: buildLeftLoginArea(context)
          ),
          Flexible(
            child: buildRightLoginArea(context),
          )
        ],
      ),
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
                buildLoginArea(context),
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
            AboutScreenMenuBar(
              onLogo: onLogo,
              onLogin: onSignUp,
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