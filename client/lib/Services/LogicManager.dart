import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Services/CameraHandler.dart';
import 'package:http/http.dart' as http;
import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/ServerResponse.dart';
import 'package:client/Services/connectionHandler.dart';

class LogicManager {

  static LogicManager logicManager;
  ConnectionHandler connectionHandler;
  CameraHandler cameraHandler;
  List<FeedbackVideoStreamer> listFeedbackVideoStreamer;
  List<File> listFileNeedFeedback;
  //TODO delete this
  FeedbackVideo feedbackVideo;

  LogicManager() {
    this.connectionHandler = new ConnectionHandler();
    this.cameraHandler = new CameraHandler();
    this.listFeedbackVideoStreamer = List();
    this.listFileNeedFeedback = List();
  }

  static LogicManager getInstance() {
    if(logicManager == null) {
      logicManager = new LogicManager();
    }
    return logicManager;
  }

  /// The function send a post request for receiving a feedback link
  /// fileBytes -
  /// length -
  /// filePath -
  /// return
  Future<FeedbackVideoStreamer> postVideoForStreaming(Uint8List fileBytes, int length,
      String filePath) async {
    String path = "/uploadForStream";
    http.MultipartFile multipartFile = http.MultipartFile.fromBytes(
        'file',
        fileBytes,
        filename: filePath,
    );
    Future<String> result = this.connectionHandler.postMultiPartFile(path, multipartFile);
    FeedbackVideoStreamer output;
    await result.then((jsonString) {
      Map responseMap = json.decode(jsonString);
      ServerResponse response = ServerResponse.fromJson(responseMap);
      Map map = response.value as Map;
      output = FeedbackVideoStreamer.factory(map);
    });
    return output;
  }

  /// The function add a feedback video streamer
  /// feedbackVideoStreamer -
  void addFeedbackVideoStreamer(FeedbackVideoStreamer feedbackVideoStreamer) {
    this.listFeedbackVideoStreamer.add(feedbackVideoStreamer);
  }

  /// The function return the first feedback streamer
  FeedbackVideoStreamer getFirstFeedbackStreamer() {
    if(this.listFeedbackVideoStreamer.isEmpty) {
      return null;
    }
    return this.listFeedbackVideoStreamer.first;
  }

  /// The function return all the feedback streamers
  List<FeedbackVideoStreamer> getListFeedbackStreamer() {
    return this.listFeedbackVideoStreamer;
  }

  //TODO where we need to call this??
  void emptyListFeedbackStreamer() {
    this.listFeedbackVideoStreamer = List();
  }

  /// The function cut a video with a given path to List of files
  Future<List<File>> cutVideoList(String videoPath) {
    return this.cameraHandler.cutVideoList(videoPath);
  }

  /// The function set the list of file need to get feedback
  void setListFileNeedFeedback(List<File> list) {
    this.listFileNeedFeedback = list;
  }

  /// The function return the list of file that need to get Feedback
  List<File> getListNeed() {
    if(this.listFileNeedFeedback == null) {
      return List();
    }
    return this.listFileNeedFeedback;
  }

  //TODO check if we need this feature ?
  Future<FeedbackVideo> postVideoForDownload(Uint8List fileBytes, int length,
      String filePath) async {
    String path = "/uploadForDownload";
    http.MultipartFile multipartFile = http.MultipartFile.fromBytes(
        'file',
        fileBytes,
        filename: filePath
    );
    Future<String> result = this.connectionHandler.postMultiPartFile(path, multipartFile);
    FeedbackVideo output;
    await result.then((jsonString) {
      Map responseMap = json.decode(jsonString);
      ServerResponse response = ServerResponse.fromJson(responseMap);
      Map map = response.value as Map;
      output = FeedbackVideo.factory(map);
    });
    return output;
  }

}