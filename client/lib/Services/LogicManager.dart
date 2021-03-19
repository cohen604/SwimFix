import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:camera/camera.dart';
import 'package:client/Domain/FeedbackFilters.dart';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Domain/ResearcherReport.dart';
import 'package:client/Domain/Swimmer.dart';
import 'package:client/Services/Authentication.dart';
import 'package:client/Services/CameraHandler.dart';
import 'package:client/Services/GoogleAuth.dart';
import 'package:client/Services/MlHandler.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:http/http.dart' as http;
import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/ServerResponse.dart';
import 'package:client/Services/ConnectionHandler.dart';
import 'package:path_provider/path_provider.dart';

class LogicManager {

  static LogicManager logicManager;
  ConnectionHandler connectionHandler;
  MlHandler mlHandler;

  // TODO refactor this
  Swimmer swimmer;

  LogicManager() {
    this.connectionHandler = new ConnectionHandler();
    this.mlHandler = new MlHandler();
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
          path, swimmer.toJson());
      if (response != null && response.isSuccess()) {
        // Map map = response.value as Map;
        this.swimmer = swimmer;
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
          path, swimmer.toJson());
      if (response != null && response.isSuccess() && response.value) {
          return true;
      }
    } catch(e) {
      print('error in logout ${e.toString()}');
    }
    return false;
  }

  /// The function send a post request for receiving a feedback link
  /// fileBytes -
  /// length -
  /// filePath -
  /// return
  Future<FeedbackVideoStreamer> postVideoForStreaming(Uint8List fileBytes, int length,
      String filePath) async {
    String path = "/swimmer/feedback/link";
    http.MultipartFile multipartFile = http.MultipartFile.fromBytes(
        'file',
        fileBytes,
        filename: filePath,
    );
    //ServerResponse response = await this.connectionHandler.postMultiPartFile(path, multipartFile);
    ServerResponse response = await this.connectionHandler.postMultiPartFileWithID(path, multipartFile,
        this.swimmer.uid, this.swimmer.email, this.swimmer.name);
    //TODO check if response is valid
    Map map = response.value as Map;
    return FeedbackVideoStreamer.factory(map);
  }

  Future<ResearcherReport> postVideoAndCsvForAnalyze(
      String videoPath, videoBytes, String labelPath, labelsBytes,
      Swimmer swimmer) async {
    String path = "/researcher/report";
    http.MultipartFile multipartVideo = http.MultipartFile.fromBytes(
      'video', videoBytes, filename: videoPath,
    );
    http.MultipartFile multipartLabels = http.MultipartFile.fromBytes(
      'labels', labelsBytes, filename: labelPath,
    );
    try {
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

  // TODO delete this
  Future<String> combineListElements(List <Uint8List> imagesBytes) async {
    Directory appDocDirectory = await getTemporaryDirectory();
    File file = new File(appDocDirectory.path + '/CombinedBytes');
    await file.create();
    for(Uint8List img in imagesBytes) {
      file.writeAsBytesSync(img);
    }
    print('file bytes ${file.lengthSync()}');
    return file.absolute.path;
  }

  // Note: Don't use it
  Future<FeedbackVideoStreamer> postListImagesForStreaming(List <Uint8List> imagesBytes, String type,
      String fileName) async {
    String path = "/uploadListForStream";
    // combine all the list of images bytes to one image list
    // create the multi part file images
    print('number of frames ${imagesBytes.length}');
    //data.codeUnits
    print('start preprocessing list of images bytes ${DateTime.now()}');
    http.MultipartFile images = await http.MultipartFile.fromPath(
      'data',
      await combineListElements(imagesBytes),
      filename: "Data",
    );
    // create meta file
    Map map = new Map();
    map['imageType'] = type;
    String metaData = json.encode(map);
    //metaData.codeUnits
    http.MultipartFile meta = http.MultipartFile.fromBytes(
      'meta',
      [],
      filename: 'metaData',
    );
    print('end preprocessing list of images bytes ${DateTime.now()}');
    print('send to server ${DateTime.now()}');
    ServerResponse response = await this.connectionHandler.postMultiPartFiles(
        path, meta, images, swimmer.uid, swimmer.email, swimmer.name,);
    //TODO check if response is valid
    Map mapResponse = response.value as Map;
    print(mapResponse);
    return FeedbackVideoStreamer.factory(mapResponse);
  }


  Future<FeedbackVideoStreamer> filterFeedback(FeedbackFilters filter) async {
    String path = "/swimmer/feedback/filter";
    ServerResponse response = await this.connectionHandler.postMessageWithID(path, filter.toMap(),
        this.swimmer.uid, this.swimmer.email, this.swimmer.name);
    //TODO check if response is valid
    Map map = response.value as Map;
    return FeedbackVideoStreamer.factory(map);
  }

  /// handle the prediction
  Future<bool> predictValidFrame(CameraImage img) async {
    return await mlHandler.predictValidFrame(img);
  }

  bool predictValidFrameBlue(CameraImage img) {
    return mlHandler.predictValidFrameBlue(img);
  }

}