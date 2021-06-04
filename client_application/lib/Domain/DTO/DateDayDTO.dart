class DateDTO {

  int year;
  int month;
  int day;
  int hour;
  int minute;
  int second;

  DateDTO(this.year, this.month, this.day, this.hour, this.minute, this.second);

  DateDTO.fromJson(Map<String, dynamic> map)
      : year = map['year'],
        month = map['month'],
        day = map['day'],
        hour = map['hour'],
        minute = map['minute'],
        second = map['second'];

  @override
  String toString() {
    return '$day.$month.$year $hour:$minute:$second';
  }

  int compareTo(DateDTO other) {
    int yearCompare = year.compareTo(other.year);
    if(yearCompare == 0) {
      int monthCompare = month.compareTo(other.month);
      if(monthCompare == 0) {
        int dayCompare = day.compareTo(other.day);
        if(dayCompare == 0) {
          int hourCompare = hour.compareTo(other.hour);
          if(hourCompare == 0) {
            int minuteCompare = minute.compareTo(other.minute);
            if(minuteCompare == 0) {
              return second.compareTo(other.second);
            }
            return minuteCompare;
          }
          return hourCompare;
        }
        return dayCompare;
      }
      return monthCompare;
    }
    return yearCompare;
  }

}