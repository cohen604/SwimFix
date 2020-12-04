import 'dart:io';
import 'dart:typed_data';
import 'package:file/memory.dart';

class FeedbackVideo {

  final String path;
  final String type;
  final Uint8List bytes;
  final List<String> comments;

  FeedbackVideo(this.path, this.type, this.bytes, this.comments);

  FeedbackVideo.fromJson(Map<String, dynamic> json)
      : path = json['path'],
        type = json['type'],
        bytes = json['bytes'],
        comments = json['comments'];

  File getFile() {
    // need to be in web however js not support local files
    // File file = MemoryFileSystem().file('feedbackvideo'+type);
    // file.writeAsBytesSync(bytes);
    // not supported on web
    File file = new File('feedbackvideo'+type);
    file.writeAsBytesSync(bytes);
    print('file exists: ' +file.existsSync().toString());
    return file;
  }

  Uint8List getBytes() {
    return this.bytes;
  }

  static FeedbackVideo factory(Map map) {
    Uint8List list = Uint8List.fromList(map['bytes'].cast<int>());
    return new FeedbackVideo(map['path'], map['type'], list, map['comments'].cast<String>());
  }

}
