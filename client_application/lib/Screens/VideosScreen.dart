import 'dart:io';
import 'dart:typed_data';
import 'package:client_application/Components/VideoStreamerCard.dart';
import 'package:client_application/Domain/Video/FeedBackVideoStreamer.dart';
import 'package:client_application/Domain/Video/VideoWithoutFeedback.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class VideosScreen extends StatefulWidget {

  List<FeedbackVideoStreamer> videos;
  List<VideoWithoutFeedback> futureVideos;
  VideosScreen({this.videos, this.futureVideos, Key key}) : super(key: key);

  @override
  _VideosScreenState createState() => _VideosScreenState();

}

class _VideosScreenState extends State<VideosScreen> {

  LogicManager _logicManager;

  List<FeedbackVideoStreamer> have;
  // each key is mapped to his future location in the have list
  Map<int, Future<FeedbackVideoStreamer>> futureHave;
  // each key is mapped to his key in the have list
  Map<int, VideoWithoutFeedback> need;
  // each element in the list is the location in the complete have list
  List<int> requsts;


  @override
  void initState() {
    _logicManager = LogicManager.getInstance();
    print("Start Init Video Screen");
    super.initState();
    // create the have map
    this.have = this.widget.videos;// logicManger.getListFeedbackStreamer();
    // create the future have map
    this.futureHave = Map();
    // create the need map
    this.need = Map();
    int index = this.have.length;
    // for(File file in logicManger.getListNeed()) {
    for(VideoWithoutFeedback videoWithNoFeedback in this.widget.futureVideos) {
      this.need[index] = videoWithNoFeedback;
      index++;
    }
    // create the request map
    this.requsts = List();
    int size = 0;
    if(this.have != null) {
      size += this.have.length;
    }
    if(this.need != null) {
      size += this.need.length;
    }
    // resize the list to have the extra place for the size
    for(int i= this.have.length; i<size; i++) {
      this.have.add(null);
    }
    // first update so we can run
    autoUpdateHaveList();
  }

  Future<FeedbackVideoStreamer> getFeedbackStreamer(File file) {
    LogicManager lm = LogicManager.getInstance();
    Uint8List fileBytes = file.readAsBytesSync();
    int length = file.lengthSync();
    String filePath = file.path;
    Future<FeedbackVideoStreamer> feedbackVideoStreamer = lm.postVideoForStreaming(
        fileBytes, length, filePath, null //TODO change this null need to be swimmer
    );
    return feedbackVideoStreamer;
  }

  void autoUpdateHaveList() {
    int selectedIndex = -1;
    // user selecting first
    if(requsts.isNotEmpty) {
      selectedIndex = requsts.first;
      requsts.removeAt(0);
    }
    // user didn't select take the first key from need
    if(selectedIndex == -1 && this.need.isNotEmpty) {
      selectedIndex = this.need.keys.first;
    }
    // get the next File for streamer
    if(selectedIndex != -1 && this.need[selectedIndex] != null) {
      VideoWithoutFeedback nextVideo = this.need[selectedIndex];
      Future<FeedbackVideoStreamer> futureLink = nextVideo.getFeedbackVideo(_logicManager);
      this.futureHave[selectedIndex] = futureLink;
      //this.have.insert(selectedIndex, link);
      this.need.remove(selectedIndex);
    }
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Scaffold(
        appBar: AppBar(
          title: Text("Feedback's"),
        ),
        body: ListView.builder(
            itemCount: this.have.length,
            itemBuilder: (context, index) {
              FeedbackVideoStreamer link = this.have[index];
              if(link != null) {
                return VideoStreamerCard(link: link, number: index,);
                return Text(link.getPath());
              }
              else {
                if(this.futureHave[index]!=null) {
                  return FutureBuilder<FeedbackVideoStreamer>(
                  future: this.futureHave[index],
                  builder: (context, snapshot) {
                    if (snapshot.hasData) {
                        SchedulerBinding.instance.addPostFrameCallback((_) {
                          this.autoUpdateHaveList();
                          this.setState(() {
                            this.have.removeAt(index);
                            this.have.insert(index, snapshot.data);
                            this.futureHave.remove(index);
                          });
                        });
                    }
                    return VideoStreamerCard(link: null, number: index,);
                    return Center(
                        child:CircularProgressIndicator()
                    );
                  });
                }
                return VideoStreamerCard(link: null, number: index,);
                // return Text("No Link Yet");
              }
            },
          ),
        ),
      );
  }

}