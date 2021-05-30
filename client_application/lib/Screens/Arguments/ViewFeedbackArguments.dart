import 'package:client_application/Domain/DTO/DateTimeDTO.dart';
import 'package:client_application/Domain/Users/AppUser.dart';
import 'package:client_application/Domain/Video/FeedBackLink.dart';

class HistoryFeedBackArguments {

  AppUser user;
  FeedbackLink link;
  DateDayDTO dateTimeDTO;
  HistoryFeedBackArguments(this.user, this.link, this.dateTimeDTO);

}