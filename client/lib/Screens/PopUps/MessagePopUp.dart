import 'package:client/Screens/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';

class MessagePopUp extends StatelessWidget {

  String _msg;
  WebColors _webColors;

  MessagePopUp(String msg) {
    _msg = msg;
    _webColors = new WebColors();
  }

  @override
  Widget build(BuildContext context) {
    return Center(
        child: Container(
          width: MediaQuery.of(context).size.width / 4,
          height: MediaQuery.of(context).size.height / 4,
          padding: EdgeInsets.all(20.0),
          decoration: BoxDecoration(
            borderRadius: BorderRadius.all(Radius.circular(20.0)),
            color: _webColors.getBackgroundPopUp()
          ),
          child: Flexible(
            fit: FlexFit.tight,
            child: Center(
              child: Text(_msg,
                  style: TextStyle(
                    fontSize: 20 * MediaQuery.of(context).textScaleFactor,
                    color:Colors.black,
                    fontWeight: FontWeight.normal,
                    decoration: TextDecoration.none
                  )
              ),
            ),
          ),
        ));
  }

}