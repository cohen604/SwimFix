import 'package:client/Components/Buttons/IconCardButton.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/MyTeamArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/SwimmerOpenTeamArguments.dart';
import 'Arguments/HistoryScreenArguments.dart';
import 'Arguments/PendingInvitationsArguments.dart';
import 'Arguments/SwimmerScreenArguments.dart';
import 'Arguments/UploadScreenArguments.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

import '../Holders/WebColors.dart';

class WebSwimmerScreen extends StatefulWidget {

  SwimmerScreenArguments arguments;
  WebSwimmerScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebSwimmerScreenState createState() => _WebSwimmerScreenState();
}


class _WebSwimmerScreenState extends State<WebSwimmerScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;
  _WebSwimmerScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
  }

  void onClickUpload(BuildContext context) {
    SchedulerBinding.instance.addPostFrameCallback((_) {
      Navigator.pushNamed(context, '/upload',
        arguments: new UploadScreenArguments(this.widget.arguments.user));
    });
  }

  void onClickHistory(BuildContext context) {
    SchedulerBinding.instance.addPostFrameCallback((_) {
        Navigator.pushNamed(context, '/history',
            arguments: new HistoryScreenArguments(this.widget.arguments.user));
      });
  }

  void onClickOpenTeam(BuildContext context) {
    SchedulerBinding.instance.addPostFrameCallback((_) {
      Navigator.pushNamed(context, '/swimmer/open/team',
          arguments: new SwimmerOpenTeamArguments(this.widget.arguments.user));
    });
  }

  void onClickInvitations(BuildContext context) {
    SchedulerBinding.instance.addPostFrameCallback((_) {
      Navigator.pushNamed(context, '/swimmer/invitations',
          arguments: new PendingInvitationsArguments(this.widget.arguments.user));
    });
  }

  void onClickMyTeam(BuildContext context) {
    SchedulerBinding.instance.addPostFrameCallback((_) {
      Navigator.pushNamed(context, '/swimmer/team',
          arguments: new MyTeamArguments(this.widget.arguments.user));
    });
  }

  Widget buildBottomSide(BuildContext context) {
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
      child: ListView(
        scrollDirection: Axis.vertical,
        children: [
          IconCardButton("Upload",
              "Upload a swimming Video",
              ()=>onClickUpload(context),
              Icons.upload_file),
          IconCardButton("History",
              "View feedback history",
              ()=>onClickHistory(context),
              Icons.history),
          IconCardButton("Open a team",
              'Create a new team and become a Coach',
               ()=>onClickOpenTeam(context),
              Icons.group_add),
          IconCardButton("Team invitations",
              'View your swimming team invitations',
              ()=>onClickInvitations(context),
              Icons.insert_invitation),
          IconCardButton("My Team",
              'View your team, you are part of',
              ()=>onClickMyTeam(context),
              Icons.group),
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
              user: this.widget.arguments.user,
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
