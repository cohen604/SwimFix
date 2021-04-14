import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:client_application/Domain/Pair.dart';
import 'package:client_application/Domain/ServerResponse.dart';
import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Domain/Video/FeedBackLink.dart';
import 'package:client_application/Services/VideoHanadler.dart';
import 'package:http/http.dart' as http;
import 'ConnectionHandler.dart';

class LogicManager {

  static LogicManager logicManager;
  ConnectionHandler _connectionHandler;
  VideoHandler _videoHandler;

  LogicManager() {
    _connectionHandler = new ConnectionHandler();
    _videoHandler = new VideoHandler();
  }

  static LogicManager getInstance() {
    if (logicManager == null) {
      logicManager = new LogicManager();
    }
    return logicManager;
  }

  Future<bool> login(Swimmer swimmer) async {
    String path = "/login";
    try {
      ServerResponse response = await _connectionHandler.postMessage(
          path, swimmer.toJson());
      if (response != null && response.isSuccess()) {
        // Map map = response.value as Map;
        return true;
      }
    } catch (e) {
      print('error in login ${e.toString()}');
    }
    return false;
  }

  Future<bool> logout(Swimmer swimmer) async {
    String path = '/logout';
    try {
      ServerResponse response = await _connectionHandler.postMessage(
          path, swimmer.toJson());
      if (response != null && response.isSuccess() && response.value) {
        return true;
      }
    } catch (e) {
      print('error in logout ${e.toString()}');
    }
    return false;
  }

  /// The function send a post request for receiving a feedback link
  /// fileBytes -
  /// length -
  /// filePath -
  /// return
  Future<FeedbackLink> getFeedback(
      Swimmer swimmer,
      Uint8List fileBytes,
      int length,
      String filePath) async {
    try {
      String path = "/swimmer/feedback/link";
      http.MultipartFile multipartFile = http.MultipartFile.fromBytes(
        'file',
        fileBytes,
        filename: filePath,
      );
      //ServerResponse response = await this.connectionHandler.postMultiPartFile(path, multipartFile);
      ServerResponse response = await _connectionHandler
          .postMultiPartFileWithID(path, multipartFile,
          swimmer.uid, swimmer.email, swimmer.name);
      //TODO check if response is valid
      Map map = response.value as Map;
      return FeedbackLink.factory(map);
    }
    catch (e) {
      print('error in post video for stream ${e.toString()}');
    }
    return null;
  }

  String getStreamUrl() {
    return _connectionHandler.getStreamUrl();
  }

  Future<List<Pair<int, int>>> getSwimmingVideoTimes(String path) async{
    return await _videoHandler.splitVideoToTimes(path);
  }

  Future<FeedbackLink> getFeedbackFromTimes(
      Swimmer swimmer,
      String videoPath,
      int startTime,
      int endTime) async {
    try {
      String newPath = 'feedback.mp4';
      File file = await _videoHandler.splitVideo(
          videoPath, newPath, startTime, endTime);
      Uint8List fileBytes = file.readAsBytesSync();
      int length = file.lengthSync();
      String filePath = file.path;
      FeedbackLink output = await getFeedback(swimmer, fileBytes, length, filePath);
      _videoHandler.deleteVideo(newPath);
      return output;
    }
    catch(e) {
      print('error in feedbackFromTimes ${e.toString()}');
    }
    return null;
  }

}