import 'package:client/Domain/Graph/Point.dart';

class FeedbackGraphs {

  int numberOfFrames;
  Map<int, Point> head;
  Map<int, Point> rightShoulder;
  Map<int, Point> rightElbow;
  Map<int, Point> rightWrist;
  Map<int, Point> leftShoulder;
  Map<int, Point> leftElbow;
  Map<int, Point> leftWrist;
  Map<int, int> errors;

  FeedbackGraphs(
      this.numberOfFrames,
      this.head,
      this.rightShoulder,
      this.rightElbow,
      this.rightWrist,
      this.leftShoulder,
      this.leftElbow,
      this.leftWrist,
      this.errors);

  FeedbackGraphs.fromJson(Map<String, dynamic> map)
    : numberOfFrames = map['numberOfFrames'],
      head = toPointMap(map['head']),
      rightShoulder = toPointMap(map['rightShoulder']),
      rightElbow = toPointMap(map['rightElbow']),
      rightWrist = toPointMap(map['rightWrist']),
      leftShoulder = toPointMap(map['leftShoulder']),
      leftElbow = toPointMap(map['leftElbow']),
      leftWrist = toPointMap(map['leftWrist']),
      errors = toErrorMap(map['errors']);

  static Map<int, Point> toPointMap(Map<String, dynamic> map) {
    Map<int, Point> output = new Map();
    map.forEach((key, value) {
      output.putIfAbsent(int.parse(key), () => Point.fromJson(value));
    });
    return output;
  }

  static Map<int, int> toErrorMap(Map<String, dynamic> map) {
    Map<int, int> output = new Map();
    map.forEach((key, value) {
      output.putIfAbsent(int.parse(key), () => value);
    });
    return output;
  }
}