import 'dart:io' as io;
import 'dart:html';
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
  // ChewieController _chewieController;

  @override
  void initState() {
    super.initState();
    setController();
  }

  void setController() async {
    // not working with file
    // _controller = VideoPlayerController.file(this.widget.feedbackVideo.getFile());
    // on web path
    ConnectionHandler connectionHandelr = new ConnectionHandler();
    String url = connectionHandelr.getStreamUrl() + this.widget.feedbackVideoStreamer.getPath();
    // String url = 'https://flutter.github.io/assets-for-api-docs/assets/videos/butterfly.mp4';
    print(url);
    _controller = VideoPlayerController.network(url);
    await _controller.initialize();
    _controller.play();
    // _chewieController = ChewieController(
    //   videoPlayerController: _controller,
    //   autoPlay: true,
    //   looping: true,
    // );
    setState(() {});
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
                    child: Text("Play / Stop"),
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
                  FittedBox(
                    fit:BoxFit.cover,
                    child: SizedBox(
                      width: _controller.value.size?.width / 2 ?? 0,
                      height: _controller.value.size?.height / 2 ?? 0,
                      child: VideoPlayer(_controller),
                    ),
                  ),
                ],
              );
            }
            return CircularProgressIndicator();
          },
        );
  }

  // Widget buildChecw(BuildContext context) {
  //   if(_chewieController != null && _chewieController.videoPlayerController.value.initialized)
  //     return Chewie(
  //       controller: _chewieController,
  //     );
  //   return CircularProgressIndicator();
  // }

  @override
  Widget build(BuildContext context) {
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
    return CircularProgressIndicator();
  }
  
}