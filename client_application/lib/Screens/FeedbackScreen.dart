import 'package:client_application/Components/MediaPlayer.dart';
import 'package:client_application/Screens/Arguments/FeedbackScreenArguments.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'Holders/ColorsHolder.dart';
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

  String getTime(TimeOfDay now) {
    if(now.minute < 10) {
      return '${now.hour}:0${now.minute}';
    }
    return '${now.hour}:${now.minute}';
  }

  Widget buildTitle(BuildContext context, String title) {
    return Text(title,
      textAlign: TextAlign.left,
      style: TextStyle(
        color: Colors.black,
        fontSize: 22 * MediaQuery.of(context).textScaleFactor,
        fontWeight: FontWeight.normal,
      ),
    );
  }

  Widget buildDes(BuildContext context, String description) {
    return Text(description,
      style: TextStyle(
        color: Colors.grey,
        fontSize: 18 * MediaQuery.of(context).textScaleFactor,
        fontWeight: FontWeight.normal,
      ),
    );
  }

  Widget buildViewFeedback(BuildContext context) {
    FeedbackScreenArguments args = this.widget.args;
    return Container(
      margin: EdgeInsets.all(3),
      child: Column(
        children: [
          buildTitle(context, 'Pool ${args.poolNumber}'),
          Container(
            alignment: Alignment.topLeft,
            child: buildDes(context, 'Time: ${getTime(args.poolTime)}'
                '\nStart swimming time: ${args.startTime}'
                '\nEnd swimming time: ${args.endTime}'
            ),
          ),
          SizedBox(height: 10,),
          Card(
            child: Container(
            padding: EdgeInsets.all(8.0),
              child: MediaPlayer(args.link)
            ),
          ),
        ],
      ),
    );
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
          padding: const EdgeInsets.all(5.0),
          child: buildViewFeedback(context),
        ),
      ),
    );
  }
}
