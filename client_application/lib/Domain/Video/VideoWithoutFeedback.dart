import 'package:client_application/Services/LogicManager.dart';
import 'FeedBackVideoStreamer.dart';

abstract class VideoWithoutFeedback {

  Future<FeedbackVideoStreamer> getFeedbackVideo(LogicManager logicManager);


}