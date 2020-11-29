import 'dart:io';
import 'package:client/Screens/Components/MobileInput.dart';
import 'package:client/Screens/Components/WebInput.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:client/Services/connectionHandler.dart';

import 'Screen.dart';

class UploadScreen extends Screen {

  UploadScreen({Key key}) : super(key: key);

  @override
  _UploadScreenState createState() => _UploadScreenState();
}

class _UploadScreenState extends State<UploadScreen> {

  File _video;            /// video from the gallery
  File _cameraVideo;      /// video from the camera
  File cv;
  ImagePicker picker = ImagePicker();

  /// The function pick video from the gallery
  _pickVideoFromGallery() async {
    PickedFile pickedFile = await picker.getVideo(source: ImageSource.gallery);
    setState(() {
      _video = File(pickedFile.path);
    });
  }

  /// film video with the camera
  _pickVideoFromCamera() async {
    // TODO - check if the user is from web or mobile
    PickedFile pickedFile = await picker.getVideo(source: ImageSource.camera);
    setState(() {
      _cameraVideo = File(pickedFile.path);
    });
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: Text("Image / Video Picker"),
        ),
        body: SingleChildScrollView(
          child: Center(
            child: Container(
              padding: const EdgeInsets.all(16.0),
              child: LayoutBuilder()
              child: Column(
                children: <Widget>[
                  WebInput(),
                  MobileInput(),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

}
