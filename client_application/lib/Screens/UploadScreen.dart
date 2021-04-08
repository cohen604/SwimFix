import 'dart:io';

import 'package:client_application/Components/Avatar.dart';
import 'package:client_application/Components/AvatarChild.dart';
import 'package:client_application/Components/AvatarTitle.dart';
import 'package:client_application/Components/VideoUploader.dart';
import 'package:client_application/Domain/Users/AppUser.dart';
import 'package:client_application/Screens/Arguments/UploadScreenArguments.dart';
import 'package:client_application/Screens/ColorsHolder.dart';
import 'package:file_picker/file_picker.dart';
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
  File _file;

  void nextState() {
    if(_state == UploadState.Select) {
      _state = UploadState.View;
    }
    else if(_state == UploadState.View) {
      _state = UploadState.Submit;
    }
    else if(_state == UploadState.Submit) {
      _state = UploadState.Result;
    }
  }

  void onUpload() async{
    // var picker = ImagePicker();
    // PickedFile pickedFile = await picker.getVideo(source: ImageSource.gallery);
    // var file = File(pickedFile.path);
    FilePickerResult result = await FilePicker.platform.pickFiles();
    if(result != null) {
      File file = File(result.files.single.path);
      setState(() {
        _file = file;
        nextState();
      });
    }
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

  Widget buildSelect(BuildContext context) {
    Function isSelected = () => _state.index >= UploadState.Select.index;
    Function onClick = (BuildContext context) => setState(() {
      _state = UploadState.Select;
    });
    String title = '1';
    String des = 'Select';
    if(_state == UploadState.Select) {
      return AvatarChild(
        title,
        isSelected,
        des,
        _colorsHolder.getBackgroundForI2(),
        _colorsHolder.getBackgroundForI3(),
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
                  ElevatedButton(
                    onPressed: onUpload,
                    child: Text('Upload')
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
        onClick,
        _colorsHolder.getBackgroundForI2(),
        _colorsHolder.getBackgroundForI3()
    );
  }

  Widget buildPipeLine(BuildContext context) {
    return Column(
      // direction: Axis.vertical,
      // spacing: 10,
      children: [
        buildSelect(context),
        Avatar(
            '2',
            ()=>_state.index >= UploadState.View.index,
            _colorsHolder.getBackgroundForI2(),
            _colorsHolder.getBackgroundForI3()
        ),
        Avatar(
            '3',
            ()=>_state.index >= UploadState.Submit.index,
            _colorsHolder.getBackgroundForI2(),
            _colorsHolder.getBackgroundForI3()
        ),
        Avatar(
            '4',
              ()=>_state.index >= UploadState.Result.index,
            _colorsHolder.getBackgroundForI2(),
            _colorsHolder.getBackgroundForI3()
        ),
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
  Result
}