import 'package:client_application/Components/VideoUploader.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class UploadScreen extends StatefulWidget {

  UploadScreen({Key key}) : super(key: key);

  @override
  _UploadScreenState createState() => _UploadScreenState();
}

class _UploadScreenState extends State<UploadScreen> {

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: Text("Video Upload"),
        ),
        body: SingleChildScrollView(
          child: Center(
            child: Container(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                children: <Widget>[
                  VideoUploader(),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

}
