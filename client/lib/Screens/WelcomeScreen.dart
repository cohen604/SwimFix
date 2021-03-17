import 'package:client/Domain/Swimmer.dart';
import 'package:client/Services/GoogleAuth.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:client/Screens/Screen.dart';

class WelcomeScreen extends Screen {

  Swimmer swimmer;
  WelcomeScreen({this.swimmer, Key key}) : super(key: key);

  @override
  _WelcomeScreenState createState() => _WelcomeScreenState();
}

class _WelcomeScreenState extends State<WelcomeScreen> {


  Widget buildUpload(BuildContext context) {
    return Card(
      child: Text("Upload")
    );
  }

  Widget buildButton(BuildContext context, String text) {
    return Container(
      width: 200,
      height: 100,
      margin: EdgeInsets.all(0),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.black),
        borderRadius: BorderRadius.circular(10),
        color: Theme.of(context).backgroundColor.withAlpha(50),
      ),
      child: InkWell(
        hoverColor: Theme.of(context).shadowColor.withAlpha(50),
        splashColor: Colors.blue,
        onTap: ()=>{},
        borderRadius: BorderRadius.circular(10),
        child: Center(
          child: Text(text , style:TextStyle(fontSize: 21, )),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: Text("Welcome Screen"),
        ),
        body: SingleChildScrollView(
          child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height - 15,
            alignment: Alignment.center,
            padding: const EdgeInsets.all(16.0),
            child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Text("Hi, ${this.widget.swimmer.name}", style: TextStyle(
                    fontSize: 28,
                    color: Theme.of(context).primaryColor,
                    fontWeight: FontWeight.bold,
                  ),),
                  Spacer(flex: 1,),
                  buildButton(context, "upload"),
                  Spacer(flex: 1,),
                  buildButton(context, "Feedback"),
                  Spacer(flex: 5,),
                ],
              ),
            ),
          ),
        ),
      );
  }

}
