import 'dart:convert';
import 'dart:html';
import 'dart:typed_data';
import 'package:client/Domain/ScreenArguments/ResearcherScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Web/Components/CardButton.dart';
import 'package:client/Web/Components/CircleButton.dart';
import 'package:client/Web/Components/MenuBar.dart';
import 'package:client/Web/Components/MessagePopUp.dart';
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

  LogicManager _logicManager = LogicManager.getInstance();
  WebColors _webColors = new WebColors();
  ResearcherStep _step = ResearcherStep.Upload_Video;

  bool _hasVideo = false;
  File _video;

  bool _hasLabels = false;
  File _labels;

  bool _hasResults = false;

  Widget buildTopSide(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: MenuBar(swimmer: this.widget.args.swimmer,),
    );
  }

  void onLogout() {
    _logicManager.logout(this.widget.args.swimmer).then(
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

  void uploadFile(String fileType, Function updateFile) {
    InputElement uploadInput = FileUploadInputElement();
    uploadInput.multiple = false;
    uploadInput.draggable = true;
    uploadInput.accept = fileType;
    uploadInput.click();
    document.body.append(uploadInput);
    uploadInput.onChange.listen((e) {
      //var bytes = Base64Decoder().convert(uploadInput.result.toString().split(",").last)
      updateFile(uploadInput.files[0]);
    });
    uploadInput.remove();
  }

  void uploadVideoWeb() {
    uploadFile('video/*',
        (File file) {
          this.setState(() {
            _video = file;
            _hasVideo = true;
          });
        }
    );
  }

  void uploadLabelsWeb() {
    uploadFile('.csv',
      (File file) {
          this.setState(() {
            _labels = file;
            _hasLabels = true;
          });
        }
    );
  }

  void removeSelectedVideo() {
    if(_hasVideo) {
      this.setState(() {
        _hasVideo = false;
        _video = null;
      });
    }
  }

  void removeSelectedLabels() {
    if(_hasLabels) {
      this.setState(() {
        _hasLabels = false;
        _labels = null;
      });
    }
  }

  void readFile(File file, Function callback) {
    final reader = new FileReader();
    reader.readAsDataUrl(_video.slice(0, _video.size, _video.type));
    reader.onLoadEnd.listen((e) {
      var bytes = Base64Decoder().convert(reader.result.toString().split(",").last);
      callback(bytes);
    });
  }

  void postVideoAndCsv() {
    readFile(_video, (videoBytes) {
      readFile(_labels, (labelsBytes) {
        _logicManager.postVideoAndCsvForAnalyze(
            _video.name,
            videoBytes,
            _labels.name,
            labelsBytes)
          .then( (result) {
            if(result!=null) {
              this.setState(() {
                _hasResults = true;
                nextState();
              });
            }
            else {
              this.setState(() {
                _step = ResearcherStep.Error;
              });
            }
          }
        );
      });
    });
    // _logicManager.postVideoAndCsvForAnalyze()
  }

  void nextState() {
    if(_step == ResearcherStep.Upload_Video) {
      this.setState(() {
        _step = ResearcherStep.Upload_Csv;
      });
    }
    else if(_step == ResearcherStep.Upload_Csv) {
      this.setState(() {
        _step = ResearcherStep.Submit;
      });
    }
    else if(_step == ResearcherStep.Submit) {
      postVideoAndCsv();
      this.setState(() {
        _step = ResearcherStep.WaitingResults;
      });
    }
    else if(_step == ResearcherStep.WaitingResults)  {
      this.setState(() {
        _step = ResearcherStep.View;
      });
    }
  }

  void uploadVideoFunctionButton() {
    this.setState(() {
      _step = ResearcherStep.Upload_Video;
    });
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
          background: _webColors.getBackgroundForI7(),
          selected: _step.index >= ResearcherStep.Upload_Video.index,
          selectedColor: _webColors.getBackgroundForI1(),
          onClick: _step.index >= ResearcherStep.Upload_Video.index?
            uploadVideoFunctionButton: null,
          fontSize: 21,
          flex: 1
        ),
      ),
    );
  }

  void uploadLabelsFunctionButton() {
    this.setState(() {
      _step = ResearcherStep.Upload_Csv;
    });
  }

  Widget buildUploadLabelsButton(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        // margin: EdgeInsets.all(30.0),
        child: NumberButton(
          number: 2,
          title: 'Upload Labels',
          background: _webColors.getBackgroundForI7(),
          selected: _step.index >= ResearcherStep.Upload_Csv.index,
          selectedColor: _webColors.getBackgroundForI1(),
          onClick: _step.index >= ResearcherStep.Upload_Csv.index ?
            uploadLabelsFunctionButton : null,
          fontSize: 21,
          flex: 1
        ),
      ),
    );
  }

  void getReportFunctionButton() {
    this.setState(() {
        _step = ResearcherStep.Submit;
      });
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
          background: _webColors.getBackgroundForI7(),
          selected: _step.index >= ResearcherStep.Submit.index,
          selectedColor: _webColors.getBackgroundForI1(),
          onClick: _step.index >= ResearcherStep.Submit.index?
              getReportFunctionButton : null,
          fontSize: 21,
          flex: 1
        ),
      ),
    );
  }

  void viewFunctionButton() {
    this.setState(() {
        _step = ResearcherStep.WaitingResults;
      });
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
            background: _webColors.getBackgroundForI8(),
            selected: _step.index >= ResearcherStep.WaitingResults.index,
            selectedColor: _webColors.getBackgroundForI1(),
            onClick: _step.index >= ResearcherStep.WaitingResults.index?
              viewFunctionButton : null,
            fontSize: 21,
            flex: 1
        ),
      ),
    );
  }

  Widget buildTitle(BuildContext context, String title,
      {int fontSize = 32, bool createFlex = true, int flex = 1}) {
    Widget child = Align(
      alignment: Alignment.topLeft,
      child: Text(title,
        style: TextStyle(
          fontSize: fontSize * MediaQuery.of(context).textScaleFactor,
          color: Colors.black87,
          fontWeight: FontWeight.normal,
          decoration: TextDecoration.none
        )
      )
    );
    if(createFlex) {
      return Flexible(
          flex: flex,
          child: child
      );
    }
    return child;
  }

  Widget buildDescription(BuildContext context, String description,
      {int fontSize = 24, bool createFlex = true, int flex = 1}) {
    Widget child = Align(
      alignment: Alignment.topLeft,
      child: Text(description,
          style: TextStyle(
              fontSize: fontSize * MediaQuery.of(context).textScaleFactor,
              color: Colors.black54,
              fontWeight: FontWeight.normal,
              decoration: TextDecoration.none
          )
      ),
    );
    if(createFlex) {
      return Flexible(
        flex:flex,
        child: child,
      );
    }
    return child;
  }

  Widget buildTextButton(BuildContext context, String title, Function onPress,
      {int fontSize = 22}) {
    return TextButton(
      onPressed: onPress,
      style: ButtonStyle(
        foregroundColor: MaterialStateColor.resolveWith((states) => _webColors.getBackgroundForI1()),
      ),
      child: Text(title,
        style: TextStyle(
          fontSize: fontSize * MediaQuery.of(context).textScaleFactor,
          fontWeight: FontWeight.normal,
          decoration: TextDecoration.none
        ),
      ),
    );
  }

  Widget buildElevatedButton(BuildContext context, String title, Function onPress) {
    return ElevatedButton(
      onPressed: onPress,
      style: ButtonStyle(
          backgroundColor: MaterialStateColor.resolveWith((states) => _webColors.getBackgroundForI2()),
      ),
      child: Container(
        width: 100,
        height: 40,
        child: Center(
          child: Text(title,
            style: TextStyle(
                fontSize: 18 * MediaQuery.of(context).textScaleFactor,
                color: Colors.white,
                fontWeight: FontWeight.bold,
                decoration: TextDecoration.none
            ),
          ),
        ),
      ),
    );
  }

  Widget buildDivider() {
    return Divider(
      color: Colors.grey,
      thickness: 1,
      height: 10,
      indent: 1,
      endIndent: 1,
    );
  }

  Widget buildSelectedFile(BuildContext context, File file,
    {int flex = 1, bool createFlex = true}) {
    Widget child = Container(
      padding: EdgeInsets.all(10.0),
      child: Card(
        child: Container(
          padding: EdgeInsets.all(10.0),
          child: Column(
            children: [
              buildTitle(context, file.name,
                fontSize: 23,
                createFlex: false,
              ),
              buildDivider(),
              buildDescription(context, 'Size: ${file.size}',
                  fontSize: 21,
                  createFlex: false),
              buildDescription(context, 'Created : ${file.lastModifiedDate}',
                  fontSize: 19,
                  createFlex: false),
            ],
          ),
        ),
      ),
    );
    if(createFlex) {
      return Flexible(
          flex: flex,
          fit: FlexFit.tight,
          child: child
      );
    }
    return child;
  }

  Widget buildVideoStep(BuildContext context) {
    return Column(
      children: [
        Container(
          margin: EdgeInsets.all(20.0),
          child: Column(
            children: [
              buildTitle(context, 'Upload Video', createFlex: false),
              SizedBox(height: 5.0,),
              buildDescription(context, 'Please select a swimming video in any video format.\nMake sure the video is not corrupted.',
                  createFlex: false),
              SizedBox(height: 5.0,),
              Align(
                alignment: Alignment.topLeft,
                child: buildTextButton(context, 'Select', uploadVideoWeb),
              ),
            ],
          )
        ),
        _hasVideo ? buildSelectedFile(context, _video, createFlex: false) : Container(),
        _hasVideo ? Container(
          margin: EdgeInsets.only(right: 10.0, left:10.0),
          child: Row(
              children: [
                Flexible(child: Container()),
                buildTextButton(context, 'Remove',
                  removeSelectedVideo,
                    fontSize: 20),
                SizedBox(width: 10,),
                buildElevatedButton(context, 'Next', nextState),
              ],
            ),
          )
            : Flexible( flex: 2, child: Container(),)
      ],
    );
  }

  Widget buildLabelsStep(BuildContext context) {
    return Column(
      children: [
        Container(
            margin: EdgeInsets.all(20.0),
            child: Column(
              children: [
                buildTitle(context, 'Upload Labels', createFlex: false),
                SizedBox(height: 5.0,),
                buildDescription(context, 'Please select a Csv file that contains the labels of the swimming video.'
                  '\nMake sure the csv contains the (x, y) values of:',
                  createFlex: false,
                  fontSize: 20,),
                buildDescription(context, 'Head, Right Shoulder, Right Elbow, Right Wrist,'
                    ' Left Shoulder, Left Elbow, Left Wrist.'
                    '\nExample for Csv:',
                    fontSize: 20,
                    createFlex: false),
                Container(
                  width: MediaQuery.of(context).size.width,
                  child: Image.asset(
                    'images/csv_example.png',
                    height: 140.0,
                    fit: BoxFit.contain,
                  ),
                ),
                SizedBox(height: 5.0,),
                Align(
                  alignment: Alignment.topLeft,
                  child: buildTextButton(context, 'Select', uploadLabelsWeb),
                ),
              ],
            )
        ),
        _hasLabels ? buildSelectedFile(context, _labels, createFlex: false) : Container(),
        _hasLabels ? Container(
          margin: EdgeInsets.only(right: 10.0, left:10.0),
          child: Row(
            children: [
              Flexible(child: Container()),
              buildTextButton(context, 'Remove',
                  removeSelectedLabels,
                  fontSize: 20),
              SizedBox(width: 10,),
              buildElevatedButton(context, 'Next', nextState),
            ],
          ),
        )
            : Flexible( flex: 2, child: Container(),)
      ],
    );
  }

  Widget buildSubmitStep(BuildContext context) {
    return Column(
      children: [
        Container(
            margin: EdgeInsets.all(20.0),
            child: Column(
              children: [
                buildTitle(context, 'Submit Data', createFlex: false),
                SizedBox(height: 5.0,),
                buildDescription(context, 'Verify that the selected files are correct',
                    createFlex: false),
                SizedBox(height: 5.0,),
              ],
            )
        ),
        Container(
          padding: EdgeInsets.only(top:5.0, right: 20.0 ,left: 20.0),
          child: Row(
            children: [
              buildDescription(context, 'Video'),
              buildDescription(context, 'Labels')
            ],
          ),
        ),
        Row(
          children: [
            buildSelectedFile(context, _video),
            buildSelectedFile(context, _labels),
          ],
        ),
        Container(
          margin: EdgeInsets.only(right: 10.0, left:10.0),
          child: Row(
            children: [
              Flexible(child: Container()),
              buildElevatedButton(context, 'Next', nextState),
            ],
          ),
        )
      ],
    );
  }

  Widget buildWaitingResultStep(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(10.0),
      child: Center(
        child: LinearProgressIndicator(),
      ),
    );
  }

  Widget buildViewStep(BuildContext context) {
      return Text('View Results');
  }

  Widget buildErrorStep(BuildContext context) {
    return Container(
      margin: EdgeInsets.all(20.0),
      child: Column(
        children: [
          buildTitle(context, 'Error', createFlex: false),
          SizedBox(height: 5.0,),
          buildDescription(context, 'Something is broken.\n'
              'Maybe the uploaded files aren\'t correct or the servers are down.\n'
              'For more information contact swimAnalytics@gmail.com',
            createFlex: false,
            fontSize: 20,),
        ],
      )
    );
  }

  Widget buildStepsMap(BuildContext context) {
    return Container(
      color: _webColors.getBackgroundForI7(),
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Column(
        children: [
          buildUploadVideoButton(context, 1),
          buildUploadLabelsButton(context, 1),
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

  Widget buildCurrentStep(BuildContext context, int flex) {
    Widget child;
    if(_step == ResearcherStep.Upload_Video) {
      child = buildVideoStep(context);
    }
    else if(_step == ResearcherStep.Upload_Csv) {
      child = buildLabelsStep(context);
    }
    else if(_step == ResearcherStep.Submit) {
      child = buildSubmitStep(context);
    }
    else if(_step == ResearcherStep.WaitingResults)  {
      child = buildWaitingResultStep(context);
    }
    else if(_step == ResearcherStep.View) {
      child = buildViewStep(context);
    }
    else if(_step == ResearcherStep.Error) {
      child = buildErrorStep(context);
    }
    return  Flexible(
        flex: flex,
        fit: FlexFit.tight,
        child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            color: _webColors.getBackgroundForI7(),
            child: child
        )
    );
  }

  Widget buildBottomSide(BuildContext context, int flex) {
    return Flexible(
        flex: flex,
        fit: FlexFit.tight,
        child: Container(
          padding: EdgeInsets.all(10.0),
          child: Row(
            children: [
              Flexible(
                flex: 1,
                fit: FlexFit.tight,
                child: buildStepsMap(context)
              ),
              buildCurrentStep(context, 4),
            ],
          ),
        )
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
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
  Error,
  Submit,
  WaitingResults,
  View,
}