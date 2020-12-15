import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Screens/Components/MobileInput.dart';
import 'package:client/Screens/Components/VideoUploader.dart';
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
  final GoogleSignIn googleSignIn = GoogleSignIn.standard(
    scopes: ['email'],
  );
  void signInWithGoogle() async {
    final GoogleSignInAccount account = await this.googleSignIn.signIn();
    print(account.email);
  }

  Widget buildLoginWithGoogleMobile(context) {
    return Center(
      child: Card(
        child: InkWell(
          splashColor: Colors.blue.withAlpha(30),
          onTap: ()=>{},
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

  Widget buildGoogleLogin(context) {
    return ListTile(
      title: Text("Sign In with Google"),
      leading: Image(image: AssetImage("assets/google_logo.png")),
      focusColor: Colors.grey,
      tileColor: Colors.grey,
      onTap: ()=>signInWithGoogle(),
      );
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
