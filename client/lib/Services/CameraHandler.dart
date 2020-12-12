import 'dart:io';
import 'package:video_player/video_player.dart';
import 'package:flutter_ffmpeg/flutter_ffmpeg.dart';

/// The class responsible for handling the camera files
class CameraHandler {

  List<File> cuttingVideos;
  FlutterFFmpeg fFmpeg;
  Directory directory;

  CameraHandler() {
    this.cuttingVideos = new List();
    this.fFmpeg = new FlutterFFmpeg();
  }

  Future<List<File>> cutVideoList(String videoPath) async {
    int last = videoPath.lastIndexOf("/");
    // originalPath = /same_root/video.mp4 => cuttingPath = /same_root/videoTmp.mp4
    String folderPath = videoPath.substring(0,last);
    int total = getTotalTime(videoPath);
    print("total video time $total");
    this.directory = createTmpDirectory(folderPath);
    //todo loop from i to P:
    int jumps = 2;
    for(int i =0; i<total; i+=jumps) {
      File cutFile = await cutVideo(videoPath, this.directory.path, "cut$i.mp4", i, i+jumps);
      cuttingVideos.add(cutFile);
    }
    return this.cuttingVideos;
  }

  /// The function create a tmp directory
  /// folder - the folder of the given path
  /// return the new tmp directory
  /// Note: if the directory exits deletes the old one
  Directory createTmpDirectory(String folder) {
    String path = "$folder/videoCuts";
    Directory dir = Directory(path);
    this.deleteTmpDirectory(path);
    dir.create(recursive: true);
    if(dir.existsSync()) {
      print('created dir');
    }
    return dir;
  }

  /// The function delete a tmp directory
  /// return ture if the directory deleted
  bool deleteTmpDirectory(String path) {
    Directory dir = Directory(path);
    if(dir.existsSync()) {
      dir.deleteSync(recursive: true);
      if(dir.existsSync()) {
        print('delete the old dir');
        return true;
      }
    }
    return false;
  }

  /// The function return the duration time of the video
  /// return duration of the video in seconds
  int getTotalTime(String videoPath) {
    File file = File(videoPath);
    VideoPlayerController controller = new VideoPlayerController.file(file);
    return controller.value.duration.inSeconds;
  }

  /// The function save new cut of the swimming video from[start_time,end_time]
  /// videoPath - the name of the video Path
  /// folderPath - the name of the folder to save the new cutting video to
  /// name - the name of the cutting video
  /// startTime - the start time of the cut
  /// endTime - the end time of the cut
  /// return the File of the cutting video
  Future<File> cutVideo(String videoPath, String folderPath, String name, int startTime, int endTime) async{
    String path = "$folderPath/$name";
    //deleteCuttingVideo(path);
    int duration = endTime - startTime;
    String command = "";
    command += "-i $videoPath";
    command += " -y";
    command += " -ss ${timeToString(startTime)}";
    command += " -t ${timeToString(duration)}";
    command += " -c copy $folderPath/$name";
    await this.fFmpeg.execute(command);
    // String path = "$folderPath/$name";
    return getFile(path);
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
      // print('file is exists: ${file.existsSync()}');
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
    if(file!=null && file.existsSync()) {
      return file;
    }
    return null;
  }

  List<File> getVideos() {
    return cuttingVideos;
  }



}