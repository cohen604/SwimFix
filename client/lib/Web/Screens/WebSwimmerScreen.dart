import 'package:client/Domain/ScreenArguments/SwimmerScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/WelcomeScreenArguments.dart';
import 'package:client/Domain/Swimmer.dart';
import 'package:client/Web/Components/CardButton.dart';
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

  Widget buildTopSide(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: MenuBar(),
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

  Widget buildMainButton(BuildContext context, int flex, String title) {
    return Flexible(
      flex: flex,
      fit: FlexFit.loose,
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height / 1.5,
        padding: EdgeInsets.only(left: 20.0, right: 20.0),
        child: CardButton(
          title: title,
          background: _webColors.getBackgroundForI4(),
          buttonBackground: _webColors.getBackgroundForI1(),
          image: 'images/swimmer_image.png',
        ),
      ),
    );
  }

  Widget buildMainButtons(BuildContext context, int flex) {
    return Container(
      margin: EdgeInsets.only(top: 20.0),
      child: Row(
        children: [
          buildMainButton(context, 1, "Upload Video"),
          buildMainButton(context, 1, "View feedbacks history"),
          buildMainButton(context, 1, "Open a team"),
          buildMainButton(context, 1, "Team invitations"),
          buildMainButton(context, 1, "Teams"),
        ],
      ),
    );
  }

  Widget buildBottomSide(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.loose,
      child: Container(
          padding: EdgeInsets.only(top:10.0, bottom: 10.0),
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          color: _webColors.getBackgroundForI6(),
          child: Column(
            children: [
              buildWelcomeTitle(context, 1),
              buildMainButtons(context, 6),
            ],
          )),
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
            buildTopSide(context, 1),
            buildBottomSide(context, 10)
          ],
        ),
      ),
    );
  }
}
