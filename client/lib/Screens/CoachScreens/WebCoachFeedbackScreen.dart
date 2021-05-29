import 'package:chewie/chewie.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Screens/CoachScreens/Arguments/CoachFeedbackScreenArguments.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class WebCoachFeedbackScreen extends StatefulWidget {

  final CoachFeedbackScreenArguments args;

  WebCoachFeedbackScreen(this.args);

  @override
  _WebCoachFeedbackScreenState createState() => _WebCoachFeedbackScreenState();
}

class _WebCoachFeedbackScreenState extends State<WebCoachFeedbackScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;
  LogicManager _logicManager;
  ScreenState _screenState;

  VideoPlayerController _controller;
  ChewieController _chewieController;

  _WebCoachFeedbackScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _logicManager = LogicManager.getInstance();
    _screenState = ScreenState.View;
  }

  @override
  void initState() {
    super.initState();
    String path = _logicManager.getStreamUrl() + this.widget.args.feedbackInfo.feedbackLink;
    print('the path is ' + path);
    _controller = VideoPlayerController.network(path);
    _controller.initialize();
    _controller.play();
    _chewieController = ChewieController(
      videoPlayerController: _controller,
      autoPlay: false,
      looping: false,
      aspectRatio: 16 / 9,
      //note: this muse be false cause chewiew having problem in full screen
      allowFullScreen: false,
      fullScreenByDefault: false,
      allowMuting: false,
      playbackSpeeds: [0.1, 0.25, 0.5, 1],
    );
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
    _chewieController.dispose();
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

  Widget buildLoading(BuildContext context) {
    return Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            CircularProgressIndicator(),
            SizedBox(height: 5.0,),
            buildText(context, 'Loading feedback...', 24, Colors.black, FontWeight.normal),
          ],
        )
    );
  }

  Widget buildError(BuildContext context) {
    return Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
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
                24, Colors.black, FontWeight.normal,
                textAlign: TextAlign.center),
          ],
        )
    );
  }

  Widget buildVideo(BuildContext context) {
    return Container(
      height: MediaQuery.of(context).size.height,
      color: _webColors.getBackgroundForI3().withAlpha(120),
      padding: EdgeInsets.all(5),
      child: Chewie(
        controller: _chewieController,
      ),
    );
  }

  Widget buildComment(BuildContext context) {
    return Container();
  }

  Widget buildEmptyCommentsList(BuildContext context) {
    return Container();
  }

  Widget buildCommentsList(BuildContext context) {
    return Container();
  }

  Widget buildComments(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      padding: const EdgeInsets.all(10.0),
      child: Card(
        child: Padding(
          padding: const EdgeInsets.all(5.0),
          child: Column(
            children: [
              buildText(context, 'Comments', 28, _webColors.getBackgroundForI2(), FontWeight.normal),
            ],
          ),
        ),
      ),
    );
  }

  Widget buildAreaVideoAndComments(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height / 1.5,
      child: Row(
        children: [
          Flexible(
            flex: 2,
              child: buildVideo(context)
          ),
          Flexible(
              child: buildComments(context)
          )
        ],
      ),
    );
  }

  Widget buildAreaForGraphs(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
    );
  }
  
  Widget buildTitle(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(15),
      alignment: Alignment.topLeft,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          buildText(context, this.widget.args.feedbackInfo.date.toString(),
              26, Colors.black, FontWeight.normal),
          buildText(context, this.widget.args.feedbackInfo.swimmer,
              30, Colors.black, FontWeight.normal),
        ],
      ),
    );
  }

  Widget buildView(BuildContext context) {
    return Column(
      children: [
        buildTitle(context),
        buildAreaVideoAndComments(context),
        buildAreaForGraphs(context),
      ],
    );
  }

  Widget buildScreenState(BuildContext context) {
    if(_screenState == ScreenState.View) {
      return buildView(context);
    }
    else if(_screenState == ScreenState.Loading) {
      return buildLoading(context);
    }
    else if(_screenState == ScreenState.Error) {
      return buildError(context);
    }
    return Container();
  }

  Widget buildMainArea(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getSwimmerBackGround()),
          fit: BoxFit.fill,
          colorFilter: ColorFilter.mode(
              _webColors.getBackgroundForI6(),
              BlendMode.hardLight),
        ),
      ),
      child: SingleChildScrollView(
        child: Scrollbar(
          child: buildScreenState(context)
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          child: Column(
            children: [
              MenuBar(
                user: this.widget.args.user,
              ),
              Expanded(
                child: buildMainArea(context)
              ),
            ],
          ),
        ),
      ),
    );
  }
}

enum ScreenState {
  Loading,
  Error,
  View
}

