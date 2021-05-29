import 'package:client/Domain/Invitations/Invitation.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class InvitationApprove extends StatefulWidget {

  final Swimmer swimmer;
  final Invitation invitation;
  final Function onSuccess;
  final bool isApprove; // if is approve then make the join request ot

  InvitationApprove(this.swimmer, this.invitation, this.onSuccess, this.isApprove);

  @override
  _InvitationApproveState createState() => _InvitationApproveState();
}

class _InvitationApproveState extends State<InvitationApprove> {

  ComponentState _state;
  WebColors _webColors;
  LogicManager _logicManager;

  _InvitationApproveState() {
    _state = ComponentState.Waiting;
    _webColors = WebColors.getInstance();
    _logicManager = LogicManager.getInstance();
  }

  void onClickNo(BuildContext context) {
    Navigator.pop(context);
  }

  void processLogicMangerResult(bool result) {
    if(result == null) {
      this.setState(() {
        _state = ComponentState.ErrorServer;
      });
    }
    else if(result) {
      this.setState(() {
        _state = ComponentState.Success;
      });
    }
    else {
      this.setState(() {
        _state = ComponentState.Rejected;
      });
    }
  }

  void onClickYes(BuildContext context) {
    Swimmer swimmer = this.widget.swimmer;
    String invitationId = this.widget.invitation.id;
    if(this.widget.isApprove) {
      _logicManager.approveInvitation(swimmer, invitationId)
          .then((bool result) =>processLogicMangerResult(result));
    }
    else {
      _logicManager.denyInvitation(swimmer, invitationId)
          .then((bool result) =>processLogicMangerResult(result));
    }
    this.setState(() {
      _state = ComponentState.Loading;
    });
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

  String getWaitingString() {
    if(this.widget.isApprove) {
      return 'join';
    }
    return 'delete';
  }

  Widget buildWaiting(BuildContext context){
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        buildText(context, 'Do you want to ${getWaitingString()} ${this.widget.invitation.teamId}?',
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

  String getLoadingString() {
    if(this.widget.isApprove) {
      return 'approve';
    }
    return 'deny';
  }

  Widget buildLoading(BuildContext context) {
    return Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          CircularProgressIndicator(),
          SizedBox(height: 5.0,),
          buildText(context, 'Sending ${getLoadingString()} invitation...', 21, Colors.black, FontWeight.normal),
        ],
    );
  }

  Widget buildError(BuildContext context) {
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

  String getRejectedString() {
    if(this.widget.isApprove) {
      return 'join';
    }
    return 'deny';
  }

  Widget buildRejected(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        buildText(context, 'Your request to ${getRejectedString()} ${this.widget.invitation.teamId} invitation was rejected', 16, Colors.black, FontWeight.normal),
        buildText(context, 'Talk with your coach', 18, Colors.black, FontWeight.normal),
        SizedBox(height: 5.0,),
        Align(
          alignment: Alignment.topRight,
          child: TextButton(
              onPressed: ()=>Navigator.pop(context),
              child: buildText(context, 'ok', 18, Colors.blueAccent, FontWeight.normal)
          ),
        )
      ],
    );
  }

  String getSuccessString() {
    if(this.widget.isApprove) {
      return 'joined';
    }
    return 'denied';
  }

  Widget buildSuccess(BuildContext context) {
    return Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          buildText(context, 'You ${getSuccessString()} ${this.widget.invitation.teamId}', 21, Colors.black, FontWeight.normal),
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
    else if(_state == ComponentState.ErrorServer) {
      return buildError(context);
    }
    else if(_state == ComponentState.Success) {
      return buildSuccess(context);
    }
    else if(_state == ComponentState.Rejected) {
      return buildRejected(context);
    }
    return Container();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(10),
      child: buildState(context),
    );
  }

}

enum ComponentState {
  Waiting,
  Loading,
  ErrorServer,
  Rejected,
  Success,
}