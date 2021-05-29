import 'package:client/Domain/Team/SwimmerTeam.dart';
import 'package:client/Domain/Users/WebUser.dart';

class CoachSwimmerScreenArguments {

  WebUser user;
  SwimmerTeam swimmerTeam;

  CoachSwimmerScreenArguments(this.user, this.swimmerTeam);
}