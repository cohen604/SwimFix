import 'dart:convert';
import 'dart:html';
import 'dart:typed_data';
import 'package:chewie/chewie.dart';
import 'package:client/Components/Buttons/NumberButton.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:video_player/video_player.dart';
import 'Arguments/UploadScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

import '../Holders/WebColors.dart';


class WebUploadScreen extends StatefulWidget {

  UploadScreenArguments args;
  WebUploadScreen({this.args, Key key}) : super(key: key);

  @override
  _WebUploadScreenState createState() => _WebUploadScreenState();
}

class _WebUploadScreenState extends State<WebUploadScreen> {

  LogicManager _logicManager;
  WebColors _webColors;
  AssetsHolder _assetsHolder;
  UploadStep _step;

  bool _hasVideo;
  File _video;

  bool _hasFeedback;
  FeedBackLink _feedbackLink;
  VideoPlayerController _controller;
  ChewieController _chewieController;

  _WebUploadScreenState() {
    _logicManager = LogicManager.getInstance();
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _step = UploadStep.Upload;
    _hasVideo = false;
    _hasFeedback = false;
  }

  @override
  void dispose() {
    super.dispose();
    if(_controller!=null) {
      _controller.dispose();
    }
  }

  void onLogout() {
    _logicManager.logout(this.widget.args.user.swimmer).then(
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

  void removeSelectedVideo() {
    if(_hasVideo) {
      this.setState(() {
        _hasVideo = false;
        _video = null;
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

  Future<bool> setController() async {
    String url = _logicManager.getStreamUrl() + _feedbackLink.getPath();
    _controller = VideoPlayerController.network(url);
    await _controller.initialize();
    _controller.play();

    _chewieController = ChewieController(
      videoPlayerController: _controller,
      // aspectRatio: 16 / 9,
      autoPlay: true,
      looping: false,
      //note: this muse be false cause chewiew having problem in full screen
      allowFullScreen: false,
      fullScreenByDefault: false,
      allowMuting: false,
    );
    //DO NOT DELETE THIS!!
    // setState(() {});
    return true;
  }

  void postVideo() {
    readFile(_video, (Uint8List videoBytes) {
      _logicManager.postVideoForStreaming(
          videoBytes,
          videoBytes.length,
          _video.name,
          this.widget.args.user.swimmer).then((feedbackLink) {
            if(feedbackLink != null) {
              _feedbackLink = feedbackLink;
              setController().then((bool value) {
                this.setState(() {
                  _hasFeedback = true;
                  _step = UploadStep.View;
                });
              });
            }
            else {
              this.setState(() {
                _step = UploadStep.Error;
              });
            }
      });
    });
  }

  void nextState() {
    if(_step == UploadStep.Upload) {
      this.setState(() {
        postVideo();
        _step = UploadStep.WaitingResults;
      });
    }
    else if(_step == UploadStep.WaitingResults)  {
      this.setState(() {
        _step = UploadStep.View;
      });
    }
  }

  void uploadVideoFunctionButton() {
    this.setState(() {
      _step = UploadStep.Upload;
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
          selected: _step.index >= UploadStep.Upload.index,
          selectedColor: _webColors.getBackgroundForI1(),
          onClick: _step.index >= UploadStep.Upload.index?
            uploadVideoFunctionButton: null,
          fontSize: 21,
          flex: 1
        ),
      ),
    );
  }

  void viewFunctionButton() {
    this.setState(() {
        _step = UploadStep.WaitingResults;
      });
  }

  Widget buildViewResultsButton(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        // margin: EdgeInsets.all(10.0),
        child: NumberButton(
            number: 2,
            title: 'View',
            background: _webColors.getBackgroundForI8(),
            selected: _step.index >= UploadStep.WaitingResults.index,
            selectedColor: _webColors.getBackgroundForI1(),
            onClick: _step.index >= UploadStep.WaitingResults.index?
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

  Widget buildWaitingResultStep(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(10.0),
      child: Center(
        child: CircularProgressIndicator(),
      ),
    );
  }

  Widget buildViewStep(BuildContext context) {
    return Material(
      child: SingleChildScrollView(
        child: AspectRatio(
          aspectRatio: 16 / 9,
          child: Chewie(
            controller: _chewieController,
          ),
        ),
      ),
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
              'Maybe the swimming video isn\'t correct or the servers are down.\n'
              'For more information contact swimAnalytics@gmail.com',
            createFlex: false,
            fontSize: 20,),
        ],
      )
    );
  }

  Widget buildStepsMap(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Column(
        children: [
          buildUploadVideoButton(context, 1),
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
    if(_step == UploadStep.Upload) {
      child = buildVideoStep(context);
    }
    else if(_step == UploadStep.Error) {
      child = buildErrorStep(context);
    }
    else if(_step == UploadStep.WaitingResults)  {
      child = buildWaitingResultStep(context);
    }
    else if(_step == UploadStep.View) {
      child = buildViewStep(context);
    }
    return  Flexible(
        flex: flex,
        fit: FlexFit.tight,
        child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            child: child
        )
    );
  }

  Widget buildBottomSide(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(10.0),
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getBackGroundImage()),
          fit: BoxFit.fill,
        ),
      ),
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

enum UploadStep {
  Upload,
  Error,
  WaitingResults,
  View,
}