class Point {

  int x;
  int y;

  Point(this.x, this.y);

  Point.fromJson(Map<String, dynamic> map)
    : x = map['x'],
      y = map['y'];
}