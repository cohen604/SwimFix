import 'dart:io';
import 'package:client_application/Domain/Pair.dart';
import 'package:client_application/Services/FileHandler.dart';
import 'package:flutter_ffmpeg/flutter_ffmpeg.dart';
import 'package:image/image.dart';
import 'package:media_info/media_info.dart';

class VideoHandler {

  FlutterFFmpeg ffmpeg;
  FileHandler fileHandler;

  VideoHandler() {
    fileHandler = new FileHandler();
    ffmpeg = new FlutterFFmpeg();
  }

  Future<List<Pair<int, int>>> splitVideoToTimes(String path,
      {int seconds=0, int milliseconds=0}) async {
    int duration = await getTotalTime(path);
    //TODO change here to the desired pool algorithm
    // int delta = 1;
    // List<Pair<int,int>> output = [];
    // for(int i=0; i<duration; i+=delta) {
    //   if(i + delta <= duration) {
    //     output.add(new Pair<int, int>(i, i + delta));
    //   }
    // }
    // return output;
    return [new Pair(0, duration)];
  }

  /// The function return the duration time of the video
  /// return duration of the video in seconds
  Future<int> getTotalTime(String videoPath) async {
    int durationInSeconds = 0;
    MediaInfo mediaInfo = MediaInfo();
    await mediaInfo.getMediaInfo(videoPath).then((value) {
      Map<String, dynamic> info = value;
      durationInSeconds = (info["durationMs"] / 1000).floor();
    });
    return durationInSeconds;
  }

  Image getImageFromVideo(String path, int second, int milliseconds) {

    return null;
  }

  Future<File> splitVideo(
      String videoPath,
      String newFileName,
      int startTime,
      int endTime) async{
    String folderPath = fileHandler.getFolderPath();
    String path = "$folderPath/$newFileName";
    int duration = endTime - startTime;
    String command = "";
    command += "-i $videoPath";
    command += " -y";
    command += " -ss ${timeFormatFFmpeg(startTime)}";
    command += " -t ${timeFormatFFmpeg(duration)}";
    command += " -c copy $folderPath/$newFileName";
    await this.ffmpeg.execute(command);
    return fileHandler.getFile(path);
  }



  /// The function return the seco
  /// return a string in the format hh:mm:ss
  String timeFormatFFmpeg(int seconds) {
    String output = "00";
    int mm = (seconds / 60).round();
    if (mm < 10) {
      output += ":0$mm";
    }
    else {
      output += ":$mm";
    }
    seconds = seconds % 60;
    int ss = seconds;
    if (ss < 10) {
      output += ":0$ss";
    }
    else {
      output += ":$ss";
    }
    return output;
  }

  void deleteVideo(String newPath) {
    String folderPath = fileHandler.getFolderPath();
    String path = "$folderPath/$newPath";
    fileHandler.deleteFile(path);
  }

}
