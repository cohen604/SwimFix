import 'package:client/Screens/Arguments/CoachScreenArguments.dart';
import 'package:client/Screens/Arguments/ResearcherScreenArguments.dart';

import 'Arguments/ReportScreenArguments.dart';
import 'Arguments/SwimmerScreenArguments.dart';
import 'Arguments/WelcomeScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Components/ImageCardButton.dart';
import 'package:client/Components/MenuBar.dart';
import 'package:client/Screens/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class WebWelcomeScreen extends StatefulWidget {

  WelcomeScreenArguments args;
  WebWelcomeScreen({Key key, this.args}) : super(key: key);

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
      child: MenuBar(user: this.widget.args.user,),
    );
  }

  Widget buildWelcomeTitle(BuildContext context, int flex) {
      return Text('Welcome ${this.widget.args.user.swimmer.name}',
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

  Widget buildSwimmer(BuildContext context) {
    if(this.widget.args.user.permissions.isSwimmer) {
      return buildMainButton(
          context, 1, "Swimmer",
          onClick('/swimmer',
              arguments: new SwimmerScreenArguments(this.widget.args.user)),
          'images/swimmer_image.png');
    }
    return Container();
  }

  Widget buildCoach(BuildContext context) {
    //TODO add call to logic manger get the coach team info
    if(this.widget.args.user.permissions.isCoach) {
      return buildMainButton(context, 1, "Coach",
      onClick('/coach',
          arguments: new CoachScreenArguments(this.widget.args.user, 'Team Name')),
          'images/coach_image.png');
    }
    return Container();
  }

  Widget buildAdmin(BuildContext context) {
    if(this.widget.args.user.permissions.isAdmin) {
      return buildMainButton(context, 1, "Admin",
          null, 'images/admin_image.png');
    }
    return Container();
  }

  Widget buildResearcher(BuildContext context) {
    if(this.widget.args.user.permissions.isResearcher) {
      return buildMainButton(context, 1, "Researcher",
          onClick('/researcher',
              arguments: new ResearcherScreenArguments(this.widget.args.user)),
          'images/researcher_image.png');
    }
    return Container();
  }

  Widget buildMainButtons(BuildContext context, int flex) {
    return Container(
      margin: EdgeInsets.only(top: 20.0),
      child: Row(
          children: [
            buildSwimmer(context),
            buildCoach(context),
            buildAdmin(context),
            buildResearcher(context),
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
