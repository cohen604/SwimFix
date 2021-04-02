import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:camera/camera.dart';
import 'package:client_application/Domain/Files/FileDonwloaded.dart';
import 'package:client_application/Domain/ServerResponse.dart';
import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Domain/Video/FeedBackVideoStreamer.dart';
import 'package:client_application/Domain/Video/FeedbackFilters.dart';
import 'package:path_provider/path_provider.dart';
import 'package:http/http.dart' as http;
import 'ConnectionHandler.dart';
import 'MlHandler.dart';


class LogicManager {

  static LogicManager logicManager;
  ConnectionHandler connectionHandler;
  MlHandler mlHandler;

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
  Future<FeedbackVideoStreamer> postVideoForStreaming(
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
      return FeedbackVideoStreamer.factory(map);
    }
    catch(e) {
      print('error in post video for stream ${e.toString()}');
    }
    return null;
  }

  String getStreamUrl() {
    return connectionHandler.getStreamUrl();
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
  Future<FeedbackVideoStreamer> postListImagesForStreaming(
      List <Uint8List> imagesBytes,
      String type,
      String fileName,
      Swimmer swimmer) async {
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


  Future<FeedbackVideoStreamer> filterFeedback(
      FeedbackFilters filter,
      Swimmer swimmer) async {
    String path = "/swimmer/feedback/filter";
    ServerResponse response = await this.connectionHandler.postMessageWithID(path, filter.toMap(),
        swimmer.uid, swimmer.email, swimmer.name);
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

  Future<FileDownloaded> getFileForDownload(String uid, String email, String name, String fileLink) async {
    String path = "/researcher/$fileLink";
    return await this.connectionHandler.downloadFile(
      path, uid, email, name);
  }

}