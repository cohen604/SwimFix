import 'package:client_application/Domain/Users/AppUser.dart';
import 'package:client_application/Domain/Video/FeedBackLink.dart';

class FeedbackScreenArguments {

  AppUser appUser;
  FeedbackLink link;

  FeedbackScreenArguments(this.appUser, this.link);
}