import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/Arguments/ReLoginScreenArguments.dart';
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

  void navigation() {
    WelcomeScreenArguments args = new WelcomeScreenArguments(
        new WebUser(swimmer, permissions));
    Navigator.pushNamed(context, "/welcome",  arguments: args);
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
              navigation();
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
