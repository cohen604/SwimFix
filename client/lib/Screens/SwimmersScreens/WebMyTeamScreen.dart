import 'package:client/Components/LeaveMyTeam.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Summaries/MyTeam.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/MyTeamArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/SwimmerScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class WebNyTeamScreen extends StatefulWidget {

  MyTeamArguments args;

  WebNyTeamScreen(this.args);

  @override
  _WebNyTeamScreenState createState() => _WebNyTeamScreenState();
}

class _WebNyTeamScreenState extends State<WebNyTeamScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;
  LogicManager _logicManager;
  ScreenState _screenState;
  String _teamName;

  _WebNyTeamScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _logicManager = LogicManager.getInstance();
    _screenState = ScreenState.Loading;
  }


  @override
  void initState() {
    super.initState();
    _logicManager.getMyTeam(this.widget.args.user.swimmer)
        .then( (MyTeam myTeam) {
          if(myTeam == null) {
            this.setState(() {
              _screenState = ScreenState.ErrorServer;
            });
          }
          else if(!myTeam.hasTeam) {
            this.setState(() {
              _screenState = ScreenState.ErrorNoTeam;
            });
          }
          else {
            this.setState(() {
              _screenState = ScreenState.View;
              _teamName = myTeam.name;
            });
          }
    });
  }

  void onLeaveTeam(BuildContext context) {
    SchedulerBinding.instance.addPostFrameCallback((_) {
      Navigator.pushNamed(context, '/swimmer',
          arguments: new SwimmerScreenArguments(this.widget.args.user));
    });
  }

  void onClickLeaveTeam(BuildContext context) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
          content: LeaveMyTeam(
            this.widget.args.user.swimmer,
            _teamName,
              ()=>onLeaveTeam(context)
          )
      ),
    );
  }

  Widget buildText(
      BuildContext context,
      String text,
      int size,
      Color color,
      FontWeight fontWeight,
      {textAlign = TextAlign.center}) {
    return Text(text,
      textAlign: textAlign,
      style: TextStyle(
          fontSize: size * MediaQuery.of(context).textScaleFactor,
          color: color,
          fontWeight: fontWeight,
          decoration: TextDecoration.none
      ),
    );
  }

  Widget buildLoading(BuildContext context) {
    return Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            CircularProgressIndicator(),
            SizedBox(height: 5.0,),
            buildText(context, 'Loading my team...', 24, Colors.black, FontWeight.normal),
          ],
        )
    );
  }

  Widget buildErrorServer(BuildContext context) {
    return Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(
              Icons.warning_amber_rounded,
              size: 45,
              color: Colors.red,
            ),
            SizedBox(height: 5.0,),
            buildText(context, 'Something is broken.\n'
                'Maybe the you don\'t have permissions or the servers are down.\n'
                'For more information contact swimAnalytics@gmail.com',
                24, Colors.black, FontWeight.normal,
                textAlign: TextAlign.center),
          ],
        )
    );
  }

  Widget buildErrorNoTeam(BuildContext context) {
    return Center(
        child: buildText(context,
            'You are not part of a swimming team. Go approve one of your team invitations.',
            24,
            Colors.black,
            FontWeight.normal,
            textAlign: TextAlign.center
        ),
    );
  }

  Widget buildView(BuildContext context) {
    return Center(
      child: Card(
        child: Padding(
          padding: const EdgeInsets.all(25),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              buildText(context, '$_teamName', 28, _webColors.getBackgroundForI2(), FontWeight.bold),
              SizedBox(height: 15,),
              ElevatedButton(
                onPressed: ()=>onClickLeaveTeam(context),
                child: Padding(
                  padding: const EdgeInsets.all(7.0),
                  child: buildText(
                      context,
                      'Leave',
                      18,
                      Colors.white,
                      FontWeight.bold),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget buildScreenState(BuildContext context) {
    if(_screenState == ScreenState.Loading) {
      return buildLoading(context);
    }
    else if(_screenState == ScreenState.ErrorServer) {
      return buildErrorServer(context);
    }
    else if(_screenState == ScreenState.ErrorNoTeam) {
      return buildErrorNoTeam(context);
    }
    else if(_screenState == ScreenState.View) {
      return buildView(context);
    }
    return Container();
  }

  Widget buildMainArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getSwimmerBackGround()),
          fit: BoxFit.fill,
          colorFilter: ColorFilter.mode(
              _webColors.getBackgroundForI6(),
              BlendMode.hardLight),
        ),
      ),
      child: buildScreenState(context),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          child: Column(
            children: [
              MenuBar(
                user: this.widget.args.user,
              ),
              Expanded(
                child: SingleChildScrollView(
                    child: Scrollbar(
                        child: buildMainArea(context)
                    )
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

}

enum ScreenState {
  Loading,
  ErrorServer,
  ErrorNoTeam,
  View,
}