import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Team/AddingTeamResponse.dart';
import 'package:client/Domain/Users/UserPermissions.dart';
import 'package:client/Screens/CoachScreens/Arguments/CoachScreenArguments.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/SwimmerOpenTeamArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class WebSwimmerOpenTeamScreen extends StatefulWidget {

  final SwimmerOpenTeamArguments args;

  WebSwimmerOpenTeamScreen(this.args);

  @override
  _WebSwimmerOpenTeamScreenState createState() => _WebSwimmerOpenTeamScreenState();
}

class _WebSwimmerOpenTeamScreenState extends State<WebSwimmerOpenTeamScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;
  LogicManager _logicManager;
  ScreenState _screenState;
  TextEditingController _controller;

  _WebSwimmerOpenTeamScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _logicManager = LogicManager.getInstance();
    _screenState = ScreenState.Insert;
    _controller = new TextEditingController();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  void showDialogWrongTeamName(BuildContext context, String teamName) {
    showDialog(
        context: context,
        builder: (_) => AlertDialog(
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Icon(
                Icons.error_outline,
                size: 45,
                color: Colors.yellow,
              ),
              buildText(context,
                  'Invalid team name $teamName.\nPlease insert other team name.',
                  24, Colors.black, FontWeight.normal),
            ],
          ),
        )
    );
  }

  void moveToCoachScreen(BuildContext context, String teamName) {
    _logicManager.getPermissions(this.widget.args.user.swimmer).then(
        (UserPermissions permissions) {
          if(permissions ==null) {
            this.setState(() {
              _screenState = ScreenState.ServerError;
            });
          }
          else {
            this.widget.args.user.permissions = permissions;
            CoachScreenArguments arguments = new CoachScreenArguments(this.widget.args.user);
            SchedulerBinding.instance.addPostFrameCallback((_) {
              Navigator.pushNamed(context, "/coach", arguments: arguments);
            });
          }
        }
    );
  }

  void onClickAddTeam(BuildContext context, String teamName) {
    if(teamName.isEmpty || teamName.replaceAll(" ", "").isEmpty) {
      showDialogWrongTeamName(context, teamName);
    }
    else {
      teamName = teamName.replaceAll("\n", "");
      _logicManager.openSwimmingTeam(this.widget.args.user.swimmer, teamName)
          .then((AddingTeamResponse result) {
          if(result == null) {
            this.setState(() {
              _screenState = ScreenState.ServerError;
            });
          }
          else if(result.isAlreadyCoach) {
            this.setState(() {
              _screenState = ScreenState.AlreadyCoachError;
            });
          }
          else if(result.isAlreadyExists) {
            this.setState(() {
              _screenState = ScreenState.TeamExistsError;
            });
          }
          else if(result.isAdded) {
            moveToCoachScreen(context, teamName);
          }
      });
    }

  }

  void onClickCancelAddTeam() {

  }

  Widget buildText(BuildContext context, String text, int size, Color color, FontWeight fontWeight) {
    return Text(text,
      textAlign: TextAlign.center,
      style: TextStyle(
          fontSize: size * MediaQuery.of(context).textScaleFactor,
          color: color,
          fontWeight: fontWeight,
          decoration: TextDecoration.none
      ),
    );
  }

  Widget buildInsertTeamName(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width / 2,
      child: TextField(
        controller: _controller,
        decoration: InputDecoration(
          border: OutlineInputBorder(),
          labelText: 'Search text',
        ),
        textAlign: TextAlign.center,
        style: TextStyle(
            fontSize: 24.0,
            height: 2.0,
            color: Colors.black,
        ),
        onSubmitted: (String value) {
          onClickAddTeam(context, value);
        },
      ),
    );
  }

  Widget buildScreenStateInsert(BuildContext context) {
    return Material(
      color: Colors.transparent,
      child: Container(
        padding: const EdgeInsets.all(10),
        alignment: Alignment.center,
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            buildText(context, 'New swimming team', 36, Colors.black, FontWeight.normal),
            SizedBox(height: 10,),
            buildInsertTeamName(context),
            SizedBox(height: 10,),
            ElevatedButton(
              onPressed: ()=>onClickAddTeam(context, _controller.text),
              child: Padding(
                padding: const EdgeInsets.all(10.0),
                child: buildText(context, 'Open', 24, Colors.white, FontWeight.normal),
              ),
            )
          ],
        ),
      ),
    );
  }

  Widget buildScreenStateLoading(BuildContext context) {
    return Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            CircularProgressIndicator(),
            SizedBox(height: 5.0,),
            buildText(context, 'Adding swimming team...', 24, Colors.black, FontWeight.normal),
          ],
        )
    );
  }

  Widget buildScreenStateAlreadyCoachError(BuildContext context) {
    return Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(
              Icons.error_outline,
              size: 45,
              color: Colors.yellow,
            ),
            SizedBox(height: 5.0,),
            buildText(context, 'You are already a coach. Go visit your team page.\n',
                24, Colors.black, FontWeight.normal),
          ],
        )
    );
  }

  Widget buildScreenStateTeamExistsError(BuildContext context) {
    return Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(
              Icons.error_outline,
              size: 45,
              color: Colors.yellow,
            ),
            SizedBox(height: 5.0,),
            buildText(context, 'Team already exists. Try insert a different name\n',
                24, Colors.black, FontWeight.normal),
          ],
        )
    );
  }

  Widget buildScreenStateServerError(BuildContext context) {
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
                24, Colors.black, FontWeight.normal),
          ],
        )
    );
  }

  Widget buildScreenState(BuildContext context) {
    if(_screenState == ScreenState.Insert) {
      return buildScreenStateInsert(context);
    }
    else if(_screenState == ScreenState.Loading) {
      return buildScreenStateLoading(context);
    }
    else if(_screenState == ScreenState.AlreadyCoachError) {
      return buildScreenStateAlreadyCoachError(context);
    }
    else if(_screenState == ScreenState.TeamExistsError) {
      return buildScreenStateTeamExistsError(context);
    }
    else if(_screenState == ScreenState.ServerError) {
      return buildScreenStateServerError(context);
    }
    return Container();
  }

  Widget buildMainFrame(BuildContext context) {
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
      child: buildScreenState(context),
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
              user: this.widget.args.user,
            ),
            Flexible(
                child: buildMainFrame(context)
            ),
          ],
        ),
      ),
    );
  }

}

enum ScreenState {
  Insert,
  Loading,
  ServerError,
  AlreadyCoachError,
  TeamExistsError,
}
