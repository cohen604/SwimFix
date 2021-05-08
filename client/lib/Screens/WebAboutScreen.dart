import 'package:client/Components/MenuBars/AboutScreenMenuBar.dart';
import 'package:client/Components/VideoPlayers/SimpleVideoPlayer.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'Arguments/AboutScreenArguments.dart';
import 'Arguments/WelcomeScreenArguments.dart';
import 'Holders/WebColors.dart';
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
  LogicManager _logicManager;
  AssetsHolder _assetsHolder;

  _WebAboutScreenState(bool videoOn, bool loginOn, bool aboutOn) {
    _logicManager = LogicManager.getInstance();
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
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
    User user = await _logicManager.signInWithGoogle();
    if(user!=null) {
      String name = user.displayName;
      String email = user.email;
      String uid = user.uid;
      Swimmer swimmer = new Swimmer(uid, email, name);
      LogicManager logicManager = LogicManager.getInstance();
      logicManager.login(swimmer).then((logged) {
        if (logged) {
          logicManager.getPermissions(swimmer).then((permissions) {
            if (permissions != null) {
              this.setState(() {
                WelcomeScreenArguments args = new WelcomeScreenArguments(
                    new WebUser(swimmer, permissions));
                Navigator.pushNamed(context, "/welcome", arguments: args);
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
  }

  void loginWithGoogle() {

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
              child: SimpleVideoPlayer(_assetsHolder.getIntroVideo())),
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
                leading: Image(image: AssetImage(_assetsHolder.getGoogleIcon())),
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
          image: AssetImage(_assetsHolder.getSwimmerBackGround()),
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
      height: MediaQuery.of(context).size.height / 6,
      color: _webColors.getBackgroundForI3(),
      child: Row(
        children: [
          Flexible(
            fit: FlexFit.tight,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(Icons.web_outlined),
                buildText(context, "Web", 24,
                    color: Colors.black,
                    fontWeight: FontWeight.bold),
              ],
            )
          ),
          Flexible(
            fit: FlexFit.tight,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(Icons.mobile_friendly),
                buildText(context, "Mobile", 24,
                    color: Colors.black,
                    fontWeight: FontWeight.bold),
              ],
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