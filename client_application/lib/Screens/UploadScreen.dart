import 'dart:io';
import 'dart:typed_data';
import 'package:client_application/Components/AvatarChild.dart';
import 'package:client_application/Components/AvatarTitle.dart';
import 'package:client_application/Components/MediaPlayer.dart';
import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Domain/Video/FeedBackVideoStreamer.dart';
import 'package:client_application/Screens/Arguments/UploadScreenArguments.dart';
import 'package:client_application/Screens/ColorsHolder.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import 'Drawers/BasicDrawer.dart';

class UploadScreen extends StatefulWidget {

  UploadScreenArguments arguments;

  UploadScreen(this.arguments,{Key key}) : super(key: key);

  @override
  _UploadScreenState createState() => _UploadScreenState();
}

class _UploadScreenState extends State<UploadScreen> {

  ColorsHolder _colorsHolder = new ColorsHolder();
  UploadState _state = UploadState.Select;
  PlatformFile _video;
  FeedbackVideoStreamer _streamer;

  void onUpload() async{
    FilePickerResult result = await FilePicker.platform.pickFiles(
      type: FileType.video,
      allowMultiple: false,
    );
    if(result != null) {
      PlatformFile file = result.files.single;
      //TODO add path name validation and video type
      //print(file.path);
      //print(file.name);
      setState(() {
        _video = file;
        _state = UploadState.View;
      });
    }
  }

  void onRemove() {
    setState(() {
      _video = null;
      _state = UploadState.Select;
    });
  }

  void onViewNext() {
    setState(() {
      _state = UploadState.Submit;
    });
    LogicManager logicManager = LogicManager.getInstance();
    int length = _video.size;
    Uint8List bytes = new File(_video.path).readAsBytesSync();
    String path = _video.path;
    // print(length);
    // print(bytes);
    // print(path);
    Swimmer swimmer = this.widget.arguments.appUser.swimmer;
    logicManager.postVideoForStreaming(bytes, length, path, swimmer)
      .then((FeedbackVideoStreamer streamer) {
        if(streamer!=null) {
          setState(() {
            _state = UploadState.Feedback;
            _streamer = streamer;
          });
        }
        else {
          setState(() {
            _state = UploadState.View;
          });
          showDialog(
            context: context,
            builder: (BuildContext context) {
              return AlertDialog(
                title: Text('Something Broken'),
                content: Text('Maybe your video is corrupted or the server are down.\n'
                    'For more information please content help@swimanalytics.com' ),
              );
            }
          );
        }
      }
    );
  }

  Widget buildTitle(BuildContext context, String title) {
    return Align(
      alignment: Alignment.topLeft,
      child: Text(title,
        style: TextStyle(
          fontSize: 24 * MediaQuery.of(context).textScaleFactor,
          color: Colors.black,
          fontWeight: FontWeight.normal,
          decoration: TextDecoration.none,
        ),
      ),
    );
  }

  Widget buildDes(BuildContext context, String des) {
    return Align(
      alignment: Alignment.topLeft,
      child: Text(des,
        style: TextStyle(
          fontSize: 18 * MediaQuery.of(context).textScaleFactor,
          color: Colors.grey,
          fontWeight: FontWeight.normal,
          decoration: TextDecoration.none,
        ),
      ),
    );
  }

