import 'dart:io';
import 'dart:typed_data';

class FeedbackVideo {

  final String type;
  final Uint8List bytes;
  final List<String> comments;

  FeedbackVideo(this.type, this.bytes, this.comments);

  FeedbackVideo.fromJson(Map<String, dynamic> json)
      : type = json['type'],
        bytes = json['bytes'],
        comments = json['comments'];

  File getFile() {
    var file = File.fromRawPath(this.bytes);
    return file;
  }

  static FeedbackVideo factory(Map map) {
    Uint8List list = Uint8List.fromList(map['bytes'].cast<int>());
    return new FeedbackVideo(map['type'], list, map['comments'].cast<String>());
  }

}
