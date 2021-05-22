import 'package:chewie/chewie.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
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
  VideoPlayerController _controller;
  ChewieController _chewieController;


  _WebViewFeedbackScreenState() {
    _logicManager = LogicManager.getInstance();
    _webColors = WebColors.getInstance();
  }

  @override
  void initState() {
    super.initState();
    String path = _logicManager.getStreamUrl() + this.widget.arguments.link.path;
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
      height: MediaQuery.of(context).size.height / 1.1,
      color: _webColors.getBackgroundForI3(),
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

  Widget buildComments(BuildContext context) {
    return Container(
        width: MediaQuery.of(context).size.width / 4,
        height: MediaQuery.of(context).size.height / 1.1,
        color: _webColors.getBackgroundForI3(),
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

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          child: SingleChildScrollView(
            child: Column(
              children: [
                MenuBar(user: this.widget.arguments.user,),
                buildView(context)
              ],
            ),
          ),
        ),
      ),
    );
  }


}



