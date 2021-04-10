import 'package:client_application/Domain/Users/AppUser.dart';
import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Screens/Arguments/CameraScreenArguments.dart';
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

  void onFilm(BuildContext context) {
    AppUser appUser = this.widget.arguments.appUser;
    Navigator.pushNamed(context, "/film",
        arguments: new CameraScreenArguments(appUser));
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

  Widget buildTitle(BuildContext context, String title) {
    return Align(
      alignment: Alignment.topLeft,
      child: Text(title,
        style: TextStyle(
          fontSize: 24 * MediaQuery.of(context).textScaleFactor,
          color: Colors.black,
          fontWeight: FontWeight.normal,
          decoration: TextDecoration.none,
        ),
      ),
    );
  }

  Widget buildDes(BuildContext context, String des) {
    return Align(
      alignment: Alignment.topLeft,
      child: Text(des,
        style: TextStyle(
          fontSize: 18 * MediaQuery.of(context).textScaleFactor,
          color: Colors.grey,
          fontWeight: FontWeight.normal,
          decoration: TextDecoration.none,
        ),
      ),
    );
  }

  Widget buildUpload(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(bottom: 10.0),
      child: Card(
        borderOnForeground: true,
        child: Container(
          padding: EdgeInsets.all(15.0),
          child: Column(
            children: [
              buildTitle(context, 'Upload video'),
              buildDes(context, 'Upload a video file in any video format.'),
              ElevatedButton(
                style: ButtonStyle(
                  backgroundColor: MaterialStateProperty.resolveWith<Color>(
                        (Set<MaterialState> states) {
                      return _colorsHolder.getBackgroundForI1(); // Use the component's default.
                    },
                  ),
                ),
                onPressed: ()=>onUpload(context),
                child: Container(
                  width: MediaQuery.of(context).size.width,
                  child: Center(
                    child: Wrap(
                      spacing: 5,
                      children: [
                        Icon(Icons.add_a_photo_sharp),
                        Text('Upload',
                          style: TextStyle(
                            fontSize: 20 * MediaQuery.of(context).textScaleFactor,
                            color: Colors.white,
                            fontWeight: FontWeight.normal,
                            decoration: TextDecoration.none,
                          ),
                        ),
                      ],
                    ),
                  ),
                )
              )
            ],
          ),
        )
      ),
    );
  }

  Widget buildCamera(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(bottom: 10.0),
      child: Card(
          borderOnForeground: true,
          child: Container(
            padding: EdgeInsets.all(15.0),
            child: Column(
              children: [
                buildTitle(context, 'Film video'),
                buildDes(context, 'Film swimming video with your device camera.'),
                ElevatedButton(
                    style: ButtonStyle(
                      backgroundColor: MaterialStateProperty.resolveWith<Color>(
                            (Set<MaterialState> states) {
                          return _colorsHolder.getBackgroundForI1(); // Use the component's default.
                        },
                      ),
                    ),
                    onPressed: ()=>onFilm(context),
                    child: Container(
                      width: MediaQuery.of(context).size.width,
                      child: Center(
                        child: Wrap(
                          spacing: 5,
                          children: [
                            Icon(Icons.videocam),
                            Text('Film',
                              style: TextStyle(
                                fontSize: 20 * MediaQuery.of(context).textScaleFactor,
                                color: Colors.white,
                                fontWeight: FontWeight.normal,
                                decoration: TextDecoration.none,
                              ),
                            ),
                          ],
                        ),
                      ),
                    )
                )
              ],
            ),
          )
      ),
    );
  }

  void onLogout(BuildContext context) {
    Function onYes = () {
      LogicManager.getInstance().logout(this.widget.arguments.appUser.swimmer);
      Navigator.of(context).popUntil((route) => route.isFirst);
    };
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Logout'),
          content: Text('Do you want to logout?'),
          actions: [
            TextButton(
                onPressed: ()=>Navigator.pop(context),
                child: Text("No")
            ),
            TextButton(
                onPressed: () {
                  Navigator.pop(context);
                  onYes();
                },
                child: Text("Yes")
            ),
          ],
        );
      }
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: WillPopScope(
        onWillPop: () {
          onLogout(context);
          return Future.value(false);
        },
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
      ),
    );
  }

}
