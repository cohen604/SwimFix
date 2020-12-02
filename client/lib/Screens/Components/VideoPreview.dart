import 'dart:io' as io;
import 'dart:html';
import 'package:chewie/chewie.dart';
import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Services/connectionHandler.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class VideoPreview extends StatefulWidget {

  FeedbackVideo feedbackVideo;
  FeedbackVideoStreamer feedbackVideoStreamer;
  VideoPreview({this.feedbackVideo, this.feedbackVideoStreamer, Key key}): super(key: key);

  @override
  _VideoPreviewState createState()=> new _VideoPreviewState();

}

class _VideoPreviewState extends State<VideoPreview> {

  VideoPlayerController _controller;
  Future<void> _futureController;
  ChewieController _chewieController;

  @override
  void initState() {
    super.initState();
    setController();
  }

  void setController() async {
    // on web path
    ConnectionHandler connectionHandelr = new ConnectionHandler();
    String url = connectionHandelr.getStreamUrl() + this.widget.feedbackVideoStreamer.getPath();
    // print(url);
    _controller = VideoPlayerController.network(url);
    await _controller.initialize();
    _controller.play();
    _chewieController = ChewieController(
      videoPlayerController: _controller,
      autoPlay: true,
      looping: true,
      //note: this muse be false cause chewiew having problem in full screen
      allowFullScreen: false,
    );
    setState(() {});
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  Widget buildChecw(BuildContext context) {
    if(_chewieController != null && _chewieController.videoPlayerController.value.initialized)
      return FittedBox(
        fit:BoxFit.cover,
        child: SizedBox(
          width: _controller.value.size?.width ?? 0,
          height: _controller.value.size?.height ?? 0,
          child: Container(
            child:Chewie(
              controller: _chewieController,
            ),
          ),
        ),
      );
    return CircularProgressIndicator();
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