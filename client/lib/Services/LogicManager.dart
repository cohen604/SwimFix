import 'dart:convert';
import 'dart:typed_data';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:http/http.dart' as http;
import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/ServerResponse.dart';
import 'package:client/Services/connectionHandler.dart';

class LogicManager {

  static LogicManager logicManager;
  ConnectionHandler connectionHandler;

  LogicManager() {
    this.connectionHandler = new ConnectionHandler();
  }

  static LogicManager getInstance() {
    if(logicManager == null) {
      logicManager = new LogicManager();
    }
    return logicManager;
  }

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

  Future<FeedbackVideoStreamer> postVideoForStreaming(Uint8List fileBytes, int length,
      String filePath) async {
    String path = "/uploadForStream";
    http.MultipartFile multipartFile = http.MultipartFile.fromBytes(
        'file',
        fileBytes,
        filename: filePath
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
}