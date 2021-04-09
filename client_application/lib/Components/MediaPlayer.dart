import 'package:chewie/chewie.dart';
import 'package:client_application/Services/ConnectionHandler.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';
import 'package:client_application/Domain/Video/FeedBackVideoStreamer.dart';
import 'package:flutter/cupertino.dart';

class MediaPlayer extends StatefulWidget {

  FeedbackVideoStreamer streamer;

  MediaPlayer(this.streamer);

  @override
  _MediaPlayerState createState() => _MediaPlayerState();
}

class _MediaPlayerState extends State<MediaPlayer> {

  VideoPlayerController _controller;
  ChewieController _chewieController;

  @override
  void initState() {
    super.initState();
    ConnectionHandler connectionHandelr = new ConnectionHandler();
    String url = connectionHandelr.getStreamUrl() +
        this.widget.streamer.getPath();
    print(url);
    _controller = VideoPlayerController.network(url);
    _controller.initialize().then(
        (value) {
      _controller.play();
      _chewieController = ChewieController(
        videoPlayerController: _controller,
        // aspectRatio: 16 / 9,
        autoPlay: true,
        looping: false,
        //note: this muse be false cause chewiew having problem in full screen
        allowFullScreen: true,
        fullScreenByDefault: false,
        allowMuting: false,
        playbackSpeeds: [0.125, 0.25, 0.5, 0.75, 1],
      );
      //DO NOT DELETE THIS!!
      setState(() {});
    });
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  Widget buildChewie(BuildContext context) {
    if(this.widget.streamer == null) {
      return Center(
        child: Text("Video didnt uploaded"),
      );
    }
    if(_chewieController != null && _chewieController.videoPlayerController.value.initialized) {
      return AspectRatio(
        aspectRatio: 1,
        child: Chewie(
          controller: _chewieController,
        ),
      );
    }
    return Column(
      children: [
        Center(
          child: CircularProgressIndicator(),
        ),
        SizedBox(height: 10,),
        Center(
            child: Text('Loading Media',
              style: TextStyle(
                fontSize: 18 * MediaQuery.of(context).textScaleFactor,
              ),
            )
        )
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: buildChewie(context),
    );
  }
}
