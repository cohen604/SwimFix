import 'package:client/Domain/ScreenArguments/WelcomeScreenArguments.dart';
import 'package:client/Domain/Swimer.dart';
import 'package:client/Web/Components/CardButton.dart';
import 'package:client/Web/Components/CircleButton.dart';
import 'package:client/Web/Components/MenuBar.dart';
import 'package:client/Web/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class WebWelcomeScreen extends StatefulWidget {

  WelcomeScreenArguments arguments;
  WebWelcomeScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebWelcomeScreenState createState() => _WebWelcomeScreenState();
}


class _WebWelcomeScreenState extends State<WebWelcomeScreen> {

  WebColors _webColors = new WebColors();

  Widget buildLogo(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        decoration: BoxDecoration(
          border: Border(
            right: BorderSide(
              color: Colors.white,
              width: 2.0,
            ),
          ),
        ),
        child: Center(
          child: Text( "Swim Analytics",
              style: TextStyle(
              fontSize: 26 * MediaQuery.of(context).textScaleFactor,
              color:Colors.white,
              fontWeight: FontWeight.bold,
              fontStyle: FontStyle.italic,
              decoration: TextDecoration.none
            )
          ),
        ),
      ),
    );
  }

  Widget buildMenuBar(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: MenuBar()
      );
  }

  Widget buildTopSide(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          decoration: BoxDecoration(
            border: Border(
              bottom: BorderSide(
                color: Colors.black,
                width: 2.0,
              ),
            ),
            color: _webColors.getBackgroundForI1(),
          ),
          child: Row(
            children: [
              buildLogo(context, 1),
              buildMenuBar(context, 4)
            ],
        )
      ),
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
          background: _webColors.getBackgroundForI2(),
          buttonBackground: _webColors.getBackgroundForI4(),
        ),
      ),
    );
  }

  Widget buildMainButtons(BuildContext context, int flex) {
    return Container(
      margin: EdgeInsets.only(top: 20.0),
      child: Row(
          children: [
            buildMainButton(context, 1, "Swimmer"),
            buildMainButton(context, 1, "Coach"),
            buildMainButton(context, 1, "Admin"),
            buildMainButton(context, 1, "Researcher"),
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
          color: _webColors.getBackgroundForI3(),
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
          color: Colors.red,
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
