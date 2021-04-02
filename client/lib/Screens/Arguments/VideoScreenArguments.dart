import 'package:client/Domain/Feedback/FeedBackVideoStreamer.dart';
import 'package:client/Domain/Video/VideoWithoutFeedback.dart';

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