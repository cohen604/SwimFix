import 'package:client/Components/Buttons/ImageCardButton.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'AdminScreens/Arguments/AdminSrceenArguments.dart';
import 'CoachScreens/Arguments/CoachScreenArguments.dart';
import 'ResearcherScreens/Arguments/ResearcherScreenArguments.dart';
import 'SwimmersScreens/Arguments/SwimmerScreenArguments.dart';
import 'Arguments/WelcomeScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

import 'Holders/WebColors.dart';

class WebWelcomeScreen extends StatefulWidget {

  WelcomeScreenArguments args;
  WebWelcomeScreen({Key key, this.args}) : super(key: key);

  @override
  _WebWelcomeScreenState createState() => _WebWelcomeScreenState();
}


class _WebWelcomeScreenState extends State<WebWelcomeScreen> {

  LogicManager _logicManger;
  WebColors _webColors;
  AssetsHolder _assetsHolder;

  _WebWelcomeScreenState() {
    _logicManger = LogicManager.getInstance();
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
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

  Widget buildWelcomeTitle(BuildContext context) {
      return Padding(
        padding: const EdgeInsets.all(10.0),
        child: Text('Welcome ${this.widget.args.user.swimmer.name}',
          style: TextStyle(
              fontSize: 32 * MediaQuery.of(context).textScaleFactor,
              color: Colors.black,
              fontWeight: FontWeight.bold,
              decoration: TextDecoration.none
          ),
        ),
      );
  }

  Widget buildMainButton(BuildContext context,
      String title,
      Function onClick,
      String image) {
    return Flexible(
      child: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height / 1.4,
          padding: EdgeInsets.only(left: 20.0, right: 20.0),
          child: ImageCardButton(
            title: title,
            background: _webColors.getBackgroundForI3(),
            buttonBackground: _webColors.getBackgroundForI1(),
            onClick: onClick,
            image: image,
          ),
      ),
    );
  }

  Widget buildSwimmer(BuildContext context) {
    if(this.widget.args.user.permissions.isSwimmer) {
      return buildMainButton(context, "Swimmer",
          onClick('/swimmer',
              arguments: new SwimmerScreenArguments(this.widget.args.user)),
          _assetsHolder.getSwimmerImage()
      );
    }
    return Container();
  }

  Widget buildCoach(BuildContext context) {
    if(this.widget.args.user.permissions.isCoach) {
      return buildMainButton(context, "Coach",
      onClick('/coach',
          arguments: new CoachScreenArguments(this.widget.args.user)),
          _assetsHolder.getCoachImage()
      );
    }
    return Container();
  }

  Widget buildAdmin(BuildContext context) {
    if(this.widget.args.user.permissions.isAdmin) {
      return buildMainButton(context, "Admin",
          onClick('/admin',
              arguments: new AdminScreenArguments(this.widget.args.user)),
          _assetsHolder.getAdminImage());
    }
    return Container();
  }

  Widget buildResearcher(BuildContext context) {
    if(this.widget.args.user.permissions.isResearcher) {
      return buildMainButton(context, "Researcher",
          onClick('/researcher',
              arguments: new ResearcherScreenArguments(this.widget.args.user)),
          _assetsHolder.getResearcherImage());
    }
    return Container();
  }

  Widget buildMainButtons(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 20.0),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: [
          buildSwimmer(context),
          buildCoach(context),
          buildAdmin(context),
          buildResearcher(context),
        ],
      ),
    );
  }

  Widget buildMainArea(BuildContext context) {
    return SingleChildScrollView(
      child: Container(
          padding: EdgeInsets.only(top:10.0, bottom: 10.0),
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          decoration: BoxDecoration(
            image: DecorationImage(
              image: AssetImage(_assetsHolder.getBackGroundImage()),
              fit: BoxFit.fill,
            ),
          ),
          child: Column(
            children: [
              buildWelcomeTitle(context),
              Expanded(
                  child: buildMainButtons(context)
              ),
            ],
          )
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
            MenuBar(user: this.widget.args.user,),
            Expanded(
                child: buildMainArea(context)
            ),
          ],
        ),
      ),
    );
  }
}
