import 'dart:io';

import 'package:client/Services/VideoRepresantations/VideoWithoutFeedback.dart';

import '../FeedBackVideoStreamer.dart';

class VideoScreenArguments {

  List<FeedbackVideoStreamer> videos;
  List<VideoWithoutFeedback> futureVideos;

  VideoScreenArguments(this.videos, this.futureVideos){
    if(this.videos == null){
      this.videos = List();    }
    if(this.futureVideos == null) {
      this.futureVideos = List();
    }
  }

}