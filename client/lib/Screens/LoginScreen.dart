import 'package:client/Services/GoogleAuth.dart';
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
    // LogicManager.getInstance().login(user);
    // move screen
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
          splashColor: Colors.blue.withAlpha(60),
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
            Container(
              decoration: BoxDecoration(
                borderRadius: BorderRadius.only(topLeft: Radius.circular(40.0),
                    bottomLeft: Radius.circular(40.0)),
                color: Theme.of(context).primaryColor,
              ),
              width: MediaQuery.of(context).size.width / 3.3,
              height: MediaQuery.of(context).size.height / 2,
              padding: EdgeInsets.only(left:50, right: 50),
              child: Center(
                child: Text( "SwimFix",
                    style: TextStyle(fontSize: 80.0, color:Colors.white,
                        fontFamily: "Satisfy")
                ),
              ),
            ),
            Container(
              height: MediaQuery.of(context).size.height / 2,
              padding: EdgeInsets.only(top: 50.0, left: 20.0),
              child: Column(
                children: [
                  Container(
                      width: 300.0,
                      height: 65.0,
                      child: buildLoginWithGoogleMobile(context)
                  ),
                ],
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
