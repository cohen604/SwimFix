import 'package:client/Components/Buttons/IconCardButton.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Screens/AdminScreens/Arguments/AddAdminsScreenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/AddResearcherScreenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/StatisticsScreenArguments.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

import 'Arguments/AdminSceenArguments.dart';

class WebAddAdminsScreen extends StatefulWidget {

  AddAdminsScreenArguments args;

  WebAddAdminsScreen(this.args);

  @override
  _WebAddAdminsScreenState createState() => _WebAddAdminsScreenState();
}

class _WebAddAdminsScreenState extends State<WebAddAdminsScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;

  _WebAddAdminsScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
  }

  Widget buildMainArea(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getSwimmerBackGround()),
          fit: BoxFit.fill,
          colorFilter: ColorFilter.mode(
              _webColors.getBackgroundForI6(),
              BlendMode.hardLight),
        ),
      ),
      child: Container(),
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
