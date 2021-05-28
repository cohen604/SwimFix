import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class LeaveMyTeam extends StatefulWidget {

  final Swimmer swimmer;
  final String teamName;
  final Function onSuccess;

  LeaveMyTeam(this.swimmer, this.teamName, this.onSuccess);

  @override
  _LeaveMyTeamState createState() => _LeaveMyTeamState();
}

class _LeaveMyTeamState extends State<LeaveMyTeam> {

  WebColors _webColors;
  LogicManager _logicManager;
  ComponentState _state;

  _LeaveMyTeamState() {
    _webColors = WebColors.getInstance();
    _logicManager = LogicManager.getInstance();
    _state = ComponentState.Waiting;
  }

  void onClickYes(BuildContext context) {
    this.setState(() {
      _state = ComponentState.Loading;
    });
    Swimmer swimmer = this.widget.swimmer;
    String teamName = this.widget.teamName;
    _logicManager.leaveTeam(swimmer, teamName).then(
        (bool result) {
          if(result == null) {
            this.setState(() {
              _state = ComponentState.ServerError;
            });
          }
          else if(result) {
            this.setState(() {
              _state = ComponentState.Success;
            });
          }
          else { // !result
            this.setState(() {
              _state = ComponentState.Rejected;
            });
          }
        }
    );
  }

  void onClickNo(BuildContext context) {
    Navigator.pop(context);
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

  Widget buildWaiting(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        buildText(context, 'Do you want to leave ${this.widget.teamName}?',
            24, Colors.black, FontWeight.normal),
        SizedBox(height: 10,),
        Align(
          alignment: Alignment.topRight,
          child: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextButton(
                  onPressed: ()=>onClickNo(context),
                  child: buildText(context, 'No', 21, _webColors.getBackgroundForI2(), FontWeight.normal)
              ),
              TextButton(
                  onPressed: ()=>onClickYes(context),
                  child: buildText(context, 'Yes', 21, _webColors.getBackgroundForI2(), FontWeight.normal)
              ),
            ],
          ),
        )
      ],
    );
  }

  Widget buildLoading(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        CircularProgressIndicator(),
        SizedBox(height: 5.0,),
        buildText(context, 'Leaving team...', 21, Colors.black, FontWeight.normal),
      ],
    );
  }

  Widget buildServerError(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Icon(
          Icons.error_outline,
          size: 35,
          color: Colors.red,
        ),
        SizedBox(height: 5.0,),
        buildText(context, 'Something is broken.\n'
            'Maybe the you don\'t have permissions or the servers are down.\n'
            'For more information contact swimAnalytics@gmail.com',
            18, Colors.black, FontWeight.normal,
            textAlign: TextAlign.center),
      ],
    );
  }

  Widget buildRejected(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        buildText(context, 'Your request to leave ${this.widget.teamName} was denied.',
            18, Colors.black, FontWeight.normal,
            textAlign: TextAlign.center),
        buildText(context, 'Please talk to your coach.',
            18, Colors.black, FontWeight.normal,
            textAlign: TextAlign.center),
      ],
    );
  }

  Widget buildSuccess(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        buildText(context, 'You left ${this.widget.teamName}', 21, Colors.black, FontWeight.normal),
        SizedBox(height: 5.0,),
        TextButton(
            onPressed: () {
              Navigator.pop(context);
              this.widget.onSuccess();
            },
            child: buildText(context, 'Ok', 21, Colors.blueAccent, FontWeight.normal)
        ),
      ],
    );
  }

  Widget buildState(BuildContext context) {
    if(_state == ComponentState.Waiting) {
      return buildWaiting(context);
    }
    else if(_state == ComponentState.Loading) {
      return buildLoading(context);
    }
    else if(_state == ComponentState.ServerError) {
      return buildServerError(context);
    }
    else if(_state == ComponentState.Rejected) {
      return buildRejected(context);
    }
    else if(_state == ComponentState.Success) {
      return buildSuccess(context);
    }
    return Container();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
        padding: EdgeInsets.all(10),
        child: buildState(context),
    );
  }

}

enum ComponentState {
  Waiting,
  Loading,
  ServerError,
  Rejected,
  Success,
}
