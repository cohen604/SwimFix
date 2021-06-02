import 'package:chewie/chewie.dart';
import 'package:client/Components/CommentComp.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Feedback/FeedbackComment.dart';
import 'package:client/Domain/Feedback/FeedbackData.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';
import 'Arguments/ViewFeedbackArguments.dart';

class WebViewFeedbackScreen extends StatefulWidget {

  final ViewFeedBackArguments arguments;
  WebViewFeedbackScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebViewFeedbackScreenState createState() => _WebViewFeedbackScreenState();
}

class _WebViewFeedbackScreenState extends State<WebViewFeedbackScreen> {

  LogicManager _logicManager;
  WebColors _webColors;
  AssetsHolder _assetsHolder;

  VideoPlayerController _controller;
  ChewieController _chewieController;

  ScreenState _state;
  FeedbackData _feedbackData;
  List<FeedbackComment> _comments;

  _WebViewFeedbackScreenState() {
    _logicManager = LogicManager.getInstance();
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _state = ScreenState.Loading;
  }

  @override
  void initState() {
    super.initState();
    Swimmer swimmer = this.widget.arguments.user.swimmer;
    String path = this.widget.arguments.link.path;
    _logicManager.getFeedbackData(swimmer, path).then(
        (FeedbackData feedback) {
          if(feedback != null) {
            initVideoControllers();
            this.setState(() {
              _feedbackData = feedback;
              _state = ScreenState.View;
              _comments = feedback.comments;
            });
          }
          else {
            this.setState(() {
              _state = ScreenState.Error;
            });
          }
        }
    );
  }

  void initVideoControllers() {
    String path = _logicManager.getStreamUrl() + this.widget.arguments.link.getPath();
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
    if(_controller!=null) {
      _controller.dispose();
    }
    if(_chewieController != null) {
      _chewieController.dispose();
    }
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
      height: MediaQuery.of(context).size.height / 1.1,
      padding: EdgeInsets.all(15),
      child: Chewie(
          controller: _chewieController,
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

  Widget buildEmptyCommentsList(BuildContext context) {
    return Center(
      child: buildText(context, 'There are no comments to feedback', 21, Colors.black, FontWeight.normal),
    );
  }

  Widget buildCommentsList(BuildContext context) {
    if(_comments.isEmpty) {
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
        width: MediaQuery.of(context).size.width / 4,
        height: MediaQuery.of(context).size.height / 1.1,
        padding: EdgeInsets.all(10),
        child: Card(
          color: _webColors.getBackgroundForI3(),
          child: Column(
            children: [
              buildCommentsTitle(context),
              Expanded(
                  child: buildCommentsList(context)
              ),
            ]
          ),
        )
    );
  }

  Widget buildView(BuildContext context) {
    return Stack(
      children: [
        Row(
          children: [
            Expanded(
                child: buildVideo(context)
            ),
            buildComments(context),
          ],
        ),
        buildBackButton(context),
      ],
    );
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
              buildText(context, 'Loading feedback...', 24, Colors.black, FontWeight.normal),
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
                  24, Colors.black, FontWeight.normal,
                  textAlign: TextAlign.center),
            ],
          )
      ),
    );
  }

  Widget buildScreenState(BuildContext context) {
    if(_state == ScreenState.Loading) {
      return buildLoading(context);
    }
    else if(_state == ScreenState.Error) {
      return buildError(context);
    }
    else if(_state == ScreenState.View) {
      return buildView(context);
    }
    return Container();
  }

  Widget buildMainArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
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
            mainAxisSize: MainAxisSize.max,
            children: [
              MenuBar(user: this.widget.arguments.user,),
              Expanded(
                  child: buildMainArea(context)
              )
            ],
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


