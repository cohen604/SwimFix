import 'dart:html';
import 'dart:typed_data';
import 'package:client/Domain/ScreenArguments/ResearcherScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Web/Components/CardButton.dart';
import 'package:client/Web/Components/CircleButton.dart';
import 'package:client/Web/Components/MenuBar.dart';
import 'package:client/Web/Components/NumberButton.dart';
import 'package:client/Web/Components/TextButton.dart';
import 'package:client/Web/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:client/Screens/Screen.dart';
import 'package:flutter/scheduler.dart';


class WebResearcherScreen extends Screen {

  ResearcherScreenArguments args;
  WebResearcherScreen({this.args, Key key}) : super(key: key);

  @override
  _WebResearcherScreenState createState() => _WebResearcherScreenState();
}

class _WebResearcherScreenState extends State<WebResearcherScreen> {

  LogicManager _logicManger = LogicManager.getInstance();
  WebColors _webColors = new WebColors();
  ResearcherStep _step = ResearcherStep.Upload_Video;

  bool _hasVideo;
  File _video;

  bool _hasCsv;
  Uint8List _csvFileBytes;
  int _csvFileLength;
  String _csvFilePath;


  Widget buildTopSide(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: MenuBar(onlogout: onLogout,),
    );
  }

  void onLogout() {
    _logicManger.logout(this.widget.args.swimmer).then(
            (value) {
          if(value) {
            this.setState(() {
              SchedulerBinding.instance.addPostFrameCallback((_) {
                Navigator.pushNamed(context, '/login');
              });
            });
          }
          else {
            showDialog(
                context: context,
                builder: (_) => AlertDialog(
                  content: Text('Cant Logout, Please Try again later',
                    textAlign: TextAlign.center,),
                )
            );
          }
        }
    );
  }

  Function onClick(String path) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, path);
        });
      });
    };
  }

  void uploadVideoWeb() async {
    InputElement uploadInput = FileUploadInputElement();
    uploadInput.multiple = false;
    uploadInput.draggable = true;
    uploadInput.accept = 'video/*';
    uploadInput.click();
    document.body.append(uploadInput);
    uploadInput.onChange.listen((e) {
      var files = uploadInput.files;
      _video = files[0];
      // var filePath = file.name;
      // final reader = new FileReader();
      // reader.readAsDataUrl(file.slice(0, file.size, file.type));
      // reader.onLoadEnd.listen((e) {
      //   var bytes = Base64Decoder().convert(reader.result.toString().split(",").last);
      //   setState(() {
      //     _videoFileBytes = bytes;
      //     _videoFileLength = this.fileBytes.length;
      //     this.filePath = filePath;
      //     print('File path: ' + filePath);
      //     print('File size: ' + file.size.toString());
      //   });
      // });
    });
    uploadInput.remove();
  }

  Widget buildUploadVideoButton(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        // margin: EdgeInsets.all(30.0),
        child: NumberButton(
          number: 1,
          title: 'Upload Video',
          background: _webColors.getBackgroundForI6(),
          selected: this._step == ResearcherStep.Upload_Video,
          selectedColor: _webColors.getBackgroundForI1(),
          onClick: null,
          fontSize: 21,
          flex: 1
        ),
      ),
    );
  }

  Widget buildUploadCSVButton(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        // margin: EdgeInsets.all(30.0),
        child: NumberButton(
          number: 2,
          title: 'Upload Labels',
          background: _webColors.getBackgroundForI6(),
          selected: this._step == ResearcherStep.Upload_Csv,
          selectedColor: _webColors.getBackgroundForI1(),
          onClick: null,
          fontSize: 21,
          flex: 1
        ),
      ),
    );
  }

  Widget buildGetReportButton(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        // margin: EdgeInsets.all(10.0),
        child: NumberButton(
          number: 3,
          title: 'Get Report',
          background: _webColors.getBackgroundForI6(),
          selected: this._step == ResearcherStep.Submit,
          selectedColor: _webColors.getBackgroundForI1(),
          onClick: null,
          fontSize: 21,
          flex: 1
        ),
      ),
    );
  }

  Widget buildViewResultsButton(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        // margin: EdgeInsets.all(10.0),
        child: NumberButton(
            number: 4,
            title: 'View',
            background: _webColors.getBackgroundForI7(),
            selected: this._step == ResearcherStep.View,
            selectedColor: _webColors.getBackgroundForI1(),
            onClick: null,
            fontSize: 21,
            flex: 1
        ),
      ),
    );
  }

  Widget buildShowResults(BuildContext context, int flex) {
    return Flexible(
        flex: flex,
        fit: FlexFit.tight,
        child: Container(

        )
    );
  }

  Widget buildTitle(BuildContext context, int flex, String title) {
    return Flexible(
      flex: flex,
      child: Align(
        alignment: Alignment.topLeft,
        child: Text(title,
            style: TextStyle(
                fontSize: 32 * MediaQuery.of(context).textScaleFactor,
                color: Colors.black87,
                fontWeight: FontWeight.normal,
                decoration: TextDecoration.none
            )
        ),
      ),
    );
  }

  Widget buildDescription(BuildContext context, int flex, String description) {
    return Flexible(
      flex: flex,
      child: Align(
        alignment: Alignment.topLeft,
        child: Text(description,
            style: TextStyle(
                fontSize: 24 * MediaQuery.of(context).textScaleFactor,
                color: Colors.black54,
                fontWeight: FontWeight.normal,
                decoration: TextDecoration.none
            )
        ),
      ),
    );
  }

  Widget buildUploadVideo(BuildContext context) {
    return Column(
      children: [
        Flexible(
          flex: 1,
          child: Container(
              margin: EdgeInsets.all(20.0),
              child: Column(
                children: [
                  buildTitle(context, 1, 'Upload Video'),
                  buildDescription(context, 1, 'Select a swimming video in any format'),
                  Align(
                    alignment: Alignment.topLeft,
                    child: OutlineButton(
                      child: Text('Select'),
                      hoverColor: _webColors.getBackgroundForI3(),
                    ),
                  )
                ],
              )
          ),
        ),
        Flexible(
          flex: 3,
          child: Container(
            child: OutlineButton(
              child: Text('Test'),
            ),
          ),
        )
      ],
    );
  }

  Widget buildSecondStep(BuildContext context) {
    return Text('2');
  }

  Widget buildThridStep(BuildContext context) {
    return Text('3');
  }

  Widget buildFourthStep(BuildContext context) {
    return Text('4');
  }

  Widget buildStepsMap(BuildContext context) {
    return Container(
      color: _webColors.getBackgroundForI6(),
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Column(
        children: [
          buildUploadVideoButton(context, 1),
          buildUploadCSVButton(context, 1),
          buildGetReportButton(context, 1),
          buildViewResultsButton(context, 1),
          Flexible(
            flex: 5,
            child: Container(),
          )
        ],
      ),
    );
  }

  Widget buildCuttentStep(BuildContext context, int flex) {
    Widget child;
    if(_step == ResearcherStep.Upload_Video) {
      child = buildUploadVideo(context);
    }
    else if(_step == ResearcherStep.Upload_Csv) {
      child = buildSecondStep(context);
    }
    else if(_step == ResearcherStep.Submit) {
      child = buildThridStep(context);
    }
    else if(_step == ResearcherStep.View)  {
      child = buildFourthStep(context);
    }
    return  Flexible(
        flex: flex,
        fit: FlexFit.tight,
        child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            color: _webColors.getBackgroundForI6(),
            child: child
        )
    );
  }

  Widget buildBottomSide(BuildContext context, int flex) {
    return Flexible(
        flex: flex,
        fit: FlexFit.tight,
        child: Row(
          children: [
            Flexible(
              flex: 1,
              fit: FlexFit.tight,
              child: buildStepsMap(context)
            ),
            buildCuttentStep(context, 4),
          ],
        )
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
        margin: EdgeInsets.only(top: 20.0),
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Column(
          children: [
            buildTopSide(context, 1),
            buildBottomSide(context, 10)
          ],
        ),
      ),
    );
  }

}

enum ResearcherStep {
  Upload_Video,
  Upload_Csv,
  Submit,
  View,
}