import 'package:client/Domain/Swimmer.dart';
import 'package:client/Services/GoogleAuth.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:client/Screens/Screen.dart';

class LoginScreen extends Screen {

  LoginScreen({Key key}) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {

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
          Navigator.pushNamed(context, "/upload");
        });
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

  Widget buildLoginMobile(context) {
    return Container(
      decoration: BoxDecoration(
        color: Theme.of(context).primaryColor,
        borderRadius: BorderRadius.circular(40.0),
        // gradient: LinearGradient(
        //   begin: Alignment.topLeft,
        //   end: Alignment.bottomRight,
        //   colors: [Theme.of(context).primaryColor, Theme.of(context).backgroundColor.withAlpha(80)]
        // ),
      ),
      child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Text( "SwimFix",
              style: TextStyle(fontSize: 80.0, color:Colors.white,
                fontFamily: "Satisfy")
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: buildLoginWithGoogleMobile(context),
            )
        ],
      ),
    );
  }

  Widget buildLoginWeb(context) {
    return Padding(
      padding: const EdgeInsets.only(top: 60.0, left:120.0, right: 120.0),
      child: Card(
        color: Theme.of(context).primaryColor.withAlpha(30),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(40.0),
        ),
        child: Row(
          children: [
            Flexible(
              flex: 2,
              child: Container(
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.only(topLeft: Radius.circular(40.0),
                      bottomLeft: Radius.circular(40.0)),
                  color: Theme.of(context).primaryColor,
                ),
                //width: MediaQuery.of(context).size.width / 3,
                //height: MediaQuery.of(context).size.height / 2,
                padding: EdgeInsets.only(left:50, right: 50),
                child: Center(
                  child: Text( "SwimFix",
                      style: TextStyle(fontSize: 80 * MediaQuery.of(context).textScaleFactor, color:Colors.white,
                          fontFamily: "Satisfy")
                  ),
                ),
              ),
            ),
            Flexible(
              flex: 3,
              child: Container(
                //height: MediaQuery.of(context).size.height / 2,
                padding: EdgeInsets.all(30.0),
                child: Column(
                  children: [
                    Flexible(
                      flex: 1,
                      child: buildLoginWithGoogleMobile(context)
                    ),
                    Flexible(
                      flex: 2,
                      child: Text(""),
                    )
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildLogin(context) {
    if (kIsWeb) {
      return buildLoginWeb(context);
    }
    return buildLoginMobile(context);
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height - 15,
            alignment: Alignment.center,
            padding: const EdgeInsets.all(16.0),
            child: buildLogin(context),
            ),
          ),
        ),
      );
  }

}
