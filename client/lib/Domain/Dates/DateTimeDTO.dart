class DateTimeDTO {

  int year;
  int month;
  int day;

  DateTimeDTO(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  DateTimeDTO.fromJson(Map<String, dynamic> map)
      : year = map['year'],
        month = map['month'],
        day = map['day'];

  Map<String, dynamic> toJson() =>
    {
      'year': year,
      'month': month,
      'day': day,
    };

  static DateTimeDTO factory(Map map) {
    return new DateTimeDTO(map['year'], map['month'], map['day']);
  }

  @override
  String toString() {
    return '$day.$month.$year';
  }

}