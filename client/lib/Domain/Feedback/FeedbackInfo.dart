import 'package:client/Domain/Dates/DateDTO.dart';
import 'package:client/Domain/Dates/DateDayDTO.dart';
import 'package:client/Domain/Feedback/FeedBackLink.dart';

class FeedbackInfo {

  String swimmer;
  DateDTO date;
  String feedbackLink;
  String key;
  int numberOfErrors;
  int numberOfComments;

  FeedbackInfo(this.swimmer, this.date, this.feedbackLink, this.key, this.numberOfErrors,
      this.numberOfComments);

  FeedbackInfo.fromJson(Map<String, dynamic> map)
    : swimmer = map['swimmersEmail'],
      date = DateDTO.fromJson(map['dateDTO']),
      feedbackLink = fixLink(map['link']),
      key = map['key'],
      numberOfErrors = map['numberOfErrors'],
      numberOfComments = map['numberOfComments'];

  static String fixLink(String link) {
    return "/"+link.replaceAll("\\", "/");
  }

}