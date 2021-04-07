import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Services/Authentication/GoogleAuth.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class LoginScreen extends StatefulWidget {

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
          Navigator.pushNamed(context, "/welcome");
        });
      }
      else {
        //TODO crete a fail popup
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

  Widget buildLogin(context) {
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
