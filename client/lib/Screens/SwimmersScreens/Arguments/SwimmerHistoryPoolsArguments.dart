import 'package:client/Domain/Dates/DateTimeDTO.dart';
import 'package:client/Domain/Users/WebUser.dart';

class SwimmerHistoryPoolsArguments {

  WebUser webUser;
  DateTimeDTO date;
  SwimmerHistoryPoolsArguments(this.webUser, this.date);

}