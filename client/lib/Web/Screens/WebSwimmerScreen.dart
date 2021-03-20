import 'package:client/Domain/ScreenArguments/SwimmerScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/WelcomeScreenArguments.dart';
import 'package:client/Domain/Swimmer.dart';
import 'package:client/Web/Components/ImageCardButton.dart';
import 'package:client/Web/Components/CircleButton.dart';
import 'package:client/Web/Components/MenuBar.dart';
import 'package:client/Web/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class WebSwimmerScreen extends StatefulWidget {

  SwimmerScreenArguments arguments;
  WebSwimmerScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebSwimmerScreenState createState() => _WebSwimmerScreenState();
}


class _WebSwimmerScreenState extends State<WebSwimmerScreen> {

  WebColors _webColors = new WebColors();

  Widget buildTopSide(BuildContext context) {
    return MenuBar(
        swimmer: this.widget.arguments.swimmer,
    );
  }

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

  Widget buildMainButton(
      BuildContext context,
      String title,
      String description,
      Function onClick) {
    return Container(
      margin: EdgeInsets.all(10.0),
      decoration: BoxDecoration(
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.3),
            spreadRadius: 1,
            blurRadius: 3,
            offset: Offset(0, 3), // changes position of shadow
          ),
        ],
      ),
      child: Card(
        child: Container(
          padding: EdgeInsets.all(10.0),
            child: Column(
              children: [
                ListTile(
                  leading: FlutterLogo(size:70),
                  title: Text( title,
                    style: TextStyle(
                      fontSize: 22 * MediaQuery.of(context).textScaleFactor,
                      color: Colors.black,
                      fontWeight: FontWeight.normal,
                      decoration: TextDecoration.none,
                    ),
                  ),
                  subtitle: Text( description,
                      style: TextStyle(
                        fontSize: 20 * MediaQuery.of(context).textScaleFactor,
                        color: Colors.grey,
                        fontWeight: FontWeight.normal,
                        decoration: TextDecoration.none
                      ),
                    ),
                  ),
                Align(
                  alignment: Alignment.topRight,
                  child: TextButton(
                    onPressed: onClick,
                    child: Text('More',
                      style: TextStyle(
                        fontSize: 20 * MediaQuery.of(context).textScaleFactor,
                        color: Colors.blue,
                        fontWeight: FontWeight.bold,
                        decoration: TextDecoration.none
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
    );
  }

  Widget buildBottomSide(BuildContext context) {
    return Container(
      color: _webColors.getBackgroundForI7(),
      child: ListView(
        scrollDirection: Axis.vertical,
          children: [
            buildMainButton(context, "Upload",
                "Upload a swimming Video", null),
            buildMainButton(context, "History",
                "View feedback history", null),
            buildMainButton(context, "Open a team",
                'Create a new team and become a Coach', null),
            buildMainButton(context, "Team invitations",
                'View your swimming team invitations', null),
            buildMainButton(context, "My Teams",
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
            buildTopSide(context),
            Flexible(
                child: buildBottomSide(context)
            ),
          ],
        ),
      ),
    );
  }
}
