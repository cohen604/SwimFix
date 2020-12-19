import 'dart:io';

import '../FeedBackVideoStreamer.dart';

class VideoScreenArguments {

  List<FeedbackVideoStreamer> videos;
  List<File> futureVideos;

  VideoScreenArguments(this.videos, this.futureVideos){
    if(this.videos == null){
      this.videos = List();
    }
    if(this.futureVideos == null) {
      this.futureVideos = List();
    }
  }

}