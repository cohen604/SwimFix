import 'dart:io';

import '../FeedBackVideoStreamer.dart';

class VideoScreenArguments {

  List<FeedbackVideoStreamer> videos;
  List<File> futureVideos;

  VideoScreenArguments(this.videos, this.futureVideos);

}