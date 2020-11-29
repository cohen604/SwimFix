import 'dart:io';

import 'package:http/http.dart' as http;

/// Class that responsible for communication with the server
class ConnectionHandler {

  String address;   /// the address of the server
  String port;      /// the port of the server

  /// Constructor
  ConnectionHandler(String address, String port) {
    this.address = 'http://127.0.0.1';
    this.port = '8080';
  }

  /// function that get response from the server in the address [path]
  Future<String> getMessage (String path) async {
    String url = this.address + ':' + this.port + path;
    final response = await http.get(url);
      if (response.statusCode == 200) {
        return response.body;
      } else {
        throw Exception('Error code !!! :0');
      }
    }

  /// function that post response from the server in the address [path]
  Future<String> postMessage (String path, Object value) async {
    String url = this.address + ':' + this.port + path;
    //TODO change this value to json(value)
    final response = await http.post(url, body: value);
    if (response.statusCode == 200) {
      return response.body;
    } else {
      throw Exception('Error code !!! :0');
    }
  }

  Future<bool> postVideo(String path, File file) async {
    String url = this.address + ':' + this.port + path;
    var request = http.MultipartRequest('POST', Uri.parse(url));
    request.files.add(
        http.MultipartFile(
            'video',
            file.readAsBytes().asStream(),
            file.lengthSync(),
            filename: file.path
        )
    );
    var res = await request.send();
    print(res);
    return true;
  }

}