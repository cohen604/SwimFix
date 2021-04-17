import 'dart:typed_data';
import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Domain/Files/FileDonwloaded.dart';
import 'package:client/Domain/Users/ResearcherReport.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/UserPermissions.dart';
import 'package:http/http.dart' as http;
import 'package:client/Domain/ServerResponse.dart';
import 'package:client/Services/ConnectionHandler.dart';

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

  Future<bool> login(Swimmer swimmer) async{
    String path = "/login";
    try {
      ServerResponse response = await connectionHandler.postMessage(
          path,
          swimmer.toJson());
      if (response != null && response.isSuccess()) {
        // Map map = response.value as Map;
        return true;
      }
    } catch(e) {
      print('error in login ${e.toString()}');
    }
    return false;
  }

  Future<bool> logout(Swimmer swimmer) async {
    String path = '/logout';
    try {
      ServerResponse response = await connectionHandler.postMessage(
          path,
          swimmer.toJson());
      if (response != null && response.isSuccess() && response.value) {
          return true;
      }
    } catch(e) {
      print('error in logout ${e.toString()}');
    }
    return false;
  }

  Future<UserPermissions> getPermissions(Swimmer swimmer) async {
    String path = '/permissions';
    try {
      ServerResponse response = await connectionHandler.postMessage(
          path,
          swimmer.toJson());
      if (response != null && response.isSuccess()) {
        Map map = response.value as Map;
        print(map);
        return UserPermissions.factory(map);
      }
    } catch(e) {
      print('error in permissions ${e.toString()}');
    }
    return null;
  }

  /// The function send a post request for receiving a feedback link
  /// fileBytes -
  /// length -
  /// filePath -
  /// return
  Future<FeedBackLink> postVideoForStreaming(
      Uint8List fileBytes,
      int length,
      String filePath,
      Swimmer swimmer) async {
    try {
      String path = "/swimmer/feedback/link";
      http.MultipartFile multipartFile = http.MultipartFile.fromBytes(
        'file',
        fileBytes,
        filename: filePath,
      );
      //ServerResponse response = await this.connectionHandler.postMultiPartFile(path, multipartFile);
      ServerResponse response = await this.connectionHandler
          .postMultiPartFileWithID(path, multipartFile,
          swimmer.uid, swimmer.email, swimmer.name);
      //TODO check if response is valid
      Map map = response.value as Map;
      return FeedBackLink.factory(map);
    }
    catch(e) {
      print('error in post video for stream ${e.toString()}');
    }
    return null;
  }

  String getStreamUrl() {
    return connectionHandler.getStreamUrl();
  }

  Future<ResearcherReport> postVideoAndCsvForAnalyze(
      String videoPath, videoBytes, String labelPath, labelsBytes,
      Swimmer swimmer) async {
    String path = "/researcher/report";
    try {
      http.MultipartFile multipartVideo = http.MultipartFile.fromBytes(
        'video', videoBytes, filename: videoPath,
      );
      http.MultipartFile multipartLabels;
      if(labelsBytes != null && labelPath != null) {
        multipartLabels = http.MultipartFile.fromBytes(
          'labels', labelsBytes, filename: labelPath,
        );
      }
      ServerResponse response = await this.connectionHandler.postMultiPartFiles(path,
          multipartVideo, multipartLabels, swimmer.uid, swimmer.email, swimmer.name);
      if (response.value != null) {
        Map mapResponse = response.value as Map;
        return ResearcherReport.factory(mapResponse);
      }
    }
   catch(e) {
     print('error in $path with ${e.toString()}');
   }
    return null;
  }

  Future<FileDownloaded> getFileForDownload(String uid, String email, String name, String fileLink) async {
    String path = "/researcher/$fileLink";
    return await this.connectionHandler.downloadFile(
      path, uid, email, name);
  }

  Future<bool> sendInvitationEmail(Swimmer swimmer, String email) async {
    String path = '/coach/invite';
    Map<String, dynamic> map = swimmer.toJson();
    map['to'] = email;
    try {
      ServerResponse response = await connectionHandler.postMessage(
          path, map);
      if (response != null && response.isSuccess() && response.value) {
        return true;
      }
    }
    catch(e) {
      print(e);
      print('error in $path with ${e.toString()}');
    }
    return false;
  }

}