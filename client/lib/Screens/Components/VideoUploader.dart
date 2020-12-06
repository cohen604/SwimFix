import 'dart:convert';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:universal_html/prefer_universal/html.dart' as html;
import 'dart:io';
import 'dart:typed_data';
import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:client/Domain/FeedbackVideo.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

import 'TitleButton.dart';
import 'VideoPreview.dart';

class VideoUploader extends StatefulWidget {

  VideoUploader({Key key}) : super(key: key);

  @override
  _VideoUploaderState createState() => _VideoUploaderState();
}

class _VideoUploaderState extends State<VideoUploader> {

  Uint8List fileBytes;
  int fileLength;
  String filePath;
  bool clickUpload = false;
  Future<FeedbackVideo> feedbackVideo;
  Future<FeedbackVideoStreamer> feedbackVideoStreamer;

  void uploadFileWeb() async {
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

  void uploadVideoMobileGallery() async {
    // var picker = ImagePicker();
    // PickedFile pickedFile = await picker.getVideo(source: ImageSource.gallery);
    // var file = File(pickedFile.path);
    FilePickerResult result = await FilePicker.platform.pickFiles();
    if(result != null) {
      File file = File(result.files.single.path);
      setState(() {
        this.fileBytes = file.readAsBytesSync();
        this.fileLength = file.lengthSync();
        this.filePath = file.path;
      });
    }
  }

  void uploadVideoMobileCamera() async {
    // var picker = ImagePicker();
    // PickedFile pickedFile = await picker.getVideo(source: ImageSource.camera);
    // var file = File(pickedFile.path);
    FilePickerResult result = await FilePicker.platform.pickFiles();
    if(result != null) {
      File file = File(result.files.single.path);
      setState(() {
        this.fileBytes = file.readAsBytesSync();
        this.fileLength = file.lengthSync();
        this.filePath = file.path;
      });
    }
  }

  /// The function call upload video of mobile or web
  /// @param bool flag - if true this goes to camera, other wise gallary
  void uploadVideo({flag:false}) {
    if (kIsWeb) {
      uploadFileWeb();
    }
    else {
      if(flag) {
        uploadVideoMobileCamera();
      }
      else {
        uploadVideoMobileGallery();
      }
    }
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
  Widget buildMobile(BuildContext context) {
    return Column(
      children: [
        TitleButton(
            title:"Pick Video from your mobile device",
            buttonText: "Upload",
            onPress: uploadVideo
        ),
        SizedBox(height: 10,),
        buildSelectedFile(context),
        SizedBox(height: 10,),
        TitleButton(
            title:"Take a video from your camera",
            buttonText: "Recording",
            onPress: ()=>uploadVideo(flag:true),
        ),
        SizedBox(height: 10,),
        TitleButton(
            title:"Submit video to SwimFix for feedback",
            buttonText: "Submit",
            onPress: ()=>getFeedback(context)
        ),
        SizedBox(height: 20,),
        buildVideoPreview(context),

      ],
    );
  }

  // Widget that we used to use.
  // the feedback is on the right side and not in the bottom
  Widget buildWeb(BuildContext context) {
    return Row(
      children: [
        SizedBox(height: 50,),
        Column(
          children: [
            TitleButton(
                title:"Pick Video from your computer",
                buttonText: "Upload",
                onPress: uploadVideo
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
  
  @override
  Widget build(BuildContext context) {
    if(kIsWeb) {
      return buildWeb(context);
    }
    return buildMobile(context);
  }
}