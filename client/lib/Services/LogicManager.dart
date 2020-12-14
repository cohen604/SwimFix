import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Services/CameraHandler.dart';
import 'package:http/http.dart' as http;
import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/ServerResponse.dart';
import 'package:client/Services/ConnectionHandler.dart';

class LogicManager {

  static LogicManager logicManager;
  ConnectionHandler connectionHandler;
  CameraHandler cameraHandler;

  LogicManager() {
    this.connectionHandler = new ConnectionHandler();
    this.cameraHandler = new CameraHandler();
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

  /// The function cut a video with a given path to List of files
  Future<List<File>> cutVideoList(String videoPath) {
    return this.cameraHandler.cutVideoList(videoPath);
  }

  /// The function delete the cutFolder from the mobile phone
  void cleanCutFolder() {
    this.cameraHandler.deleteDir();
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