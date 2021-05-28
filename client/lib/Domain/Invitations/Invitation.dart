import 'package:client/Domain/Dates/DateTimeDTO.dart';

class Invitation {

  String id;
  String teamId;
  DateTimeDTO date;

  Invitation(this.id, this.teamId, this.date);

  Invitation.fromJson(Map<String, dynamic> map)
    : id = map['id'],
      teamId = map['teamId'],
      date = DateTimeDTO.fromJson(map['dateDTO']);

}