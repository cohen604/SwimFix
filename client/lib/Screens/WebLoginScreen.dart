import 'package:client/Domain/Users/Swimmer.dart';

import 'Arguments/WelcomeScreenArguments.dart';
import 'package:client/Services/GoogleAuth.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Screens/PopUps/MessagePopUp.dart';
import 'package:client/Screens/WebColors.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class WebLoginScreen extends StatefulWidget {

  WebLoginScreen({Key key}) : super(key: key);

  @override
  _WebLoginScreenState createState() => _WebLoginScreenState();
}

class _WebLoginScreenState extends State<WebLoginScreen> {

  WebColors _webColors = new WebColors();

  void signInWithGoogle() async {
    GoogleAuth googleAuth = new GoogleAuth();
    User user = await googleAuth.signIn();
    String name = user.displayName;
    String email = user.email;
    String uid = user.uid;
    Swimmer swimmer = new Swimmer(uid, email, name);
    LogicManager.getInstance().login(swimmer).then((logged) {
      if (logged) {
        this.setState(() {
          WelcomeScreenArguments args = new WelcomeScreenArguments(swimmer);
          Navigator.pushNamed(context, "/welcome",  arguments: args);
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

  Widget buildLoginWithGoogleMobile(context) {
    return Center(
      child: Card(
        color: Colors.white,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10.0),
        ),
        child: InkWell(
          borderRadius: BorderRadius.circular(10.0),
          splashColor: Theme.of(context).splashColor,
          onTap: signInWithGoogle,
          child: Padding(
            padding: const EdgeInsets.all(5.0),
            child: ListTile(
              title: Text("Sign In with Google"),
              leading: Image(image: AssetImage("assets/google_logo.png")),
            ),
          ),
        ),
      ),
    );
  }

  Widget buildTitle(context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.loose,
      child: Container(
        width:  MediaQuery.of(context).size.width,
        child: Center(
          child: Text( "Swim Analytics",
              style: TextStyle(
                fontSize: 80 * MediaQuery.of(context).textScaleFactor,
                color:Colors.white,
                fontWeight: FontWeight.bold,
                fontStyle: FontStyle.italic)

          ),
        ),
      ),
    );
  }

  Widget buildSwimAnalyticsInfo(context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Text("The best swimming solutions for you!!!",
          style: TextStyle(fontSize: 24 * MediaQuery.of(context).textScaleFactor, color:Colors.white))
    );
  }

  Widget buildRunningPictures(context, int flex) {
    return Flexible(
        flex: flex,
        fit: FlexFit.tight,
        child: Container(
          margin: EdgeInsets.only(right: 20.0, left: 20.0, bottom: 20.0),
          width:  MediaQuery.of(context).size.width,
          color: _webColors.getBackgroundForI4(),
          child: Text("Here will be a video about swim analytics.",
              style: TextStyle(fontSize: 20 * MediaQuery.of(context).textScaleFactor, color:Colors.black)),
        )
    );
  }

  Widget buildLeftSide(context, int flex) {
    return  Flexible(
      flex: flex,
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.only(topLeft: Radius.circular(20.0),
              bottomLeft: Radius.circular(20.0)),
          color: _webColors.getBackgroundForI1(),
        ),
        child: Column( children: [
          buildTitle(context, 3),
          buildSwimAnalyticsInfo(context, 1),
          buildRunningPictures(context, 4),
        ]),
      ),
    );
  }

  Widget buildRightSide(context, int flex) {
    return Flexible(
      flex: flex,
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.only(topRight: Radius.circular(20.0),
              bottomRight: Radius.circular(20.0)),
          color: _webColors.getBackgroundForI3(),
        ),
        padding: EdgeInsets.all(10.0),
        child: Column(
          children: [
            Flexible(
                child: buildLoginWithGoogleMobile(context)
            ),
          ],
        ),
      ),
    );
  }

  Widget buildLogin(context) {
    return Padding(
      padding: EdgeInsets.only(top: 50.0, bottom: 50, left:120.0, right: 120.0),
      child: Card(
        //color: Theme.of(context).primaryColor.withAlpha(30),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(40.0),
        ),
        child: Row(
          children: [
            buildLeftSide(context, 4),
            buildRightSide(context, 3),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        backgroundColor: Colors.white70,
        body: SingleChildScrollView(
          child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            alignment: Alignment.center,
            child: buildLogin(context),
            ),
          ),
        ),
      );
  }

}
