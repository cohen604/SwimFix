import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Screens/Components/MobileInput.dart';
import 'package:client/Screens/Components/VideoUploader.dart';
import 'package:client/Services/GoogleAuth.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:google_sign_in/google_sign_in.dart';
import 'Screen.dart';

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
        child: InkWell(
          splashColor: Colors.blue.withAlpha(30),
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

  Widget buildLoginWithGoogle(context) {
    if(kIsWeb) {
      return Center(
        child: buildLoginWithGoogleMobile(context),
      );
    }
    return buildLoginWithGoogleMobile(context);
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: Text("Login"),
        ),
        body: SingleChildScrollView(
          child: Center(
            child: Container(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                children: <Widget>[
                  buildLoginWithGoogle(context),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

}
