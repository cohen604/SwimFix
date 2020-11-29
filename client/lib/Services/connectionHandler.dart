import 'dart:io';
import 'dart:typed_data';

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

    Future<bool> postFile(String path, http.MultipartFile file) async {
      String url = this.address + ':' + this.port + path;
      var request = new http.MultipartRequest('POST', Uri.parse(url));
      request.files.add(file);
      http.Response response = await http.Response.fromStream(await request.send());
      if (response.statusCode == 200) {
        print(response.body);
        return true;
      } else {
        throw Exception('Error code !!! :0');
      }
      return true;
    }

  //TODO delete this
  Future<bool> postMobileFile(String path, File file) async {
    http.MultipartFile multipartFile = new http.MultipartFile(
        'video',
        file.readAsBytes().asStream(),
        file.lengthSync(),
        filename: file.path
    );
    return postFile(path, multipartFile);
  }

  Future<bool> postWebFile(String path, Uint8List fileBytes, int length,
      String filePath) async {
    http.MultipartFile multipartFile = http.MultipartFile.fromBytes(
          'file',
          fileBytes,
          filename: filePath
      );
    return postFile(path, multipartFile);
  }




}