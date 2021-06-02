import 'package:chewie/chewie.dart';
import 'package:client_application/Components/CommentComp.dart';
import 'package:client_application/Components/MediaPlayer.dart';
import 'package:client_application/Domain/DTO/DateTimeDTO.dart';
import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Domain/Video/FeedbackComment.dart';
import 'package:client_application/Domain/Video/FeedbackData.dart';
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
  ScreenState _screenState;
  FeedbackData _feedbackData;
  List<FeedbackComment> _comments;

  _HistoryFeedbackScreenState() {
    _logicManager = LogicManager.getInstance();
    _colorsHolder = new ColorsHolder();
    _screenState = ScreenState.Loading;
  }

  @override
  void initState() {
    super.initState();
    Swimmer swimmer = this.widget.arguments.user.swimmer;
    String path = this.widget.arguments.link.path;
    _logicManager.getFeedbackData(swimmer, path).then(
            (FeedbackData feedback) {
          if(feedback != null) {
            this.setState(() {
              _feedbackData = feedback;
              _screenState = ScreenState.View;
              _comments = feedback.comments;
            });
          }
          else {
            this.setState(() {
              _screenState = ScreenState.Error;
            });
          }
        }
    );
  }

  void onBack(BuildContext context) {
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
          fontSize: size * MediaQuery
              .of(context)
              .textScaleFactor,
          color: color,
          fontWeight: fontWeight,
          decoration: TextDecoration.none
      ),
    );
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
            fontSize: 21,
            color: Colors.black,
            fontWeight: FontWeight.bold
          ),
        ),
      ),
    );
  }

  Widget buildEmptyCommentsList(BuildContext context) {
    return Center(
      child: buildText(context, 'There are no comments to feedback', 21, Colors.black, FontWeight.normal),
    );
  }

  Widget buildCommentsList(BuildContext context) {
    if (_comments.isEmpty) {
      return buildEmptyCommentsList(context);
    }
    return ListView.builder(
        itemCount: _comments.length,
        itemBuilder: (BuildContext context, int index) {
          return CommentComp(_comments[index]);
        }
    );
  }

  Widget buildComments(BuildContext context) {
    return Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        color: _colorsHolder.getBackgroundForI3(),
        padding: EdgeInsets.all(5),
        child: Card(
          color: _colorsHolder.getBackgroundForI4(),
          child: Column(
            children: [
              buildCommentsTitle(context),
              Expanded(child: buildCommentsList(context))
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

  Widget buildLoading(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              CircularProgressIndicator(),
              SizedBox(height: 5.0,),
              buildText(context, 'Loading feedback...', 21, Colors.black, FontWeight.normal),
            ],
          )
      ),
    );
  }

  Widget buildError(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Center(
          child: Column(
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
                  18, Colors.black, FontWeight.normal,
                  textAlign: TextAlign.center),
            ],
          )
      ),
    );
  }

  Widget buildScreenState(BuildContext context) {
    if(_screenState == ScreenState.Loading) {
      return buildLoading(context);
    }
    else if(_screenState == ScreenState.View) {
      return buildView(context);
    }
    else if(_screenState == ScreenState.Error) {
      return buildError(context);
    }
    return Container();
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
              child: buildScreenState(context)
          ),
        ),
      ),
    );
  }

}

enum ScreenState {
  Loading,
  View,
  Error,
}



