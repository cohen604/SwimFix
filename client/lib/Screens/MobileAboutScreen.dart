import 'package:chewie/chewie.dart';
import 'package:client/Components/MenuBars/MobileAboutScreenMenuBar.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:video_player/video_player.dart';
import 'Arguments/AboutScreenArguments.dart';
import 'Holders/WebColors.dart';

class MobileAboutScreen extends StatefulWidget {

  AboutScreenArguments args;

  MobileAboutScreen(this.args);

  @override
  _MobileAboutScreenState createState() => _MobileAboutScreenState(
      args.videoOn, args.loginOn, args.aboutOn);

}

class _MobileAboutScreenState extends State<MobileAboutScreen> {

  WebColors _webColors;
  ScreenState state;
  VideoPlayerController _controller;
  ChewieController _chewieController;
  AssetsHolder _assetsHolder;

  _MobileAboutScreenState(bool videoOn, bool loginOn, bool aboutOn) {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    if(videoOn && !loginOn) {
      state = ScreenState.Video;
    }
    else {
      state = ScreenState.Login;
    }
  }

  @override
  void initState() {
    super.initState();
    _controller = VideoPlayerController.asset(_assetsHolder.getIntroVideo());
    _controller.initialize().then((_) {
      _chewieController = ChewieController(
        videoPlayerController: _controller,
        aspectRatio: _controller.value.aspectRatio,
        autoPlay: false,
        looping: false,
        //note: this muse be false cause chewiew having problem in full screen
        allowFullScreen: false,
        allowedScreenSleep: false,
        fullScreenByDefault: false,
        allowMuting: false,
        playbackSpeeds: [0.1, 0.25, 0.5, 1, 1.5],
      );
      this.setState(() { });
    });
  }

  @override
  void dispose() {
    _chewieController.dispose();
    _controller.dispose();
    super.dispose();
  }

  void onSignUp() {
    this.setState(() {
      state = ScreenState.Login;
    });
  }

  void onLogo() {
    this.setState(() {
      state = ScreenState.Video;
    });
  }

  void onLogin() {
    this.setState(() {
      state = ScreenState.Login;
    });
  }

  Function onDownload(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/downloads',);
        });
      });
    };
  }

  Widget buildText(BuildContext context,
      String text,
      double fontSize,
      { Color color = Colors.white,
        FontWeight fontWeight = FontWeight.normal}) {
    return Text(text,
        textAlign: TextAlign.left,
        style: TextStyle(
            fontSize: fontSize * MediaQuery.of(context).textScaleFactor,
            color: color,
            fontWeight: fontWeight,
            decoration: TextDecoration.none
        ));
  }

  Widget buildDesArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      padding: EdgeInsets.only(left: 30),
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getSwimmerBackGround()),
          fit: BoxFit.cover,
          colorFilter: ColorFilter.mode(Colors.black.withAlpha(120), BlendMode.darken),
        ),
      ),
      child: Column(
        children: [
          Flexible(
              fit: FlexFit.tight,
              child: Container(
                  alignment: Alignment.bottomLeft,
                  child: buildText(context, "Swim Analytics", 54,
                      fontWeight: FontWeight.bold))
          ),
          Container(
              alignment: Alignment.centerLeft,
              child: buildText(
                context, "Swimming training solutions", 24,
              )
          ),
          Expanded(
            child: Container(
              alignment: Alignment.topLeft,
              child: buildText(context,"The best platform to improve your swimming abilities.\n"
                  "The platform allows any swimmer to record, view, train, receive swimming videos,"
                  "feedback's and many more.\n", 21,),
            ),
          ),
        ],
      ),
    );
  }

  Widget buildVideo(BuildContext context) {
    if(_controller == null || !_controller.value.initialized
        || _chewieController == null) {
      return Center(
          child: CircularProgressIndicator()
      );
    }
    return Padding(
      padding: const EdgeInsets.all(5.0),
      child: Material(
        color: Colors.black.withAlpha(120),
        child: Padding(
          padding: const EdgeInsets.all(3.0),
          child: Chewie(
            controller: _chewieController
          ),
        ),
      ),
    );
  }

  Widget buildVideoArea(BuildContext context) {
    return Container(
      // width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height / 2,
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getSwimmerBackGround()),
          fit: BoxFit.cover,
          colorFilter: ColorFilter.mode(Colors.black.withAlpha(120), BlendMode.darken),
        ),
      ),
      child: buildVideo(context),
    );
  }

  Widget buildSupportArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height / 6,
      color: _webColors.getBackgroundForI3(),
      child: Row(
        children: [
          Flexible(
            fit: FlexFit.tight,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(Icons.web_outlined),
                buildText(context, "Web", 24,
                  color: Colors.black,
                  fontWeight: FontWeight.bold),
              ],
            )
          ),
          Flexible(
            fit: FlexFit.tight,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(Icons.mobile_friendly),
                buildText(context, "Mobile", 24,
                  color: Colors.black,
                  fontWeight: FontWeight.bold),
              ],
            )
          ),
        ],
      ),
    );
  }

  Widget buildAreas(BuildContext context) {
    return Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        color: _webColors.getBackgroundForI6(),
        child: Scrollbar(
          child: SingleChildScrollView(
            child: Column(
              children: [
                buildDesArea(context),
                buildVideoArea(context),
                buildSupportArea(context),
              ],
            ),
          ),
        )
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
            MobileAboutScreenMenuBar(
              onLogo: onLogo,
              onDownload: onDownload(context),
            ),
            Expanded(
                child: buildAreas(context)
            ),
          ],
        ),
      ),
    );
  }

}

enum ScreenState {
  Video,
  Login,
  About,
}