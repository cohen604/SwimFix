import 'package:client/Domain/Dates/DateDTO.dart';
import 'package:client/Domain/Dates/DateDayDTO.dart';
import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Domain/Graph/FeedbackGraphs.dart';

import 'FeedbackComment.dart';

class FeedbackAnalysis {

  String swimmerEmail;
  String link;
  String key;
  DateDTO date;
  List<FeedbackComment> comments;
  FeedbackGraphs graphs;


  FeedbackAnalysis(this.swimmerEmail, this.link, this.key, this.date,
      this.comments, this.graphs);

  FeedbackAnalysis.fromJson(Map<String, dynamic> map)
      : swimmerEmail = map['swimmerEmail'],
        link = map['link'],
        key = map['key'],
        date = DateDTO.fromJson(map['date']),
        comments = (map['comments'] as List).map((e) => FeedbackComment.fromJson(e)).toList(),
        graphs = FeedbackGraphs.fromJson(map['graphs']);

}