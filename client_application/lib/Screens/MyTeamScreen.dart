import 'package:client_application/Components/LeaveMyTeam.dart';
import 'package:client_application/Domain/Swimmer/MyTeam.dart';
import 'package:client_application/Screens/Arguments/MyTeamScreenArguments.dart';
import 'package:client_application/Screens/Arguments/WelcomeScreenArguments.dart';
import 'package:client_application/Screens/Holders/ColorsHolder.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

import 'Drawers/BasicDrawer.dart';

class MyTeamScreen extends StatefulWidget {

  MyTeamScreenArguments args;

  MyTeamScreen(this.args);

  @override
  _MyTeamScreenState createState() => _MyTeamScreenState();
}

class _MyTeamScreenState extends State<MyTeamScreen> {

  ColorsHolder _colorsHolder;
  LogicManager _logicManager;
  ScreenState _state;
  String _teamName;

  _MyTeamScreenState() {
    _colorsHolder = new ColorsHolder();
    _logicManager = LogicManager.getInstance();
    _state = ScreenState.Loading;
  }

  @override
  void initState() {
    super.initState();
    _logicManager.getMyTeam(this.widget.args.appUser.swimmer)
        .then( (MyTeam myTeam) {
      if(myTeam == null) {
        this.setState(() {
          _state = ScreenState.ErrorServer;
        });
      }
      else if(!myTeam.hasTeam) {
        this.setState(() {
          _state = ScreenState.ErrorNoTeam;
        });
      }
      else {
        this.setState(() {
          _state = ScreenState.View;
          _teamName = myTeam.name;
        });
      }
    });
  }

  void onLeaveTeam(BuildContext context) {
    SchedulerBinding.instance.addPostFrameCallback((_) {
      Navigator.pushNamed(context, '/welcome',
          arguments: new WelcomeScreenArguments(this.widget.args.appUser));
    });
  }

  void onClickLeaveTeam(BuildContext context) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
          content: LeaveMyTeam(
              this.widget.args.appUser.swimmer,
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
            buildText(context, 'Loading swimming team...', 21, Colors.black, FontWeight.normal),
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
              size: 35,
              color: Colors.red,
            ),
            SizedBox(height: 5.0,),
            buildText(context, 'Something is broken.\n'
                'Maybe the you don\'t have permissions or the servers are down.\n'
                'For more information contact swimAnalytics@gmail.com',
                21, Colors.black, FontWeight.normal,
                textAlign: TextAlign.center),
          ],
        )
    );
  }

  Widget buildErrorNoSwimmingTeam(BuildContext context) {
    return Center(
      child: buildText(context,
          'You are not part of a swimming team. Go approve one of your team invitations.',
          21,
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
              buildText(context, '$_teamName', 28, _colorsHolder.getBackgroundForI2(), FontWeight.bold),
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
    if(_state == ScreenState.Loading) {
      return buildLoading(context);
    }
    else if(_state == ScreenState.ErrorServer) {
      return buildErrorServer(context);
    }
    else if(_state == ScreenState.ErrorNoTeam) {
      return buildErrorNoSwimmingTeam(context);
    }
    else if(_state == ScreenState.View) {
      return buildView(context);
    }
    return Container();
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        drawer: BasicDrawer(
            this.widget.args.appUser
        ),
        appBar: AppBar(
          backgroundColor: Colors.blue,
          title: Text("Swim Analytics",),
        ),
        body: SingleChildScrollView(
          child: Container(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height,
              color: _colorsHolder.getBackgroundForI6(),
              padding: const EdgeInsets.all(16.0),
              child: buildScreenState(context)
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
  View
}
