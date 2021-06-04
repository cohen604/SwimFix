import 'package:chewie/chewie.dart';
import 'package:client/Components/CommentComp.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Feedback/FeedbackComment.dart';
import 'package:client/Domain/Feedback/FeedbackData.dart';
import 'package:client/Domain/Users/Swimmer.dart';
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

  VideoPlayerController _videoController;
  ChewieController _chewieController;
  TextEditingController _textController;

  FeedbackData _feedbackData;
  List<FeedbackComment> _comments;

  _WebCoachFeedbackScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _logicManager = LogicManager.getInstance();
    _screenState = ScreenState.Loading;
    _textController = new TextEditingController();
  }

  @override
  void initState() {
    super.initState();
    Swimmer coach = this.widget.args.user.swimmer;
    String swimmersEmail = this.widget.args.feedbackInfo.swimmer;
    String feedbackKey = this.widget.args.feedbackInfo.key;
    _logicManager.coachGetFeedbackData(coach, swimmersEmail, feedbackKey).then(
        (FeedbackData data) {
          if(data == null) {
            this.setState(() {
              _screenState = ScreenState.Error;
            });
          }
          else {
            initVideoPlayers();
            this.setState(() {
              _feedbackData = data;
              _screenState = ScreenState.View;
              _comments = data.comments;
            });
          }
        }
    );
  }

  void initVideoPlayers() {
    String path = _logicManager.getStreamUrl() + this.widget.args.feedbackInfo.feedbackLink;
    print('the path is ' + path);
    _videoController = VideoPlayerController.network(path);
    _videoController.initialize();
    _videoController.play();
    _chewieController = ChewieController(
      videoPlayerController: _videoController,
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
    _textController.dispose();
    if(_videoController != null) {
      _videoController.dispose();
    }
    if(_chewieController != null) {
      _chewieController.dispose();
    }
  }

  void updateFeedbackData() {
    Swimmer coach = this.widget.args.user.swimmer;
    String swimmersEmail = this.widget.args.feedbackInfo.swimmer;
    String feedbackKey = this.widget.args.feedbackInfo.key;
    _logicManager.coachGetFeedbackData(coach, swimmersEmail, feedbackKey).then(
            (FeedbackData data) {
          if(data != null) {
            this.setState(() {
              _textController.text = "";
              _feedbackData = data;
              _comments = data.comments;
            });
          }
        }
    );
  }

  void showServerFailMsg(BuildContext context) {
    showDialog(
        context: context,
        builder: (_) => AlertDialog(
          content:Column(
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
                  24, Colors.black, FontWeight.normal),
            ],
          )
        )
    );
  }

  void showInvalidCommentMsg(BuildContext context) {
    _textController.text = "";
    showDialog(
        context: context,
        builder: (_) => AlertDialog(
            content:Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisSize: MainAxisSize.min,
              children: [
                Icon(
                  Icons.warning_amber_outlined,
                  size: 45,
                  color: Colors.yellow,
                ),
                SizedBox(height: 5.0,),
                buildText(context, 'Invalid comment. Please insert new comment',
                    24, Colors.black, FontWeight.normal),
              ],
            )
        )
    );
  }

  void onAddComment(BuildContext context, String text) {
    if(text.isEmpty || text.replaceAll(" ", "").isEmpty) {
      showInvalidCommentMsg(context);
    }
    else {
      Swimmer coach = this.widget.args.user.swimmer;
      String swimmersEmail = this.widget.args.feedbackInfo.swimmer;
      String feedbackKey = this.widget.args.feedbackInfo.key;
      _logicManager.coachAddFeedbackComment(coach, swimmersEmail, feedbackKey, text)
          .then((bool added) {
            if (added == null) {
              showServerFailMsg(context);
            }
            else if (added) {
              updateFeedbackData();
            }
            else {
              showServerFailMsg(context);
            }
        }
      );
    }
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
      color: _webColors.getBackgroundForI3(),
      child: Chewie(
        controller: _chewieController,
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

 Widget buildAddComment(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(3),
      decoration: BoxDecoration(
        border: Border.all(
            color: Colors.black
        ),
        color: _webColors.getBackgroundForI6()
      ),
      child: Padding(
        padding: const EdgeInsets.all(5.0),
        child: Row(
          children: [
            Expanded(
                child: TextField(
                  controller: _textController,
                  minLines: 1,
                  maxLines: 3,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Comment text',
                  ),
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 16.0,
                    color: Colors.black,
                  ),
                )
            ),
            Padding(
                padding: const EdgeInsets.all(3),
                child: IconButton(
                  icon: Icon(
                      Icons.add
                  ),
                  color: _webColors.getBackgroundForI2(),
                  iconSize: 35,
                  onPressed: ()=>onAddComment(context, _textController.text),
                )
            ),
          ],
        ),
      ),
    );
 }

  Widget buildComments(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      child: Container(
        color: _webColors.getBackgroundForI4(),
        child: Padding(
          padding: const EdgeInsets.all(5.0),
          child: Column(
            children: [
              buildText(context, 'Comments', 26, Colors.black, FontWeight.bold, textAlign: TextAlign.left),
              Expanded(
                  child: buildCommentsList(context),
              ),
              buildAddComment(context),
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
              24, Colors.black, FontWeight.normal),
          buildText(context, this.widget.args.feedbackInfo.swimmer,
              24, Colors.black, FontWeight.normal),
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
          image: AssetImage(_assetsHolder.getBackGroundImage()),
          fit: BoxFit.fill,
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

