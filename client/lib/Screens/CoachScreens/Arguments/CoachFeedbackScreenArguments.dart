import 'package:client/Domain/Feedback/FeedbackInfo.dart';
import 'package:client/Domain/Users/WebUser.dart';

class CoachFeedbackScreenArguments {

  WebUser user;
  FeedbackInfo feedbackInfo;

  CoachFeedbackScreenArguments(this.user, this.feedbackInfo);
}