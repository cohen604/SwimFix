import 'dart:convert';
import 'dart:html' as html;
import 'dart:typed_data';
import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Services/connectionHandler.dart';
import 'package:flutter/material.dart';

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
    });
    ConnectionHandler connectionHandler = new ConnectionHandler();
    this.setState(() {
      // feedbackVideo = connectionHandler.postVideoForDownload(this.fileBytes,
      //     this.fileLength, this.filePath);
      feedbackVideoStreamer = connectionHandler.postVideoForStreaming(this.fileBytes,
          this.fileLength, this.filePath);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        FutureBuilder<FeedbackVideoStreamer>(
            future: this.feedbackVideoStreamer,
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                return VideoPreview(feedbackVideoStreamer:snapshot.data);
              }
              if(this.clickUpload) {
                return CircularProgressIndicator();
              }
              return Text("");
            }
        ),
        SizedBox(height: 50,),
        Text(
            "Click on Pick Video to select video",
            style: TextStyle(fontSize: 18.0)
        ),
        RaisedButton(
          onPressed: uploadFile,
          child:Text("Upload from your computer")
        ),
        SizedBox(height: 50,),
        Text(
            "Get Feedback",
            style: TextStyle(fontSize: 18.0)
        ),
        RaisedButton(
            onPressed: ()=>getFeedback(context),
            child:Text("Upload from your computer")
        ),
      ],
    );
  }
}