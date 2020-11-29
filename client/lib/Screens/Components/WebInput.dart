import 'dart:convert';
import 'dart:html' as html;
import 'dart:typed_data';
import 'package:client/Services/connectionHandler.dart';
import 'package:flutter/material.dart';

class WebInput extends StatefulWidget {

  WebInput({Key key}) : super(key: key);

  @override
  _WebInputState createState() => _WebInputState();
}

class _WebInputState extends State<WebInput> {
  Uint8List fileBytes;
  int fileLength;
  String filePath;

  void uploadFile() {
    html.InputElement uploadInput = html.FileUploadInputElement();
    uploadInput.multiple = false;
    uploadInput.draggable = true;
    uploadInput.accept = 'image/*';
    uploadInput.click();
    html.document.body.append(uploadInput);
    uploadInput.onChange.listen((e) {
      var files = uploadInput.files;
      var file = files[0];
      var filePath = file.name;
      final reader = new html.FileReader();
      reader.onLoadEnd.listen((e) {
        setState(() {
          this.fileBytes = Base64Decoder().convert(reader.result.toString().split(",").last);
          this.fileLength = this.fileBytes.length;
          this.filePath = filePath;
        });
      });

    });
    uploadInput.remove();
  }

  void getFeedback() {
    ConnectionHandler connectionHandler = new ConnectionHandler("", "");
    connectionHandler.postWebFile("/upload", this.input);
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
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
            onPressed: getFeedback,
            child:Text("Upload from your computer")
        ),
      ],
    );
  }
}