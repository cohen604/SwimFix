import 'package:client_application/Screens/Arguments/FeedbackScreenArguments.dart';
import 'package:client_application/Screens/ColorsHolder.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'Drawers/BasicDrawer.dart';

class FeedbackScreen extends StatefulWidget {

  FeedbackScreenArguments args;

  FeedbackScreen(this.args);

  @override
  _FeedbackScreenState createState() => _FeedbackScreenState();
}

class _FeedbackScreenState extends State<FeedbackScreen> {

  ColorsHolder _colorsHolder;


  _FeedbackScreenState() {
    _colorsHolder = new ColorsHolder();
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
          title: Text("View feedback",),
        ),
        body: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          color: _colorsHolder.getBackgroundForI6(),
          padding: const EdgeInsets.all(16.0),
        ),
      ),
    );
  }
}
