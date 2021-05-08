import 'package:chewie/chewie.dart';
import 'package:client_application/Components/MediaPlayer.dart';
import 'package:client_application/Domain/DTO/DateTimeDTO.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

import 'Arguments/ViewFeedbackArguments.dart';
import 'Drawers/BasicDrawer.dart';
import 'Holders/ColorsHolder.dart';

class HistoryFeedbackScreen extends StatefulWidget {

  final HistoryFeedBackArguments arguments;
  HistoryFeedbackScreen({Key key, this.arguments}) : super(key: key);

  @override
  _HistoryFeedbackScreenState createState() => _HistoryFeedbackScreenState();
}

class _HistoryFeedbackScreenState extends State<HistoryFeedbackScreen> {

  LogicManager _logicManager;
  ColorsHolder _colorsHolder;

  _HistoryFeedbackScreenState() {
    _logicManager = LogicManager.getInstance();
    _colorsHolder = new ColorsHolder();
  }

  void onBack(BuildContext context) {
    Navigator.pop(context);
  }

  Widget buildBackButton(BuildContext context) {
    return Align(
      alignment: Alignment.topLeft,
      child: IconButton(
        icon: Icon(Icons.arrow_back,
          size: 30,
        ),
        onPressed: ()=>onBack(context),
      ),
    );
  }

  Widget buildVideo(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height / 2,
      color: _colorsHolder.getBackgroundForI3(),
      padding: EdgeInsets.all(15),
      child: Center(
          child: MediaPlayer(this.widget.arguments.link)
      ),
    );
  }

  Widget buildCommentsTitle(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Center(
        child: Text('Comments',
          style: TextStyle(
            fontSize: 24,
            color: Colors.black,
          ),
        ),
      ),
    );
  }

  Widget buildComments(BuildContext context) {
    return Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        color: _colorsHolder.getBackgroundForI3(),
        padding: EdgeInsets.all(10),
        child: Card(
          child: Column(
            children: [
              buildCommentsTitle(context),
            ]
          ),
        )
    );
  }

  Widget buildView(BuildContext context) {
    return Column(
      children: [
        buildVideo(context),
        Expanded(
            child: buildComments(context)
        ),
      ],
    );
  }

  String getStringTitle(String path) {
    int start = path.lastIndexOf("\\") + 1;
    int end = path.lastIndexOf(".");
    String output = path.substring(start, end);
    return output.replaceAll("-", ".");
  }

  @override
  Widget build(BuildContext context) {
    String path = this.widget.arguments.link.path;
    return SafeArea(
      child: Scaffold(
        drawer: BasicDrawer(
            this.widget.arguments.user
        ),
        appBar: AppBar(
          backgroundColor: Colors.blue,
          title: Text("Feedback ${getStringTitle(path)}",),
        ),
        body: SingleChildScrollView(
          child: Container(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height,
              color: _colorsHolder.getBackgroundForI6(),
              child: buildView(context)
          ),
        ),
      ),
    );
  }

}



