
import 'package:client_application/Domain/DTO/DateTimeDTO.dart';
import 'package:client_application/Domain/Users/AppUser.dart';

class HistoryDayScreenArguments {

  AppUser user;
  DateTimeDTO date;
  HistoryDayScreenArguments(this.user, this.date);

}