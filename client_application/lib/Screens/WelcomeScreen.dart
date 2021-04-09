import 'package:client_application/Domain/Users/AppUser.dart';
import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Screens/Arguments/UploadScreenArguments.dart';
import 'package:client_application/Screens/Arguments/WelcomeScreenArguments.dart';
import 'package:client_application/Screens/ColorsHolder.dart';
import 'package:client_application/Screens/Drawers/BasicDrawer.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class WelcomeScreen extends StatefulWidget {

  WelcomeScreenArguments arguments;

  WelcomeScreen(this.arguments) : super();

  @override
  _WelcomeScreenState createState() => _WelcomeScreenState();
}

class _WelcomeScreenState extends State<WelcomeScreen> {

  LogicManager _logicManager = LogicManager.getInstance();
  ColorsHolder _colorsHolder = new ColorsHolder();

  void onUpload(BuildContext context) {
    AppUser appUser = this.widget.arguments.appUser;
    Navigator.pushNamed(context, "/upload",
      arguments: new UploadScreenArguments(appUser));
  }

  void onCamera(BuildContext context) {

  }

  Widget buildHi(BuildContext context) {
    return Center(
      child: Column(
        children: [
          Text("Welcome",
            style: TextStyle(
              fontSize: 28,
              color: _colorsHolder.getBackgroundForI1(),
              fontWeight: FontWeight.bold,
            ),
          ),
          Text("${this.widget.arguments.appUser.swimmer.name}",
            style: TextStyle(
              fontSize: 28,
              color: _colorsHolder.getBackgroundForI1(),
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }

  Widget buildUpload(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(bottom: 10.0),
      child: Card(
        borderOnForeground: true,
        child: ListTile(
          leading: Icon(Icons.add_a_photo_sharp),
          title: Text('Upload',
            style: TextStyle(
              fontSize: 22 * MediaQuery.of(context).textScaleFactor,
              color: Colors.black,
              fontWeight: FontWeight.normal,
              decoration: TextDecoration.none,
            ),
          ),
          onTap: ()=>onUpload(context),
        ),
      ),
    );
  }

  Widget buildCamera(BuildContext context) {
    return Card(
      shadowColor: Colors.black,
      child: ListTile(
        leading: Icon(Icons.camera_alt_sharp),
        title: Text('Camera',
          style: TextStyle(
            fontSize: 22 * MediaQuery.of(context).textScaleFactor,
            color: Colors.black,
            fontWeight: FontWeight.normal,
            decoration: TextDecoration.none,
          ),
        ),
        onTap: ()=>onCamera(context),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        drawer: BasicDrawer(
          this.widget.arguments.appUser
        ),
        appBar: AppBar(
          backgroundColor: Colors.blue,
          title: Text("Swim Analytics",),
        ),
        body: SingleChildScrollView(
          child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            color: _colorsHolder.getBackgroundForI6(),
            padding: const EdgeInsets.all(16.0),
            child: Column(
              children: [
                buildHi(context),
                SizedBox(height: 10,),
                Expanded(
                  child: ListView(
                    padding: EdgeInsets.zero,
                    children: <Widget>[
                      buildUpload(context),
                      buildCamera(context),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

}
