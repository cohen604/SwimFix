import 'package:client/Domain/Dates/DateDayDTO.dart';
import 'package:client/Domain/Users/WebUser.dart';

class SwimmerHistoryPoolsArguments {

  WebUser webUser;
  DateDayDTO date;
  SwimmerHistoryPoolsArguments(this.webUser, this.date);

}