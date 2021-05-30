import 'package:client/Domain/Dates/DateDayDTO.dart';

class Invitation {

  String id;
  String teamId;
  String email;
  DateDayDTO date;
  bool isPending;
  bool isApprove;
  bool isDenied;


  Invitation(this.id, this.teamId, this.email, this.date, this.isPending,
      this.isApprove, this.isDenied);

  Invitation.fromJson(Map<String, dynamic> map)
    : id = map['id'],
      teamId = map['teamId'],
      email = map['email'],
      date = DateDayDTO.fromJson(map['dateDayDTO']),
      isPending = map['isPending'],
      isApprove = map['isApprove'],
      isDenied = map['isDenied'];


  int compareTo(Invitation other) {
    if(isApprove && !other.isApprove) {
      return -1;
    }
    else if(!isApprove && other.isApprove) {
      return 1;
    }
    else {
      if(isPending && !other.isPending) {
        return -1;
      }
      else if(!isPending && other.isPending) {
        return 1;
      }
      else {
        if(isDenied && !other.isDenied) {
          return -1;
        }
        else if(!isDenied && other.isDenied) {
          return 1;
        }
      }
    }
    return 0;
  }

}