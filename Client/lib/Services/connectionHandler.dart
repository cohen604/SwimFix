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
    this.address = 'http://10.0.0.34'; //'http://127.0.0.1';
    this.port = '8080';
  }

  /// function that get response from the server in the address [path]
  Future<String> getMessage (String path) async {
    String url = getUrl() + path;
    final response = await http.get(url);
      if (response.statusCode == 200) {
        return response.body;
      } else {
        throw Exception('Error code !!! :0');
      }
    }

  /// function that post response from the server in the address [path]
  Future<String> postMessage (String path, Object value) async {
    String url = getUrl() + path;
    //TODO change this value to json(value)
    final response = await http.post(url, body: value);
    if (response.statusCode == 200) {
      return response.body;
    } else {
      throw Exception('Error code !!! :0');
    }
  }

  /// The function send a post message with a multi part file to the server
  Future<String> postMultiPartFile(String path, http.MultipartFile file) async {
    String url = getUrl() + path;
    var request = new http.MultipartRequest('POST', Uri.parse(url));
    request.files.add(file);
    //print(request);
    http.Response response = await http.Response.fromStream(await request.send());
    if (response.statusCode == 200) {
      return response.body;
    } else {
      throw Exception('Error code !!! :0');
    }
    return null;
  }

  String getUrl() {
    return this.address + ':' + this.port ;
  }

  String getStreamUrl(){
    return getUrl() + "/stream";
  }


}