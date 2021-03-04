import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Domain/ServerResponse.dart';
import 'package:flutter/material.dart';
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

  String getUrl() {
    return this.address + ':' + this.port ;
  }

  String getStreamUrl(){
    return getUrl() + "/stream";
  }


}
