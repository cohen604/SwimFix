import 'package:client_application/Domain/DTO/DateDayDTO.dart';

class FeedbackComment {

  DateDTO dateDTO;
  String coachId;
  String comment;

  FeedbackComment(this.dateDTO, this.coachId, this.comment);

  FeedbackComment.fromJson(Map<String, dynamic> map)
    : dateDTO = DateDTO.fromJson(map['dateDTO']),
      coachId = map['coachId'],
      comment = map['comment'];

}