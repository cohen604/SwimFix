import 'package:flutter/material.dart';

class MobileInput extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _MobileInputState();
}

class _MobileInputState extends State<MobileInput> {


  void uploadVideoFromGallery() {

  }

  void uploadVideoFromCamera() {

  }

  void getFeedBack() {

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
          onPressed: getFeedBack,
          child: Text("analyze video"),
        )
      ],
    );
    throw UnimplementedError();
  }

}