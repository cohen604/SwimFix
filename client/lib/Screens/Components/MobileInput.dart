import 'dart:io';
import 'dart:typed_data';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Services/connectionHandler.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

class MobileInput extends StatefulWidget {

  MobileInput({Key key}):super(key:key);

  @override
  State<StatefulWidget> createState() => new _MobileInputState();
}

class _MobileInputState extends State<MobileInput> {

  ImagePicker _picker = ImagePicker();
  File _file;
  FeedbackVideo feedbackVideo;

  void uploadVideoFromGallery() async {
     PickedFile pickedFile = await _picker.getVideo(source: ImageSource.gallery);
     setState(() {
       _file = File(pickedFile.path);
     });
  }

  void uploadVideoFromCamera() async {
    PickedFile pickedFile = await _picker.getVideo(source: ImageSource.camera);
    setState(() {
      _file = File(pickedFile.path);
    });
  }

  void getFeedBack(BuildContext innerContext) async {
    Uint8List bytes = _file.readAsBytesSync();
    LogicManager logicManger = LogicManager.getInstance();
    var feedbackVideo = await logicManger.postVideoForDownload(bytes, await _file.length(), _file.path);
    this.setState(() {
      this.feedbackVideo=feedbackVideo;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        SizedBox(height: 50),
        Text(
          "Click on Pick Video to select video",
          style: TextStyle(fontSize: 18.0),
        ),
        RaisedButton(
          onPressed: uploadVideoFromGallery,
          child: Text("Pick Video From Gallery"),
        ),
        SizedBox(height: 50),
        Text(
          "Click on Pick Video to select video",
          style: TextStyle(fontSize: 18.0),
        ),
        RaisedButton(
          onPressed: uploadVideoFromCamera,
          child: Text("Pick Video From Camera"),
        ),
        SizedBox(height: 50),
        Text(
          "Send the video to analyze",
          style: TextStyle(fontSize: 18.0),
        ),
        RaisedButton(
          onPressed: ()=>getFeedBack(context),
          child: Text("analyze video"),
        )
      ],
    );
    throw UnimplementedError();
  }

}