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
  Future<void> _futureController;
  @override
  void initState() {
    io.File file = this.widget.feedbackVideo.getFile();
    _controller = VideoPlayerController.file(file);
    _futureController = _controller.initialize();
    _controller.setLooping(true);
    _controller.setVolume(35);
    super.initState();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  Widget buildVideoPlayer() {
    return
        FutureBuilder(
          future: _futureController,
          builder: (BuildContext context, AsyncSnapshot<void> snapshot){
            if(snapshot.connectionState == ConnectionState.done) {
              return
              Column(
                children: [
                  FlatButton(
                    child: Text("Click Me"),
                    onPressed: () {
                      setState(() {
                        if(_controller.value.isPlaying) {
                          print('stop');
                          _controller.pause();
                        }
                        else {
                          print('play');
                          _controller.play();
                        }
                      });
                    }),
                  AspectRatio(
                    aspectRatio: _controller.value.aspectRatio,
                    child: VideoPlayer(_controller),
                  ),
                ],
              );
            }
            return CircularProgressIndicator();
          },
        );
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
      ],
    );
  }
  
}