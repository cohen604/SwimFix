import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Domain/Users/WebUser.dart';

class ViewFeedBackArguments {

  WebUser user;
  FeedBackLink link;
  ViewFeedBackArguments(this.user, this.link);

}