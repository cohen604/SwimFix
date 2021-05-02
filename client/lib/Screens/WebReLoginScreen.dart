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
import 'package:client/Screens/WebSwimmerScreen.dart';
import 'package:client/Services/GoogleAuth.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'PopUps/MessagePopUp.dart';

class WebReLoginScreen extends StatefulWidget {

  ReLoginScreenArguments args;

  WebReLoginScreen({Key key, this.args}) : super(key: key);

  @override
  _WebReLoginScreenState createState() => _WebReLoginScreenState();

}

class _WebReLoginScreenState extends State<WebReLoginScreen> {

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
      // TODO get a team name
      String teamName = 'Need to get team name from relogin screen';
      args = new CoachScreenArguments(user, teamName);
    }
    else if(prefix == '/history') {

    }
    else if(prefix == '') {

    }
    Navigator.pushNamed(context, prefix, arguments: args);
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

  Widget buildLogo(BuildContext context) {
    return Container();
  }

  Widget buildLoginArea(BuildContext context) {
    return Container();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Center(
        child: Card(
          child: Container(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              mainAxisSize: MainAxisSize.min,
              children: [
                buildLogo(context),
                buildLoginArea(context),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
