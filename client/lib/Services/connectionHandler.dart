import 'dart:io';

import 'package:http/http.dart' as http;
import 'dart:convert';

class ConnectionHandelr {

  String address;
  String port;

  ConnectionHandelr(String adderss, String port) {
    this.address = 'http://127.0.0.1';
    this.port = '8080';
  }

  Future<String> getMessage (String path) async {
    Map<String, String> requestHeaders = {
      // 'Content-type': 'application/json',
      // 'Accept': 'application/json',
      'Access-Control-Allow-Credentials': 'true',
      'Content-Type': 'application/json; charset=UTF-8',
      'Access-Control-Allow-Methods': 'POST, OPTIONS, GET',
      'Access-Control-Allow-Headers': '*',
      'Access-Control-Allow-Origin': '*',

    };
      final response = await http.get(this.address + ':' + this.port + path);
      if (response.statusCode == 200) {
        return response.body;
      } else {
        throw Exception('Error code !!! :0');
      }
    }
  }