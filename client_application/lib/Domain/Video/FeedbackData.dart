import 'package:client_application/Domain/DTO/DateDTO.dart';

import 'FeedbackComment.dart';

class FeedbackData {

  String swimmerEmail;
  String link;
  String key;
  DateDTO date;
  List<FeedbackComment> comments;

  FeedbackData(
      this.swimmerEmail, this.link, this.key, this.date, this.comments);

  FeedbackData.fromJson(Map<String, dynamic> map)
    : swimmerEmail = map['swimmerEmail'],
      link = map['link'],
      key = map['key'],
      date = DateDTO.fromJson(map['date']),
      comments = (map['comments'] as List).map((e) => FeedbackComment.fromJson(e)).toList();

}