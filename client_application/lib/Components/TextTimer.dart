import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class TextTimer extends StatefulWidget {

  Function onTimerEnd;
  int duration;
  TextTimer(this.onTimerEnd, {this.duration = 3});

  @override
  _TextTimerState createState() => _TextTimerState();
}

class _TextTimerState extends State<TextTimer> {

  Timer timer;
  int counter = 0;

  @override
  void initState() {
    super.initState();
    counter = this.widget.duration;
    Duration duration = Duration(milliseconds: counter*500);
    timer = Timer.periodic(duration, onTick);
  }

  @override
  void dispose() {
    timer?.cancel();
    super.dispose();
  }

  void onTick(Timer timer) {
   setState(() {
     counter--;
     if(counter == 0) {
       this.widget.onTimerEnd();
       timer.cancel();
     }
   });
  }

  @override
  Widget build(BuildContext context) {
    return Center(
        child: CircleAvatar(
          backgroundColor: Colors.white.withAlpha(100),
          radius: 40,
          child: Container(
            padding: const EdgeInsets.all(8.0),
            child: Text('$counter',
              style: TextStyle(
                fontSize: 40 * MediaQuery.of(context).textScaleFactor,
                color: Colors.white,
                fontWeight: FontWeight.bold,
                decoration: TextDecoration.none,
              ),
            ),
          ),
        ));
  }
}
