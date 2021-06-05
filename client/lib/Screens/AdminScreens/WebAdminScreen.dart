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

import 'Arguments/AdminSrceenArguments.dart';

class WebAdminScreen extends StatefulWidget {

  AdminScreenArguments args;

  WebAdminScreen(this.args);

  @override
  _WebAdminScreenState createState() => _WebAdminScreenState();
}

class _WebAdminScreenState extends State<WebAdminScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;

  _WebAdminScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
  }


  void onClickAddAdmins() {
    this.setState(() {
      SchedulerBinding.instance.addPostFrameCallback((_) {
        Navigator.pushNamed(context, '/admin/add/admins',
            arguments: new AddAdminsScreenArguments(this.widget.args.user));
      });
    });
  }

  void onClickAddResearchers() {
    this.setState(() {
      SchedulerBinding.instance.addPostFrameCallback((_) {
        Navigator.pushNamed(context, '/admin/add/researchers',
            arguments: new AddResearcherScreenArguments(this.widget.args.user));
      });
    });
  }

  void onClickStatistics() {
    this.setState(() {
      SchedulerBinding.instance.addPostFrameCallback((_) {
        Navigator.pushNamed(context, '/admin/statistics',
            arguments: new StatisticsScreenArguments(this.widget.args.user));
      });
    });
  }

  Widget buildMainArea(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getBackGroundImage()),
          fit: BoxFit.fill,
        ),
      ),
      child: ListView(
        scrollDirection: Axis.vertical,
        children: [
          IconCardButton("Add admins",
              "Add to user an admin permissions", onClickAddAdmins, Icons.add_circle_outline),
          IconCardButton("Add researchers",
              "Add to user a researcher permissions", onClickAddResearchers, Icons.add_circle_outline),
          IconCardButton("View statistics",
              'View statistical information about the platform', onClickStatistics, Icons.analytics_outlined),
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
