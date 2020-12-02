import 'dart:convert';
import 'dart:html' as html;
import 'dart:typed_data';
import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Services/connectionHandler.dart';
import 'package:flutter/material.dart';

import 'TitleButton.dart';
import 'VideoPreview.dart';

class WebInput extends StatefulWidget {

  WebInput({Key key}) : super(key: key);

  @override
  _WebInputState createState() => _WebInputState();
}

class _WebInputState extends State<WebInput> {

  Uint8List fileBytes;
  int fileLength;
  String filePath;
  bool clickUpload = false;
  Future<FeedbackVideo> feedbackVideo;
  Future<FeedbackVideoStreamer> feedbackVideoStreamer;

  void uploadFile() async {
    html.InputElement uploadInput = html.FileUploadInputElement();
    uploadInput.multiple = false;
    uploadInput.draggable = true;
    uploadInput.accept = 'video/*';
    uploadInput.click();
    html.document.body.append(uploadInput);
    uploadInput.onChange.listen((e) {
      var files = uploadInput.files;
      var file = files[0];
      var filePath = file.name;
      final reader = new html.FileReader();
      reader.readAsDataUrl(file.slice(0, file.size, file.type));
      reader.onLoadEnd.listen((e) {
        var bytes = Base64Decoder().convert(reader.result.toString().split(",").last);
        setState(() {
          this.fileBytes = bytes;
          this.fileLength = this.fileBytes.length;
          this.filePath = filePath;
          print('File path: ' + filePath);
          print('File size: ' + file.size.toString());
        });
      });
    });
    uploadInput.remove();
  }

  void getFeedback(BuildContext innerContext) async {
    this.setState(() {
      this.clickUpload = true;
      LogicManager logicManager = LogicManager.getInstance();
      feedbackVideoStreamer = logicManager.postVideoForStreaming(this.fileBytes,
          this.fileLength, this.filePath);
    });
  }


  Widget buildSelectedFile(BuildContext context) {
    if(this.filePath == null) {
      return Text("");
    }
    return Container(
      decoration: const BoxDecoration(
        border: Border(
          top: BorderSide(width: 1.0, color: Colors.black),
          left: BorderSide(width: 1.0, color: Colors.black),
          right: BorderSide(width: 1.0, color: Colors.black),
          bottom: BorderSide(width: 1.0, color: Colors.black),
        ),
        color: Color.fromRGBO(105, 173, 251, 1),
      ),
      child: Padding(
        padding: EdgeInsets.all(12.0),
        child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              SizedBox(height: 10,),
              Text("File: " + this.filePath,
                style: TextStyle(fontWeight: FontWeight.bold,
                    fontSize: 16),
                textAlign: TextAlign.left,
              ),
              SizedBox(height: 10,),
            ],
          ),
        ),
      );
  }

  Widget buildVideoPreview(BuildContext context) {
    return FutureBuilder<FeedbackVideoStreamer>(
        future: this.feedbackVideoStreamer,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            return VideoPreview(feedbackVideoStreamer:snapshot.data);
          }
          if(this.clickUpload) {
            return Center(
              child: CircularProgressIndicator(),
            );
          }
          return Text("");
        }
    );
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        SizedBox(height: 50,),
        Column(
          children: [
            TitleButton(
              title:"Pick Video from your computer",
              buttonText: "Upload",
              onPress: uploadFile
            ),
            SizedBox(height: 10,),
            buildSelectedFile(context),
            SizedBox(height: 10,),
            TitleButton(
                title:"Submit video to SwimFix for feedback",
                buttonText: "Submit",
                onPress: ()=>getFeedback(context)
            ),
          ],
        ),
        SizedBox(height: 20,),
        Expanded(
          child:buildVideoPreview(context),
        ),
      ],
    );
  }
}