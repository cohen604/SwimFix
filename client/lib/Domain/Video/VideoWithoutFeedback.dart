import 'package:client/Domain/Feedback/FeedBackVideoStreamer.dart';
import 'package:client/Services/LogicManager.dart';

abstract class VideoWithoutFeedback {

  Future<FeedbackVideoStreamer> getFeedbackVideo(LogicManager logicManager);


}