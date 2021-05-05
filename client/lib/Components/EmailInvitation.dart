import 'dart:math';

import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/material.dart';

class EmailInvitation extends StatefulWidget {

  WebUser webUser;

  EmailInvitation(this.webUser);

  @override
  _EmailInvitationState createState() => _EmailInvitationState();

}

class _EmailInvitationState extends State<EmailInvitation> {

  ScreenState _state;
  TextEditingController _textController;
  LogicManager logicManager;

  _EmailInvitationState() {
    _state = ScreenState.Preparing;
    _textController = new TextEditingController();
    logicManager = new LogicManager();
  }

  void onCancel(BuildContext context) {
    Navigator.pop(context);
  }

  bool checkEmail(String email) {
    int i1 = email.indexOf("@");
    int i2 = email.lastIndexOf(".");
    return email.isNotEmpty
        && i1 >= 1
        && i2 >= 2
        && i2 > i1
        && email.length - 1 > i2;
  }

  String cleanEmail(String email) {
    return email.replaceAll(String.fromCharCode(143), '');
  }

  void onSendEmail(BuildContext context) {
    String email = cleanEmail(_textController.text);
    if(checkEmail(email)) {
      this.setState(() {
        _state = ScreenState.Pending;
      });
      logicManager.sendInvitationEmail(
          this.widget.webUser.swimmer,
          email)
          .then((response) {
        if (response) {
          this.setState(() {
            _state = ScreenState.Success;
          });
        }
        else {
          this.setState(() {
            _state = ScreenState.Failed;
          });
        }
      });
    }
    else {
      this.setState(() {
        _state = ScreenState.Failed;
      });
    }
  }

  void onRetryEmail(BuildContext context) {
    this.setState(() {
      _state = ScreenState.Preparing;
    });
  }

  Widget buildPreparingButtonBar(BuildContext context) {
    return Align(
      alignment: Alignment.centerRight,
      child: Wrap(
        spacing: 10,
        children: [
          TextButton(
              onPressed: ()=>onCancel(context),
              child: Text("Cancel")
          ),
          TextButton(
              onPressed: ()=>onSendEmail(context),
              child: Text("Send")
          ),
        ],
      ),
    );
  }

  Widget buildPreparing(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(10),
      child: Wrap(
        spacing: 10,
        direction: Axis.horizontal,
        children: [
          Text('Send email invitation',),
          TextField(
            controller: _textController,
            textAlign: TextAlign.center,
            decoration: InputDecoration(
              hintText: "Swimmer's email",
            ),
          ),
          buildPreparingButtonBar(context),
        ],
      ),
    );
  }

  Widget buildPending(BuildContext context) {
    return Container(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          CircularProgressIndicator(),
          Text('Sending email'),
        ],
      ),
    );
  }

  Widget buildSuccess(BuildContext context) {
    return Text('Email send to ${_textController.text}');
  }

  Widget buildFailedButtonBar(BuildContext context) {
    return Align(
      alignment: Alignment.centerRight,
      child: Wrap(
        spacing: 10,
        children: [
          TextButton(
              onPressed: ()=>onCancel(context),
              child: Text("Cancel")
          ),
          TextButton(
              onPressed: ()=>onRetryEmail(context),
              child: Text("Retry")
          ),
        ],
      ),
    );
  }

  Widget buildFailed(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Icon(
          Icons.warning_amber_outlined,
          color: Colors.yellow,
          size: 35,
        ),
        Text("Email didn't send.\n"
            "Make sure the inserted email is correct."),
        buildFailedButtonBar(context),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    if(_state == ScreenState.Preparing) {
      return buildPreparing(context);
    }
    else if(_state == ScreenState.Pending) {
      return buildPending(context);
    }
    else if(_state == ScreenState.Success) {
      return buildSuccess(context);
    }
    return buildFailed(context);
  }
}

enum ScreenState {
  Preparing,
  Pending,
  Success,
  Failed,
}