  void onClickSelect(BuildContext context) {
    Function onYes = () => setState(() {
      _state = UploadState.Select;
    });
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Video warning'),
          content: Text('Do you want to upload a new Video?'),
          actions: [
            TextButton(
              onPressed: ()=>Navigator.pop(context),
              child: Text("No")
            ),
            TextButton(
              onPressed: () {
                Navigator.pop(context);
                onYes();
              },
              child: Text("Yes")
            ),
          ],
        );
      }
    );
  }

  Widget buildSelect(BuildContext context) {
    Function isSelected = () => _state.index >= UploadState.Select.index;
    String title = '1';
    String des = 'Select';
    if(_state == UploadState.Select) {
      return AvatarChild(
        title,
        isSelected,
        des,
        _colorsHolder.getBackgroundForI1(),
        _colorsHolder.getBackgroundForI2(),
        child: Container(
          width: MediaQuery.of(context).size.width,
          // height: MediaQuery.of(context).size.height / 2,
          margin: EdgeInsets.all(5),
          child: Card(
            child: Container(
              padding: EdgeInsets.all(10),
              child: Column(
                children: [
                  buildTitle(context, 'Select Video'),
                  buildDes(context, 'You can select any video format to upload'),
                  SizedBox(height: 10,),
                  Container(
                    width: MediaQuery.of(context).size.width,
                    child: ElevatedButton(
                      onPressed: onUpload,
                      child: Text('Upload',
                        style: TextStyle(
                          fontSize: 16 * MediaQuery.of(context).textScaleFactor,
                        ),
                      )
                    ),
                  ),
                ]
              ),
            ),
          )
        )
      );
    }
    return AvatarTitle(
        title,
        isSelected,
        des,
        isSelected() ? onClickSelect : null,
        _colorsHolder.getBackgroundForI1(),
        _colorsHolder.getBackgroundForI2()
    );
  }

  Widget buildView(BuildContext context) {
    Function isSelected = () => _state.index >= UploadState.View.index;
    Function onClick = (BuildContext context) => setState(() {
      _state = UploadState.View;
    });
    String title = '2';
    String des = 'View';
    if(_state == UploadState.View) {
      return AvatarChild(
          title,
          isSelected,
          des,
          _colorsHolder.getBackgroundForI1(),
          _colorsHolder.getBackgroundForI2(),
          child: Container(
              width: MediaQuery.of(context).size.width,
              // height: MediaQuery.of(context).size.height / 2,
              margin: EdgeInsets.all(5),
              child: Card(
                child: Container(
                  padding: EdgeInsets.all(10),
                  child: Column(
                      children: [
                        buildTitle(context, 'Video'),
                        SizedBox(height: 10,),
                        buildDes(context, '${_video.path.substring(_video.path.lastIndexOf('/'))}'),
                        buildDes(context, 'Type: ${_video.path.substring(_video.path.lastIndexOf('.'))}'),
                        buildDes(context, 'Size: ${_video.size}'),
                        SizedBox(height: 10,),
                        Align(
                          alignment: Alignment.bottomRight,
                          child: Wrap(
                            children: [
                              TextButton(
                                onPressed: onRemove,
                                child: Text('Remove',
                                  style: TextStyle(
                                    fontSize: 16 * MediaQuery.of(context).textScaleFactor,
                                  ),
                                )
                              ),
                              ElevatedButton(
                                onPressed: onViewNext,
                                child: Text('Next',
                                  style: TextStyle(
                                    fontSize: 16 * MediaQuery.of(context).textScaleFactor,
                                  ),
                                )
                              ),
                            ],
                          ),
                        )
                      ]
                  ),
                ),
              )
          )
      );
    }
    return AvatarTitle(
        title,
        isSelected,
        des,
        isSelected() ? onClick : null,
        _colorsHolder.getBackgroundForI1(),
        _colorsHolder.getBackgroundForI2()
    );
  }

  Widget buildSubmit(BuildContext context) {
    Function isSelected = () => _state.index >= UploadState.Submit.index;
    Function onClick = null;
    String title = '3';
    String des = 'Submit';
    if(_state == UploadState.Submit) {
      return AvatarChild(
          title,
          isSelected,
          des,
          _colorsHolder.getBackgroundForI1(),
          _colorsHolder.getBackgroundForI2(),
          child: Container(
              width: MediaQuery.of(context).size.width,
              // height: MediaQuery.of(context).size.height / 2,
              margin: EdgeInsets.all(5),
              child: Card(
                child: Container(
                  padding: EdgeInsets.only(
                      top: 20,
                      bottom: 20),
                  child: Column(
                    children: [
                      Center(
                          child: CircularProgressIndicator()
                      ),
                      SizedBox(height: 10,),
                      Center(
                          child: Text('Analyze swimming video',
                            style: TextStyle(
                              fontSize: 18 * MediaQuery.of(context).textScaleFactor,
                            ),
                          )
                      )
                    ],
                  )
                ),
              )
          )
      );
    }
    return AvatarTitle(
        title,
        isSelected,
        des,
        isSelected() ? onClick : null,
        _colorsHolder.getBackgroundForI1(),
        _colorsHolder.getBackgroundForI2()
    );
  }

  Widget buildFeedback(BuildContext context) {
    Function isSelected = () => _state.index >= UploadState.Feedback.index;
    String title = '4';
    String des = 'Feedback';
    if(_state == UploadState.Feedback) {
      return AvatarChild(
        title,
        isSelected,
        des,
        _colorsHolder.getBackgroundForI1(),
        _colorsHolder.getBackgroundForI2(),
        child: Container(
          width: MediaQuery.of(context).size.width,
          // height: MediaQuery.of(context).size.height / 2,
          margin: EdgeInsets.all(5),
          child: Card(
            child: Container(
              padding: EdgeInsets.all(10),
              child: MediaPlayer(_streamer),
            )
          ),
        )
      );
    }
    return AvatarTitle(
        title,
        isSelected,
        des,
        null,
        _colorsHolder.getBackgroundForI1(),
        _colorsHolder.getBackgroundForI2()
    );
  }

  Widget buildPipeLine(BuildContext context) {
    return Column(
      // direction: Axis.vertical,
      // spacing: 10,
      children: [
        buildSelect(context),
        buildView(context),
        buildSubmit(context),
        buildFeedback(context),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: Text("Upload Swimming Video"),
        ),
        drawer: BasicDrawer(
            this.widget.arguments.appUser
        ),
        body: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          color: _colorsHolder.getBackgroundForI6(),
          margin: EdgeInsets.all(5),
          child: SingleChildScrollView(
            child: buildPipeLine(context)
          ),
        ),
      ),
    );
  }

}

enum UploadState {
  Select,
  View,
  Submit,
  Feedback
}