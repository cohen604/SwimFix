import 'dart:core';
import 'package:client/Domain/Dates/DateDayDTO.dart';
import 'package:client/Domain/Invitations/Invitation.dart';

import 'SwimmerTeam.dart';

class Team {

  String name;
  DateDayDTO date;
  String coach;
  List<SwimmerTeam> swimmers;
  List<Invitation> invitations;

  Team(this.name, this.date, this.coach, this.swimmers, this.invitations);

  Team.fromJson(Map<String, dynamic> map)
    : name = map['name'],
      date = DateDayDTO.fromJson(map['dateDayDTO']),
      coach = map['coachId'],
      swimmers = (map['swimmers'] as List).map((e) => SwimmerTeam.fromJson(e)).toList(),
      invitations = (map['invitations'] as List).map((e) => Invitation.fromJson(e)).toList();
}