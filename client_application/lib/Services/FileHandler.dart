import 'dart:io';

import 'package:path_provider/path_provider.dart';

class FileHandler {

  Directory _folder;

  FileHandler() {
    getTemporaryDirectory().then((value) {
      if(value == null || !value.existsSync()) {
        throw Exception('Not create swimming dir');
      }
      _folder = value;
    }); //_createDirectory("swimAnalytics");
  }

  Directory _createDirectory(String path) {
    Directory dir = Directory(path);
    _deleteTmpDirectory(path);
    dir.create(recursive: true);
    if(dir.existsSync()) {
      print('created dir');
    }
    else {
      throw Exception('Not create swimming dir');
    }
    return dir;
  }

  bool _deleteTmpDirectory(String path) {
    Directory dir = Directory(path);
    if(dir.existsSync()) {
      dir.deleteSync(recursive: true);
      if(dir.existsSync()) {
        print('delete the old dir');
        return true;
      }
    }
    return false;
  }

  String getFolderPath() {
    return _folder.path;
  }

  File getFile(String path) {
    File file = File(path);
    if(file!=null && file.existsSync()) {
      return file;
    }
    return null;
  }

  void deleteFile(String path) {
    File file = getFile(path);
    if(file!=null) {
      file.deleteSync();
      // print('file is exists: ${file.existsSync()}');
    }
    else {
      throw new Exception('error in Delete File');
    }
  }

}