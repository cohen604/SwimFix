import 'dart:io';

import 'package:flutter_ffmpeg/flutter_ffmpeg.dart';

class CameraHandler {

  String originalPath;
  String cuttingPath;
  FlutterFFmpeg fFmpeg;

  CameraHandler(String originalPath) {
    this.originalPath = originalPath;
    int last = originalPath.lastIndexOf("/");
    this.cuttingPath = originalPath; // originalPath = /../video.mp4 => /../videoTmp.mp4
    this.fFmpeg = new FlutterFFmpeg();
  }

  /// The function get a int time in seconds
  /// time - the time seconds
  /// return a string in the format hh:mm:ss
  String timeToString(int time) {
    String output ="00";
    int mm = (time / 60).round();
    if(mm < 10) {
      output += ":0$mm";
    }
    else {
      output += ":$mm";
    }
    time = time % 60;
    int ss = time;
    if (ss < 10) {
      output += ":0$ss";
    }
    else {
      output += ":$ss";
    }
    return output;
  }


  /// The fucntion
  ///
  Future<bool> cutVideo(int startTime, int endTime) async{
    int duration = endTime - startTime;
    String command = "";
    command += "-i ${this.originalPath}";
    command += " -ss ${timeToString(startTime)}";
    command += " -t ${timeToString(duration)}";
    command += " -c copy ${this.cuttingPath}";
    var output = false;
    await this.fFmpeg.execute(command).then((value) {
      if(value >= 0) {
        output = true;
      }
    });
    return output;
  }

  File getVideo() {
    File file = File(this.cuttingPath);
  }

}