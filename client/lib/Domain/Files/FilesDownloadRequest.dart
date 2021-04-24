import 'dart:convert';

import 'package:client/Domain/Users/Swimmer.dart';

class FilesDownloadRequest {

  Swimmer swimmer;
  List<String> files;

  FilesDownloadRequest(this.swimmer, this.files);

  Map<String, dynamic> toJson() =>
      {
        'user': swimmer,
        'files': files,
      };
}