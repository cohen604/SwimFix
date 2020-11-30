import 'dart:io' as io;
import 'dart:html';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class VideoPreview extends StatefulWidget {

  FeedbackVideo feedbackVideo;

  VideoPreview({this.feedbackVideo, Key key}): super(key: key);

  @override
  _VideoPreviewState createState()=> new _VideoPreviewState();
  
}

class _VideoPreviewState extends State<VideoPreview> {

  VideoPlayerController _controller;

  @override
  void initState() {
    super.initState();
    io.File file = this.widget.feedbackVideo.getFile();
    _controller = VideoPlayerController.file(file)..initialize().then(
            (_){ setState(() {});} );
    _controller.setLooping(true);

  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  Widget buildVideoPlayer() {
    return Column(
      children: [
        AspectRatio(
          aspectRatio: _controller.value.aspectRatio,
          child: VideoPlayer(_controller),
        ),
    ]);
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        SizedBox(height: 20,),
        Text('FeedBackVideo type ${this.widget.feedbackVideo.type}'),
        SizedBox(height: 20),
        Text('Size ${this.widget.feedbackVideo.bytes.length}'),
        SizedBox(height: 20),
        buildVideoPlayer(),
        FlatButton(
          onPressed: () {
            setState(() {
              if(_controller.value.isPlaying) {
                _controller.pause();
              }
              else {
                _controller.play();
              }
            });
          },
        )
      ],
    );
  }
  
}