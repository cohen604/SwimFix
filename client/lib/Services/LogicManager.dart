import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Domain/Swimer.dart';
import 'package:client/Services/Authentication.dart';
import 'package:client/Services/CameraHandler.dart';
import 'package:client/Services/GoogleAuth.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:http/http.dart' as http;
import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/ServerResponse.dart';
import 'package:client/Services/ConnectionHandler.dart';

class LogicManager {

  static LogicManager logicManager;
  ConnectionHandler connectionHandler;
  CameraHandler cameraHandler;
  Swimmer swimmer;

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

  Future<bool> login(Swimmer swimmer) async{
    String path = "/login";
    ServerResponse response = await connectionHandler.postMessage(path, swimmer.toJson());
    //TODO check if response is valid
    Map map = response.value as Map;
    this.swimmer = swimmer;
    return true;
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
    ServerResponse response = await this.connectionHandler.postMultiPartFile(path, multipartFile);
    //TODO check if response is valid
    Map map = response.value as Map;
    return FeedbackVideoStreamer.factory(map);
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
    ServerResponse response = await this.connectionHandler.postMultiPartFile(path, multipartFile);
    //TODO check if response is valid
    Map map = response.value as Map;
    return FeedbackVideo.factory(map);
  }

}