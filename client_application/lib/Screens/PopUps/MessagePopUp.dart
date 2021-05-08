import 'file:///C:/Users/avrah/Desktop/semesterA/final_project/SwimFix/client_application/lib/Screens/Holders/ColorsHolder.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';

class MessagePopUp extends StatelessWidget {

  String _msg;
  ColorsHolder _colorsHolder;

  MessagePopUp(String msg) {
    _msg = msg;
    _colorsHolder = new ColorsHolder();
  }

  @override
  Widget build(BuildContext context) {
    return Center(
        child: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height / 4,
          margin: EdgeInsets.all(10.0),
          padding: EdgeInsets.all(20.0),
          decoration: BoxDecoration(
            borderRadius: BorderRadius.all(Radius.circular(20.0)),
            color: _colorsHolder.getBackgroundPopUp()
          ),
          child: Text(_msg,
                  style: TextStyle(
                    fontSize: 16 * MediaQuery.of(context).textScaleFactor,
                    color:Colors.black,
                    fontWeight: FontWeight.normal,
                    decoration: TextDecoration.none
                  )
              ),
            ),
        );
  }

}