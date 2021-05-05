import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/UserPermissions.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/Arguments/CoachScreenArguments.dart';
import 'package:client/Screens/Arguments/MultiReportScreenArguments.dart';
import 'package:client/Screens/Arguments/ReLoginScreenArguments.dart';
import 'package:client/Screens/Arguments/ReportScreenArguments.dart';
import 'package:client/Screens/Arguments/ResearcherScreenArguments.dart';
import 'package:client/Screens/Arguments/SwimmerScreenArguments.dart';
import 'package:client/Screens/Arguments/UploadScreenArguments.dart';
import 'package:client/Screens/Arguments/WelcomeScreenArguments.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'Arguments/HistoryScreenArguments.dart';
import 'PopUps/MessagePopUp.dart';
import 'Holders/WebColors.dart';

class WebReLoginScreen extends StatefulWidget {

  ReLoginScreenArguments args;

  WebReLoginScreen({Key key, this.args}) : super(key: key);

  @override
  _WebReLoginScreenState createState() => _WebReLoginScreenState();

}

class _WebReLoginScreenState extends State<WebReLoginScreen> {

  LogicManager _logicManager;
  WebColors _webColors;
  AssetsHolder _assetsHolder;

  _WebReLoginScreenState() {
    _logicManager = LogicManager.getInstance();
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
  }

  //TODO better solution is to refactor this to class arguments maker
  void navigation(Swimmer swimmer, UserPermissions permissions) {
    dynamic args;
    String prefix = this.widget.args.desPath;
    WebUser user = new WebUser(swimmer, permissions);
    if(prefix == '/welcome') {
      args = new WelcomeScreenArguments(user);
    }
    else if(prefix == '/swimmer') {
      args = new SwimmerScreenArguments(user);
    }
    else if(prefix == '/upload') {
      args = new UploadScreenArguments(user);
    }
    else if(prefix == '/researcher') {
      args = new ResearcherScreenArguments(user);
    }
    else if(prefix == '/researcher/report') {
      args = new ReportScreenArguments(user);
    }
    else if(prefix == '/researcher/multireport') {
      args = new MultiReportScreenArguments(user);
    }
    else if(prefix == '/coach') {
      //TODO add support to coach screen when receive no team name
      args = new CoachScreenArguments(user, null);
    }
    else if(prefix == '/history') {
      args = new HistoryScreenArguments(user);
    }
    Navigator.pushNamed(context, prefix, arguments: args);
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
                navigation(swimmer, permissions);
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

  Widget buildText(BuildContext context,
      String text,
      double fontSize,
      { Color color = Colors.white,
        FontWeight fontWeight = FontWeight.normal,
        TextAlign textAlign = TextAlign.left}) {
    return Text(text,
        textAlign: textAlign,
        style: TextStyle(
            fontSize: fontSize * MediaQuery.of(context).textScaleFactor,
            color: color,
            fontWeight: fontWeight,
            decoration: TextDecoration.none,
        ));
  }

  Widget buildLogo(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(top:10, bottom: 8),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          buildText(context, 'Swim Analytics', 45,
            color: Colors.white,
            fontWeight: FontWeight.bold,
            textAlign: TextAlign.center,
          ),
          buildText(context, 'For access this page, please sign in.', 21,
            color: Colors.white,
            textAlign: TextAlign.center,
          )
        ],
      )
    );
  }

  Widget buildLoginWithGoogle(context) {
    return Center(
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
    );
  }

  Widget buildLoginArea(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: _webColors.getBackgroundForI3(),
        borderRadius: BorderRadius.only(
          bottomLeft: Radius.circular(20),
          bottomRight: Radius.circular(20),
        )
      ),
      child: buildLoginWithGoogle(context),
    );
  }

  @override
  Widget build(BuildContext context) {
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
      padding: EdgeInsets.all(10),
      child: Center(
        child: Container(
          width: MediaQuery.of(context).size.width / 2,
          height: MediaQuery.of(context).size.height / 2,
          decoration: BoxDecoration(
            color: _webColors.getBackgroundForI2().withAlpha(150),
            borderRadius: BorderRadius.all(Radius.circular(20)),
            border: Border.all(color: Colors.black, width: 3)
          ),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            mainAxisSize: MainAxisSize.min,
            children: [
              buildLogo(context),
              Expanded(
                  child: buildLoginArea(context)
              ),
            ],
          ),
        ),
      ),
    );
  }
}
