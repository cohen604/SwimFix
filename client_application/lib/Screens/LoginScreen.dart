import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Domain/Users/AppUser.dart';
import 'package:client_application/Screens/Arguments/WelcomeScreenArguments.dart';
import 'package:client_application/Screens/ColorsHolder.dart';
import 'package:client_application/Services/Authentication/GoogleAuth.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import 'PopUps/MessagePopUp.dart';

class LoginScreen extends StatefulWidget {

  LoginScreen({Key key}) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {

  ColorsHolder _colorsHolder = new ColorsHolder();


  void signInWithGoogle() async {
    GoogleAuth googleAuth = new GoogleAuth();
    User user = await googleAuth.signIn();
    print(user);
    String name = user.displayName;
    String email = user.email;
    String uid = user.uid;
    Swimmer swimmer = new Swimmer(uid, email, name);
    LogicManager.getInstance().login(swimmer).then((logged) {
      if (logged) {
        this.setState(() {
          AppUser appUser = new AppUser(swimmer, user.photoURL);
          Navigator.pushNamed(context, "/welcome",
              arguments: new WelcomeScreenArguments(appUser));
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

  Widget buildLogin(context) {
    return Container(
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage('assets/images/about_screen_background.png'),
          fit: BoxFit.cover,
          colorFilter: ColorFilter.mode(Colors.black.withAlpha(120), BlendMode.darken),
        ),
        border: Border.all(color: _colorsHolder.getBackgroundForI7(), width: 4),
        borderRadius: BorderRadius.circular(35.0),
      ),
      child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Text( "Swim Analytics",
              style: TextStyle(
                fontSize: 50.0,
                color:Colors.white,
                fontFamily: "Satisfy",
                fontWeight: FontWeight.bold,
              ),
              textAlign: TextAlign.center,
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: buildLoginWithGoogleMobile(context),
            )
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height - 15,
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage('assets/images/about_screen_background.png'),
                fit: BoxFit.cover,
              ),
            ),
            alignment: Alignment.center,
            padding: const EdgeInsets.all(16.0),
            child: buildLogin(context),
            ),
          ),
        ),
      );
  }

}
