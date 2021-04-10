import 'package:chewie/chewie.dart';
import 'package:client_application/Domain/Video/FeedBackVideoStreamer.dart';
import 'package:client_application/Domain/Video/FeedbackFilters.dart';
import 'package:client_application/Services/ConnectionHandler.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';
import 'package:flutter/foundation.dart' show kIsWeb;

//TODO change this
List<String> maxErrors = List();

class VideoPreviewScreen extends StatefulWidget {

  FeedbackVideoStreamer feedbackVideoStreamer;
  VideoPreviewScreen({this.feedbackVideoStreamer, Key key}):
        super(key: key);

  @override
  _VideoPreviewScreenState createState()=> new _VideoPreviewScreenState();

}

class _VideoPreviewScreenState extends State<VideoPreviewScreen> {

  VideoPlayerController _controller;
  Future<void> _futureController;
  ChewieController _chewieController;
  //TODO need to get the list of filters from the feedbackVideoStreamer
  List<String> errors; // = ["Elbow", "Forearm", "Palm"];
  Map<String, bool> filters; // = {"Elbow": true, "Forearm": true, "Palm": true};

  @override
  void initState() {
    super.initState();
    setController();
    List<String> errorsNames = this.widget.feedbackVideoStreamer.detectors;
    this.errors = List();
    this.filters = Map();
    for(String name in errorsNames) {
      if(!maxErrors.contains(name)) {
        maxErrors.add(name);
      }
      this.errors.add(name);
      this.filters.putIfAbsent(name, () => true);
    }
    print(maxErrors);
  }

  void setController() async {
    if(this.widget.feedbackVideoStreamer != null) {
      ConnectionHandler connectionHandelr = new ConnectionHandler();
      String url = connectionHandelr.getStreamUrl() + this.widget.feedbackVideoStreamer.getPath();
      print(url);
      _controller = VideoPlayerController.network(url);
      await _controller.initialize();
      _controller.play();

      _chewieController = ChewieController(
        videoPlayerController: _controller,
        // aspectRatio: 16 / 9,
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

  Widget buildChewieMobile(BuildContext context) {
    if(this.widget.feedbackVideoStreamer == null) {
      return Center(
        child: Text("Video didnt uploaded"),
      );
    }
    if(_chewieController != null && _chewieController.videoPlayerController.value.initialized) {
      return AspectRatio(
        aspectRatio: 16 / 9,
        child: Chewie(
          controller: _chewieController,
        ),
      );
    }
    return Center(
      child:CircularProgressIndicator(),
    );
  }

  void clickView() {
    if(this.widget.feedbackVideoStreamer != null) {
      String path = this.widget.feedbackVideoStreamer.path;
      List<String> output = new List();
      for (String error in this.errors) {
        if (this.filters[error]) {
          output.add(error);
        }
      }
      FeedbackFilters filter = new FeedbackFilters(path, output);
      LogicManager.getInstance().filterFeedback(
          filter
          , null //TODO change this null need to be swimmer
      ).then((link) {
        Navigator.pushNamed(context, "/videoPreview",
            arguments: link);
      });
    }
  }

  void clickReset() {
    String path = this.widget.feedbackVideoStreamer.path;
    FeedbackFilters filter = new FeedbackFilters(path, maxErrors);
    LogicManager.getInstance().filterFeedback(
        filter,
        null //TODO change this null need to be swimmer
    ).then((link) {
      Navigator.pushNamed(context, "/videoPreview",
          arguments: link);
    });
  }

  Widget buildErrorList(BuildContext context) {
    return Column(
      children: [ListView.builder(
        itemCount: this.errors.length,
        shrinkWrap: true,
        itemBuilder: (_, index) {
          return CheckboxListTile(
            title: Text("${this.errors[index]} Detector"),
            controlAffinity: ListTileControlAffinity.leading,
            value: this.filters[this.errors[index]],
            onChanged: (value) {
              this.setState(() {
                this.filters[this.errors[index]] = !this.filters[this.errors[index]];
              });
            },
          );
        }
      ),
      RaisedButton(
          onPressed: this.clickView,
          child:Text("View")
      ),
      SizedBox(
        height: 20,
      ),
      RaisedButton(
          onPressed: this.clickReset,
          child:Text("Reset")
      ),
    ]);
  }

  Widget buildChewie(BuildContext context) {
    if(kIsWeb) {
      return Row(
        children: [
          Expanded(
            flex: 1,
            child: buildErrorList(context)),
          Expanded(
            flex:  4,
            child: LimitedBox(
              maxHeight: MediaQuery.of(context).size.height - 70,
              child: buildChewieMobile(context)
            ),
          ),
        ],
      );
    }
    return buildChewieMobile(context);
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
      appBar: AppBar(
      title: Text("Video Preview"),
        ),
      body: SingleChildScrollView(
      child: buildChewie(context),
        ),
      ),
    );
    return buildChewie(context);
  }
  
}