import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Services/LogicManager.dart';

abstract class VideoWithoutFeedback {

  Future<FeedbackVideoStreamer> getFeedbackVideo(LogicManager logicManager);


}