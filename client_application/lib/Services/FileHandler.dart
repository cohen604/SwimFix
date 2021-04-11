import 'dart:io';

class FileHandler {

  Directory _folder;

  FileHandler() {
    _folder = _createDirectory("swimAnalytics");
  }

  Directory _createDirectory(String path) {
    Directory dir = Directory(path);
    _deleteTmpDirectory(path);
    dir.create(recursive: true);
    if(dir.existsSync()) {
      print('created dir');
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

  bool deleteFile(String path) {
    File file = getFile(path);
    if(file!=null) {
      file.deleteSync();
      // print('file is exists: ${file.existsSync()}');
      return true;
    }
    return false;
  }

}