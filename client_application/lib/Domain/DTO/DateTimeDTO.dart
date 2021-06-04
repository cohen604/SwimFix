class DateDayDTO {

  int year;
  int month;
  int day;

  DateDayDTO(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  DateDayDTO.fromJson(Map<String, dynamic> json)
      : year = int.parse(json['year']),
        month = int.parse(json['month']),
        day = int.parse(json['day']);

  Map<String, dynamic> toJson() =>
    {
      'year': year,
      'month': month,
      'day': day,
    };

  static DateDayDTO factory(Map map) {
    return new DateDayDTO(map['year'], map['month'], map['day']);
  }

  @override
  String toString() {
    return '$day.$month.$year';
  }

  int compareTo(DateDayDTO other) {
    if(year < other.year) {
      return -1;
    }
    else if(year > other.year) {
      return 1;
    }
    else {
      if(month < other.month) {
        return -1;
      }
      else if(month > other.month) {
        return 1;
      }
      else {
        if(day < other.day) {
          return -1;
        }
        else if(day > other.day) {
          return 1;
        }
      }
    }
    return 0;
  }

}