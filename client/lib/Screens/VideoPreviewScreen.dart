import 'package:chewie/chewie.dart';
import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Services/connectionHandler.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';
import 'package:flutter/foundation.dart' show kIsWeb;

class VideoPreviewScreen extends StatefulWidget {

  FeedbackVideo feedbackVideo;
  FeedbackVideoStreamer feedbackVideoStreamer;

  VideoPreviewScreen({Key key}): super(key: key){
    LogicManager logicManager = LogicManager.getInstance();
    this.feedbackVideo = logicManager.getFeedbackVideo();
    this.feedbackVideoStreamer = logicManager.getFeedbackVideoStreamer();
  }

  @override
  _VideoPreviewScreenState createState()=> new _VideoPreviewScreenState();

}

class _VideoPreviewScreenState extends State<VideoPreviewScreen> {

  VideoPlayerController _controller;
  Future<void> _futureController;
  ChewieController _chewieController;

  @override
  void initState() {
    super.initState();
    setController();
  }

  void setController() async {
    if(LogicManager.getInstance().getFeedbackVideoStreamer() != null) {
      ConnectionHandler connectionHandelr = new ConnectionHandler();
      String url = connectionHandelr.getStreamUrl() + this.widget.feedbackVideoStreamer.getPath();
      print(url);
      _controller = VideoPlayerController.network(url);
      await _controller.initialize();
      _controller.play();

      _chewieController = ChewieController(
        videoPlayerController: _controller,
        autoPlay: true,
        looping: false,
        //note: this muse be false cause chewiew having problem in full screen
        allowFullScreen: (kIsWeb) ? false : true,
        fullScreenByDefault: (kIsWeb) ? false : true,
        allowMuting: false,
      );
      //DO NOT DELETE THIS!!
      setState(() {});
    }
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  Widget buildChecw(BuildContext context) {
    if(LogicManager.getInstance().getFeedbackVideoStreamer() == null) {
      return SafeArea(
        child: Scaffold(
            appBar: AppBar(
              title: Text("Video Preview"),
            ),
            body: SingleChildScrollView(
                child: Center(
                  child: Text("Video didnt uploaded"),
                ),
            ),
        ),
      );
    }
    if(_chewieController != null && _chewieController.videoPlayerController.value.initialized) {
      return SafeArea(
        child: Scaffold(
          appBar: AppBar(
            title: Text("Video Preview"),
          ),
          body: SingleChildScrollView(
            child: Container(
                child:Chewie(
                  controller: _chewieController,)
            ),
          ),
        ),
      );
    }
    return Center(
      child:CircularProgressIndicator(),
    );
  }

  Widget buildVideoPlayer(BuildContext context) {
    if(_controller != null && _controller.value.initialized)
      return FittedBox(
          fit:BoxFit.cover,
          child: SizedBox(
            width: _controller.value.size?.width ?? 0,
            height: _controller.value.size?.height ?? 0,
            child: Container(
                child:VideoPlayer( _controller))
          )
      );
    return Center(
      child:CircularProgressIndicator()
    );
  }

  @override
  Widget build(BuildContext context) {
    return buildChecw(context);
  }
  
}