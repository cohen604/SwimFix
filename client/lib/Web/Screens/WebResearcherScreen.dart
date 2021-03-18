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

  bool _hasVideo = false;
  File _video;

  bool _hasLabels = false;
  File _labels;

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
    // });
    // uploadInput.remove();
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
      this.setState(() {
        _step = ResearcherStep.View;
      });
    }
    else if(_step == ResearcherStep.View)  {

    }
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
          selected: this._step.index >= ResearcherStep.Upload_Video.index,
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
          background: _webColors.getBackgroundForI7(),
          selected: this._step.index >= ResearcherStep.Upload_Csv.index,
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
          background: _webColors.getBackgroundForI7(),
          selected: this._step.index >= ResearcherStep.Submit.index,
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
            background: _webColors.getBackgroundForI8(),
            selected: this._step.index >= ResearcherStep.View.index,
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

  Widget buildUploadVideo(BuildContext context) {
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

  Widget buildSecondStep(BuildContext context) {
    return Column(
      children: [
        Container(
            margin: EdgeInsets.all(20.0),
            child: Column(
              children: [
                buildTitle(context, 'Upload Labels', createFlex: false),
                SizedBox(height: 5.0,),
                buildDescription(context, 'Please select a Csv file that contains the labels of the swimming video.'
                    '\nMake sure the csv structure:'
                    '\nHead(x,y), Right Shoulder(x,y), Right Elbow(x,y), Right Wrist(x,y),'
                    'Left Shoulder(x,y), Left Elbow(x,y), Left Wrist(x,y)',
                    createFlex: false),
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

  Widget buildThirdStep(BuildContext context) {
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

  Widget buildFourthStep(BuildContext context) {
    return Center(
      child: LinearProgressIndicator(),
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
      child = buildThirdStep(context);
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
              buildCuttentStep(context, 4),
            ],
          ),
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