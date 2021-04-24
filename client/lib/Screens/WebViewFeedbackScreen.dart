import 'package:chewie/chewie.dart';
import 'package:client/Components/MenuBar.dart';
import 'package:client/Screens/Arguments/ViewFeedbackArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class WebViewFeedbackScreen extends StatefulWidget {

  final ViewFeedBackArguments arguments;
  WebViewFeedbackScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebViewFeedbackScreenState createState() => _WebViewFeedbackScreenState();
}

class _WebViewFeedbackScreenState extends State<WebViewFeedbackScreen> {

  VideoPlayerController _controller;
  ChewieController _chewieController;
  LogicManager _logicManager;


  @override
  void initState() {
    super.initState();
    _logicManager = LogicManager.getInstance();
    String path = _logicManager.getStreamUrl() + this.widget.arguments.path;
    print('the path is ' + path);
    _controller = VideoPlayerController.network(path);
    _controller.initialize();
    _controller.play();
    _chewieController = ChewieController(
      videoPlayerController: _controller,
      // aspectRatio: 16 / 9,
      autoPlay: true,
      looping: false,
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

  Widget buildViewStep(BuildContext context) {
    return Material(
      child: SingleChildScrollView(
        child: AspectRatio(
          aspectRatio: 16 / 9,
          child: Chewie(
            controller: _chewieController,
          ),
        ),
      ),
    );
  }


  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Column(
          children: [
            MenuBar(user: this.widget.arguments.user,),
            Flexible(
                child: buildViewStep(context)
            ),
          ],
        ),
      ),
    );
  }


}



