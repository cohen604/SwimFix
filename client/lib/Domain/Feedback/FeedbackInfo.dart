import 'package:client/Domain/Dates/DateTimeDTO.dart';
import 'package:client/Domain/Feedback/FeedBackLink.dart';

class FeedbackInfo {

  String swimmer;
  DateTimeDTO date;
  String feedbackLink;
  int numberOfErrors;
  int numberOfComments;

  FeedbackInfo(this.swimmer, this.date, this.feedbackLink, this.numberOfErrors,
      this.numberOfComments);

  FeedbackInfo.fromJson(Map<String, dynamic> map)
    : swimmer = map['swimmersEmail'],
      date = DateTimeDTO.fromJson(map['dateDTO']),
      feedbackLink = fixLink(map['link']),
      numberOfErrors = map['numberOfErrors'],
      numberOfComments = map['numberOfComments'];

  static String fixLink(String link) {
    return "/"+link.replaceAll("\\", "/");
  }

}