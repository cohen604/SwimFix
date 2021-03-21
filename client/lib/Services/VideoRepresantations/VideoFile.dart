import 'dart:io';
import 'dart:typed_data';
import 'package:client/Domain/FeedBackVideoStreamer.dart';

import 'package:client/Services/LogicManager.dart';

import 'VideoWithoutFeedback.dart';

class VideoFile extends VideoWithoutFeedback {

  File _file;

  VideoFile(File file) {
    _file = file;
  }

  @override
  Future<FeedbackVideoStreamer> getFeedbackVideo(LogicManager logicManager) {
    Uint8List bytes = _file.readAsBytesSync();
    int length = bytes.length;
    String filePath = _file.path;
    return logicManager.postVideoForStreaming(
        bytes,
        length,
        filePath,
        null //TODO change this null need to be a swimmer
    );
  }


}