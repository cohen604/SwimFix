import 'dart:io';

import 'package:flutter_ffmpeg/flutter_ffmpeg.dart';

/// The class responsible for handling the camera files
class CameraHandler {

  List<File> cuttingVideos;
  FlutterFFmpeg fFmpeg;

  CameraHandler() {
    this.fFmpeg = new FlutterFFmpeg();
    this.cuttingVideos = new List();
  }

  List<File> cutVideoList(String videoPath) {
    int last = videoPath.lastIndexOf("/");
    // originalPath = /same_root/video.mp4 => cuttingPath = /same_root/videoTmp.mp4
    String folderPath = videoPath.substring(0,last);
    //todo loop from i to P:
    File file = cutVideo(videoPath, folderPath, "test1.mp4", 1, 2);
    cuttingVideos.add(file);
    return this.cuttingVideos;
  }


  /// The function save new cut of the swimming video from[start_time,end_time]
  /// videoPath - the name of the video Path
  /// folderPath - the name of the folder to save the new cutting video to
  /// name - the name of the cutting video
  /// startTime - the start time of the cut
  /// endTime - the end time of the cut
  /// return the File of the cutting video
  File cutVideo(String videoPath, String folderPath, String name, int startTime, int endTime) {
    int duration = endTime - startTime;
    String command = "";
    command += "-i $videoPath";
    command += " -ss ${timeToString(startTime)}";
    command += " -t ${timeToString(duration)}";
    command += " -c copy $folderPath/$name";
    var output = false;
    this.fFmpeg.executeAsync(command, (arg1, arg2)=>{});
    String path = "$folderPath/$name";
    File file = getFile(path);
    return file;
  }

  /// The function delete cutting video list
  void deleteCuttingVideos() {
    for(File video in this.cuttingVideos) {
      video.deleteSync();
    }
  }

  /// The function delete a cutting video with the given name
  bool deleteCuttingVideo(String path) {
    File file = getFile(path);
    if(file!=null) {
      file.deleteSync();
      return true;
    }
    return false;
  }

  /// The function get a int time in seconds
  /// time - the time seconds
  /// return a string in the format hh:mm:ss
  String timeToString(int time) {
    String output = "00";
    int mm = (time / 60).round();
    if (mm < 10) {
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

  /// The function return a video file if exists
  /// path - the path of the file
  /// return a file
  File getFile(String path) {
    File file = File(path);
    if(file.existsSync()) {
      return file;
    }
    return null;
  }

  List<File> getVideos() {
    return cuttingVideos;
  }



}