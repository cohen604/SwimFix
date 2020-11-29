import 'dart:io';

// import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

class UploadVideo extends StatefulWidget {

  UploadVideo({Key key}) : super(key: key);

  @override
  _UploadVideoState createState() => _UploadVideoState();
}

class _UploadVideoState extends State<UploadVideo> {

  File _video;            /// video from the gallery
  File _cameraVideo;      /// video from the camera

  File cv;

  ImagePicker picker = ImagePicker();

  /// pick video from the gallery
  _pickVideoFromGallery() async {
    PickedFile pickedFile = await picker.getVideo(source: ImageSource.gallery);
    setState(() {
      _video = File(pickedFile.path);;
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
  //
  // _computerVideo() async {
  //   FilePickerResult result = await FilePicker.platform.pickFiles();
  //   if(result != null) {
  //     setState(() {
  //       cv = File(result.files.single.path);
  //     });
  //   } else {
  //     // User canceled the picker
  //   }
  // }

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
              child: Column(
                children: <Widget>[
                  SizedBox(height: 50),
                  Text(
                    "Click on Pick Video to select video",
                    style: TextStyle(fontSize: 18.0),
                  ),
                  RaisedButton(
                    onPressed: () {
                       // kIsWeb? _computerVideo() :
                      _pickVideoFromGallery();
                    },
                    child: Text("Pick Video From Gallery"),
                  ),
                  SizedBox(height: 50),
                  Text(
                      "Click on Pick Video to select video",
                      style: TextStyle(fontSize: 18.0),
                    ),
                  RaisedButton(
                    onPressed: () {
                      _pickVideoFromCamera();
                    },
                    child: Text("Pick Video From Camera"),
                  ),
                  SizedBox(height: 50),
                  Text(
                    "Send the video to analyze",
                    style: TextStyle(fontSize: 18.0),
                  ),
                  RaisedButton(
                    onPressed: () {
                      _pickVideoFromCamera();
                    },
                    child: Text("analyze video"),
                  )
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

}
