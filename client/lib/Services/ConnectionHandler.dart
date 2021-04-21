import 'dart:convert';
import 'package:client/Domain/Files/FileDonwloaded.dart';
import 'package:client/Domain/ServerResponse.dart';
import 'package:http/http.dart' as http;

/// Class that responsible for communication with the server
class ConnectionHandler {

  String address;   /// the address of the server
  String port;      /// the port of the server

  /// Constructor
  ConnectionHandler() {
    this.address = 'http://127.0.0.1'; //'http://132.72.96.31';
    this.port = '8080';
  }

  /// The function receives a String body and generate form him a server response
  ServerResponse toServerResponse(String body) {
    String jsonString = body;
    Map responseMap = json.decode(jsonString);
    return ServerResponse.fromJson(responseMap);
  }

  /// function that get response from the server in the address [path]
  Future<ServerResponse> getMessage (String path) async {
    String url = getUrl() + path;
    final response = await http.get(url);
      if (response.statusCode == 200) {
        return toServerResponse(response.body);
      } else {
        throw Exception('Error code !!! :0');
      }
    }

  /// function that post response from the server in the address [path]
  /// path - the path to send the post message in the server
  /// value - the object json to send to the server
  Future<ServerResponse> postMessage (String path, Map<String, dynamic> map) async {
    String url = getUrl() + path;
    //TODO change this value to json(value)
    Map<String,String> headers = {
      'Content-type' : 'application/json',
      'Accept': 'application/json',
    };
    print('$url json ${json.encode(map)}');
    final response = await http.post(url, body: json.encode(map), headers: headers);
    if (response.statusCode == 200) {
      return toServerResponse(response.body);
    } else {
      throw Exception('Error: post message send to $path');
    }
  }

  /// The function send a post message with a multi part file to the server
  Future<ServerResponse> postMultiPartFile(String path, http.MultipartFile file) async {
    String url = getUrl() + path;
    var request = new http.MultipartRequest('POST', Uri.parse(url));
    request.files.add(file);
    http.Response response = await http.Response.fromStream(await request.send());
    if (response.statusCode == 200) {
      return toServerResponse(response.body);
    } else {
      throw Exception('Error: post message send to $path');
    }
  }

  /// The function send a post message with a body and a multi part file to the server
  Future<ServerResponse> postMultiPartFileWithID(String path, http.MultipartFile file,
      String uid, String email, String name) async {
    String url = getUrl() + path;
    var request = new http.MultipartRequest('POST', Uri.parse(url));
    request.files.add(file);
    request.fields['uid'] = uid;
    request.fields['email'] = email;
    request.fields['name'] = name;
    http.Response response = await http.Response.fromStream(await request.send());
    if (response.statusCode == 200) {
      return toServerResponse(response.body);
    } else {
      throw Exception('Error: post message send to $path');
    }
  }

  /// The function send a post message with a body and a multi part file to the server
  Future<ServerResponse> postMessageWithID(String path, Map<String, dynamic> message,
      String uid, String email, String name) async {
    String url = getUrl() + path;
    var request = new http.MultipartRequest('POST', Uri.parse(url));
    request.fields['body'] = json.encode(message);
    request.fields['uid'] = uid;
    request.fields['email'] = email;
    request.fields['name'] = name;
    http.Response response = await http.Response.fromStream(await request.send());
    if (response.statusCode == 200) {
      return toServerResponse(response.body);
    } else {
      throw Exception('Error: post message send to $path');
    }
  }

  /// The function send a post message with a multi part file  and meta data about it to the server
  Future<ServerResponse> postMultiPartFiles(String path,
      http.MultipartFile firstFile,
      http.MultipartFile secondFile,
      String uid, String email, String name) async {
    print('post to server 2 multipart files');
    String url = getUrl() + path;
    var request = new http.MultipartRequest('POST', Uri.parse(url));
    if(firstFile!=null) {
      request.files.add(firstFile);
    }
    if(secondFile!=null) {
      request.files.add(secondFile);
    }
    request.fields['uid'] = uid;
    request.fields['email'] = email;
    request.fields['name'] = name;
    print('request $request');
    http.Response response = await http.Response.fromStream(await request.send());
    if (response.statusCode == 200) {
      print('server response ${toServerResponse(response.body)}');
      return toServerResponse(response.body);
    } else {
      throw Exception('Error: post message send to $path');
    }
  }

  String getUrl() {
    return this.address + ':' + this.port ;
  }

  String getStreamUrl(){
    return getUrl() + "/stream";
  }

  Future<FileDownloaded> downloadFile(String path, Map<String, dynamic> map) async {
    // print('get file from $path');
    String url = getUrl() + path;
    Map<String,String> headers = {
      'Accept' : '*',
      'Access-Control-Allow-Origin': "*",
      'Content-type' : 'application/json',
    };
    http.Response response = await http.post(Uri.parse(url),
        body: json.encode(map), headers: headers);
    if (response.statusCode == 200) {
      String headerValue = response.headers['content-type'];
      int start = headerValue.indexOf("/");
      String fileName = 'file.${headerValue.substring(start + 1)}';
      print(fileName);
      return new FileDownloaded(fileName, response.bodyBytes);
    }
    throw Exception('Error: get message send to $path');
  }


}
