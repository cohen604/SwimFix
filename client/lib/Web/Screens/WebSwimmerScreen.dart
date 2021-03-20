import 'package:client/Domain/ScreenArguments/SwimmerScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/UploadScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/WelcomeScreenArguments.dart';
import 'package:client/Domain/Swimmer.dart';
import 'package:client/Web/Components/IconCardButton.dart';
import 'package:client/Web/Components/ImageCardButton.dart';
import 'package:client/Web/Components/CircleButton.dart';
import 'package:client/Web/Components/MenuBar.dart';
import 'package:client/Web/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class WebSwimmerScreen extends StatefulWidget {

  SwimmerScreenArguments arguments;
  WebSwimmerScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebSwimmerScreenState createState() => _WebSwimmerScreenState();
}


class _WebSwimmerScreenState extends State<WebSwimmerScreen> {

  WebColors _webColors = new WebColors();

  Widget buildWelcomeTitle(BuildContext context, int flex) {
      return Text('Welcome ${this.widget.arguments.swimmer.name}',
        style: TextStyle(
            fontSize: 32 * MediaQuery.of(context).textScaleFactor,
            color: Colors.black,
            fontWeight: FontWeight.bold,
            decoration: TextDecoration.none
        ),
      );
  }

  void onClickUpload() {
    this.setState(() {
      SchedulerBinding.instance.addPostFrameCallback((_) {
        Navigator.pushNamed(context, '/upload',
          arguments: new UploadScreenArguments(this.widget.arguments.swimmer));
      });
    });
  }

  Widget buildBottomSide(BuildContext context) {
    return Container(
      color: _webColors.getBackgroundForI7(),
      child: ListView(
        scrollDirection: Axis.vertical,
        children: [
          IconCardButton("Upload",
              "Upload a swimming Video", onClickUpload),
          IconCardButton("History",
              "View feedback history", null),
          IconCardButton("Open a team",
              'Create a new team and become a Coach', null),
          IconCardButton("Team invitations",
              'View your swimming team invitations', null),
          IconCardButton("My Teams",
              'View your teams, you are part of', null), // buildMainButtons(context, 6),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Column(
          children: [
            MenuBar(
              swimmer: this.widget.arguments.swimmer,
            ),
            Flexible(
                child: buildBottomSide(context)
            ),
          ],
        ),
      ),
    );
  }
}
