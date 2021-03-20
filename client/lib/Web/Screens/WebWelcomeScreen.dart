import 'package:client/Domain/ScreenArguments/ResearcherScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/SwimmerScreenArguments.dart';
import 'package:client/Domain/ScreenArguments/WelcomeScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Web/Components/ImageCardButton.dart';
import 'package:client/Web/Components/MenuBar.dart';
import 'package:client/Web/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class WebWelcomeScreen extends StatefulWidget {

  WelcomeScreenArguments arguments;
  WebWelcomeScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebWelcomeScreenState createState() => _WebWelcomeScreenState();
}


class _WebWelcomeScreenState extends State<WebWelcomeScreen> {

  LogicManager _logicManger = LogicManager.getInstance();
  WebColors _webColors = new WebColors();

  Widget buildTopSide(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: MenuBar(swimmer: this.widget.arguments.swimmer,),
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

  Widget buildMainButton(BuildContext context,
      int flex,
      String title,
      Function onClick,
      String image) {
    return Flexible(
      flex: flex,
      fit: FlexFit.loose,
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height / 1.3,
        padding: EdgeInsets.only(left: 20.0, right: 20.0),
        child: ImageCardButton(
          title: title,
          background: _webColors.getBackgroundForI4(),
          buttonBackground: _webColors.getBackgroundForI1(),
          onClick: onClick,
          image: image,
        ),
      ),
    );
  }

  Function onClick(String path, {Object arguments}) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, path, arguments: arguments);
        });
      });
    };
  }

  Widget buildMainButtons(BuildContext context, int flex) {
    return Container(
      margin: EdgeInsets.only(top: 20.0),
      child: Row(
          children: [
            buildMainButton(context, 1, "Swimmer",
                onClick('/swimmer',
                    arguments: new SwimmerScreenArguments(this.widget.arguments.swimmer)),
                'images/swimmer_image.png'),
            buildMainButton(context, 1, "Coach",
                null, 'images/coach_image.png'),
            buildMainButton(context, 1, "Admin",
                null,  'images/admin_image.png'),
            buildMainButton(context, 1, "Researcher",
                onClick('/researcher',
                    arguments: new ResearcherScreenArguments(this.widget.arguments.swimmer)),
                'images/researcher_image.png'),
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
