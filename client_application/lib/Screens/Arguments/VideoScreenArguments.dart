import 'package:client_application/Domain/Video/FeedBackVideoStreamer.dart';
import 'package:client_application/Domain/Video/VideoWithoutFeedback.dart';

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