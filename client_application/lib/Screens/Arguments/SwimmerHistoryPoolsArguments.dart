
import 'package:client_application/Domain/DTO/DateTimeDTO.dart';
import 'package:client_application/Domain/Users/AppUser.dart';

class SwimmerHistoryPoolsArguments {

  AppUser webUser;
  DateTimeDTO date;
  SwimmerHistoryPoolsArguments(this.webUser, this.date);

}