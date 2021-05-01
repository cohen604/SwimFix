import 'package:flutter/cupertino.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

/// This class is simple video player from assets!!
class SimpleVideoPlayer extends StatefulWidget {

  String path;
  SimpleVideoPlayer(this.path);

  @override
  _SimpleVideoPlayerState createState() => _SimpleVideoPlayerState();
}

class _SimpleVideoPlayerState extends State<SimpleVideoPlayer> {

  VideoPlayerController _controller;
  bool _showButtons = false;

  @override
  void initState() {
    super.initState();
    _controller = VideoPlayerController.asset(this.widget.path);
    _controller.initialize().then( (_) {
      _controller.addListener(videoListener);
      setState(() {});
    });
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  void videoListener() {
    if(_controller.value.position == _controller.value.duration) {
      this.setState(() {});
    }
  }

  void onEnterVideo(PointerEnterEvent event) {
    this.setState(() {
      _showButtons = true;
    });
  }

  void onLeaveVideo(PointerExitEvent event) {
    this.setState(() {
      _showButtons = false;
    });
  }

  Widget buildVideoButtons(BuildContext context) {
    if(!_showButtons) {
      return Container();
    }
    return Center(
      child: FloatingActionButton(
        onPressed: () {
          setState(() {
            _controller.value.isPlaying
                ? _controller.pause()
                : _controller.play();
          });
        },
        backgroundColor: Colors.black.withAlpha(120),
        child: Icon(
          _controller.value.isPlaying ? Icons.pause : Icons.play_arrow,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    if(_controller.value.initialized) {
      return Container(
        child: AspectRatio(
          aspectRatio: _controller.value.aspectRatio,
          child: MouseRegion(
            onEnter: onEnterVideo,
            onExit: onLeaveVideo,
            child: Stack(
              children: [
                Container(
                  child: VideoPlayer(_controller,)),
                buildVideoButtons(context),
              ],
            ),
          ),
        ),
      );
    }
    return Container();
  }

}
