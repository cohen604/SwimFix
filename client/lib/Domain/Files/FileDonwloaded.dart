import 'dart:typed_data';

class FileDownloaded{

  String fileName;
  Uint8List bytes;

  FileDownloaded(String fileName, Uint8List bytes) {
    this.fileName = fileName;
    this.bytes = bytes;
  }

}