import 'package:client/Domain/Dates/DateDTO.dart';

class SwimmerHistoryFeedback {

  String path;
  DateDTO date;

  SwimmerHistoryFeedback(this.path, this.date);

  SwimmerHistoryFeedback.fromJson(Map<String, dynamic> map)
      : path = map['path'],
        date = DateDTO.fromJson(map['date']);

  String getPath() {
    return "/"+this.path.replaceAll("\\", "/");
  }

}