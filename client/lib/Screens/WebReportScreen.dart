import 'dart:convert';
import 'dart:html';
import 'package:client/Domain/Files/FileDonwloaded.dart';
import 'package:client/Domain/Users/ResearcherReport.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'Arguments/ReportScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Components/MenuBar.dart';
import 'package:client/Components/NumberButton.dart';
import 'package:client/Screens/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';


class WebReportScreen extends StatefulWidget {

  ReportScreenArguments args;
  WebReportScreen({this.args, Key key}) : super(key: key);

  @override
  _WebReportScreenState createState() => _WebReportScreenState();
}

class _WebReportScreenState extends State<WebReportScreen> {

  LogicManager _logicManager = LogicManager.getInstance();
  WebColors _webColors = new WebColors();
  ResearcherStep _step = ResearcherStep.Upload_Video;

  bool _hasVideo = false;
  File _video;

  bool _hasLabels = false;
  File _labels;

  bool _hasResults = false;
  ResearcherReport _report;

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
    reader.readAsDataUrl(file.slice(0, file.size, file.type));
    reader.onLoadEnd.listen((e) {
      var bytes = reader.result;
      // print('before');
      // print(bytes.toString());
      bytes = Base64Decoder().convert(reader.result.toString().split(",").last);
      callback(bytes);
    });
  }

  void postVideoWithCsv(videoBytes) {
    readFile(_labels, (labelsBytes) {
      _logicManager.postVideoAndCsvForAnalyze(
          _video.name,
          videoBytes,
          _labels.name,
          labelsBytes,
          this.widget.args.user.swimmer)
          .then((result) {
        if (result != null) {
          this.setState(() {
            _hasResults = true;
            _report = result;
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
  }

  void postVideoWithOutCsv(videoBytes) {
    _logicManager.postVideoAndCsvForAnalyze(
        _video.name,
        videoBytes,
        null,
        null,
        this.widget.args.user.swimmer)
        .then((result) {
      if (result != null) {
        this.setState(() {
          _hasResults = true;
          _report = result;
          nextState();
        });
      }
      else {
        this.setState(() {
          _step = ResearcherStep.Error;
        });
      }
    });
  }

  void postVideoAndCsv() {
    readFile(_video, (videoBytes) {
      if(_labels!=null) {
        postVideoWithCsv(videoBytes);
      }
      else {
        postVideoWithOutCsv(videoBytes);
      }
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

  void uploadLabelsFunctionButton() {
    this.setState(() {
      _step = ResearcherStep.Upload_Csv;
    });
  }

  void getReportFunctionButton() {
    this.setState(() {
      _step = ResearcherStep.Submit;
    });
  }

  void viewFunctionButton() {
    this.setState(() {
      _step = ResearcherStep.WaitingResults;
    });
  }

  void onDownloadLabelsExampleFile() {
    window.open('/assets/files/LabelsExample.csv', "csv");
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

  Widget buildNoFileSelected(BuildContext context, {String des}) {
    return Container(
      padding: EdgeInsets.all(10.0),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          des == null ? Container() : buildDescription(context, des),
          Align(
            alignment: Alignment.topLeft,
            child: Card(
              child: Container(
                padding: EdgeInsets.all(20.0),
                child: Text('No file selected',
                  style: TextStyle(
                    fontSize: 18,
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget buildSelectedFile(BuildContext context, File file,
    {int flex = 1, bool createFlex = true, String des}) {
    Widget child;
    if(file == null) {
      return buildNoFileSelected(context, des:des);
    }
    else {
      return Container(
        padding: EdgeInsets.all(10.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            des == null ? Container() : buildDescription(context, des),
            Card(
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
                    buildDescription(
                        context, 'Created : ${file.lastModifiedDate}',
                        fontSize: 19,
                        createFlex: false),
                  ],
                ),
              ),
            ),
          ],
        ),
      );
    }
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

  Widget buildLabelsExampleFile(BuildContext context) {
    //onDownloadLabelsExampleFile
    return Container(
      width: MediaQuery.of(context).size.width,
      child: Image.asset(
        'images/csv_example.png',
        height: 140.0,
        fit: BoxFit.contain,
      ),
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
                    ' Left Shoulder, Left Elbow, Left Wrist.',
                    fontSize: 20,
                    createFlex: false),
                // buildLabelsExampleFile(context),
                SizedBox(height: 5.0,),
                Align(
                  alignment: Alignment.topLeft,
                  child: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      buildTextButton(context, 'Example labels Csv',
                          onDownloadLabelsExampleFile),
                      SizedBox(width: 10,),
                      buildTextButton(context, 'Select', uploadLabelsWeb),
                      SizedBox(width: 10,),
                      buildTextButton(context, 'Skip', nextState),
                    ],
                  ),
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
      mainAxisSize: MainAxisSize.min,
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
        buildSelectedFile(context, _video, des:'Video'),
        buildSelectedFile(context, _labels, des:'Labels'),
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

  void onFileClick(String fileLink) {
    Swimmer swimmer = this.widget.args.user.swimmer;
    _logicManager.getFileForDownload(
        swimmer.uid,
        swimmer.email,
        swimmer.name,
      fileLink,
    ).then((FileDownloaded fileDownloaded) {
          String content = base64Encode(fileDownloaded.bytes);
          AnchorElement(
              href: "data:application/octet-stream;charset=utf-16le;base64,$content")
            ..setAttribute("download", fileDownloaded.fileName)
            ..click();
        }
    );
  }

  void onFeedbackClick() {
    if(_report.videoLink!=null) {
      onFileClick(_report.videoLink);
    }
  }

  void onCsvClick() {
    if(_report.csvLink!=null) {
      onFileClick(_report.csvLink);
    }
  }

  void onPdfClick() {
    if(_report.pdfLink!=null) {
      onFileClick(_report.pdfLink);
    }
  }

  Widget buildViewStep(BuildContext context) {
    return Container(
      margin: EdgeInsets.all(20.0),
      child: Column(
        children: [
          buildTitle(context, 'View report', createFlex: false),
          SizedBox(height: 10.0,),
          Align(
            alignment: Alignment.topLeft,
            child: Container(
              padding: EdgeInsets.only(left:10),
              child: buildTextButton(context, 'Feedback', onFeedbackClick),
            ),
          ),
          SizedBox(height: 5.0,),
          Align(
              alignment: Alignment.topLeft,
              child: buildTextButton(context, 'Csv', onCsvClick)
          ),
          SizedBox(height: 5.0,),
          Align(
              alignment: Alignment.topLeft,
              child: buildTextButton(context, 'Pdf', onPdfClick)
          ),
        ],
      )
    );
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

  Widget buildBottomSide(BuildContext context) {
    return Container(
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
            MenuBar(user: this.widget.args.user,),
            Flexible(
                child: buildBottomSide(context)
            ),
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