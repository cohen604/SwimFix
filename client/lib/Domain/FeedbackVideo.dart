import 'dart:typed_data';

class FeedbackVideo {

  final String type;
  final List<int> bytes;
  final List<String> comments;

  FeedbackVideo(this.type, this.bytes, this.comments);

  FeedbackVideo.fromJson(Map<String, dynamic> json)
      : type = json['type'],
        bytes = json['bytes'],
        comments = json['comments'];

  static FeedbackVideo factory(Map map) {
    return new FeedbackVideo(map['type'], map['bytes'].cast<int>(), map['comments'].cast<String>());
  }

  //TODO if needed create toJson
}
