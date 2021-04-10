import 'package:camera/camera.dart';
import 'package:client_application/Components/Avatar.dart';
import 'package:client_application/Domain/Concurrent/ConcurrentQueue.dart';
import 'package:client_application/Domain/Video/FeedBackVideoStreamer.dart';
import 'package:client_application/Domain/Video/VideoListImages.dart';
import 'package:client_application/Domain/Video/VideoWithoutFeedback.dart';
import 'package:client_application/Screens/ColorsHolder.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'Arguments/CameraScreenArguments.dart';
import 'Arguments/VideoScreenArguments.dart';
import 'Drawers/BasicDrawer.dart';

class CameraScreen extends StatefulWidget {

  CameraScreenArguments args;
  CameraScreen(this.args, {Key key}) : super(key: key);

  @override
  _CameraScreenState createState() => _CameraScreenState();
}

class _CameraScreenState extends State<CameraScreen> {

  LogicManager _logicManager;

  List<VideoWithoutFeedback> _poolWithNoFeedBack;
  List<CameraImage> _pool;

  int _thresholdInvalidImages;
  int _counterInvalidImages;
  int _thresholdFrames;


  _CameraScreenState() {
    _logicManager = LogicManager.getInstance();
    _poolWithNoFeedBack = new List();
    _pool = new List();
    _cameraController = null;
    // TODO must remember:  _thresholdFrames > _thresholdInvalidImages
    _thresholdFrames = 1;
    _thresholdInvalidImages = 0;
    _counterInvalidImages = 0;
  }

  //TODO
  VideoListImages createVideoListImageFromCameraImage(List<CameraImage> pool) {
    List<CameraImage> bytes = new List();
    bytes.addAll(pool);
    String name = 'pool${_poolWithNoFeedBack.length + 1}';
    return new VideoListImages(bytes, name);
  }

  //TODO
  /// The function handle an camera img and send him to the ml service
  /// param img - the camera img to predict
  void handleCameraImageBlue(CameraImage img) {
    bool valid = _logicManager.predictValidFrameBlue(img);
    if (valid) {
      _pool.add(img);
      _counterInvalidImages = 0;
    }
    else {
      _counterInvalidImages += 1;
      if(_counterInvalidImages > _thresholdInvalidImages && _pool.length <= _thresholdFrames){
        _pool = new List();
      }
      else if (_counterInvalidImages > _thresholdInvalidImages && _pool.length > _thresholdFrames) {
        VideoListImages videoListImages = createVideoListImageFromCameraImage(_pool);
        _poolWithNoFeedBack.add(videoListImages);
        _pool = new List();
      }
      else {
        _pool.add(img);
      }
    }
    print('pool length ${_pool.length}');
  }

  void startRecording() {
    _cameraController.startImageStream(handleCameraImageBlue); // handleCameraImage
  }

  void stopRecording() {
    _cameraController.stopImageStream();
    // maybe there is another last pool to get
    if(_pool.isNotEmpty && _pool.length > _thresholdFrames) {
      VideoListImages videoListImages = createVideoListImageFromCameraImage(_pool);
      _poolWithNoFeedBack.add(videoListImages);
      _pool = new List();
    }
    print('video pools found ${_poolWithNoFeedBack.length}');
    if(_poolWithNoFeedBack.isNotEmpty) {
      VideoWithoutFeedback firstPool = _poolWithNoFeedBack.removeAt(0);
      firstPool.getFeedbackVideo(_logicManager).then((feedbackLink) {
        print('got feedback for first camera pool video');
        List<FeedbackVideoStreamer> feedbacks = new List();
        feedbacks.add(feedbackLink);
        var args = new VideoScreenArguments(feedbacks, _poolWithNoFeedBack);
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, "/videos", arguments: args);
        });
      });
    }
    else {
      // TODO move to error screen
      //FOUND error no polls
    }
  }

  // New Code here

  ColorsHolder _colorsHolder = new ColorsHolder();
  ScreenStates _screenState = ScreenStates.Search;
  List<CameraDescription> _cameras;
  CameraController _cameraController;
  FilmStates _filmStates;
  AnimationController _animationController;

  @override
  void initState() {
    super.initState();
    availableCameras().then((cameras) async {
      if (cameras == null || cameras.length < 1) {
        setState(() {
          _screenState = ScreenStates.NoCameraFound;
        });
      }
      else {
        setState(() {
          _screenState = ScreenStates.Select;
          _cameras = cameras;
        });
      }
    });
  }

  @override
  void dispose() {
    _cameraController?.dispose();
    super.dispose();
  }

  void onCameraSelected(int index) {
    _cameraController = new CameraController(
      _cameras[index],
      ResolutionPreset.high,
    );
    _cameraController.initialize().then(
      (value) {
        setState(() {
          _screenState = ScreenStates.Film;
          _filmStates = FilmStates.Ready;
        });
      }
    );
    setState(() {
      _screenState = ScreenStates.ConnectingToCamera;
    });
  }

  void onCameraPause() {
    setState(() {
      _filmStates = FilmStates.Pause;
      _cameraController.pauseVideoRecording();
      //TODO
    });
  }

  void onCameraResume() {
    setState(() {
      _filmStates = FilmStates.Filming;
      _cameraController.resumeVideoRecording();
    });
  }

  void onCameraStop() {
    //play_arrow_outlined
    setState(() {
      _filmStates = FilmStates.Finished;
      _cameraController.stopVideoRecording();
      _screenState = ScreenStates.View;
    });
  }

  Widget buildSearchState(BuildContext context) {
    return  Container(
      margin: EdgeInsets.all(10.0),
      child: Column(
        children: [
          Card(
            child: Container(
              width: MediaQuery.of(context).size.width,
              padding: EdgeInsets.all(20.0),
              child: Column(
                children: [
                  CircularProgressIndicator(),
                  SizedBox(height: 10.0,),
                  Text('Searching camera devices'),
                ],
              ),
            ),
          ),
          Expanded(child: Container())
        ],
      ),
    );
  }

  Widget buildNoCameraState(BuildContext context) {
    return Container(
      margin: EdgeInsets.all(10.0),
      child: Column(
        children: [
          Card(
            child: Container(
              width: MediaQuery.of(context).size.width,
              padding: EdgeInsets.all(20.0),
              child: Center(
                  child: Text('No camera devices')
              ),
            ),
          ),
          Expanded(child: Container())
        ],
      ),
    );
  }

  String getCameraName(String name) {
    if(name == '0') {
      return 'Mobile camera';
    }
    else if(name == '1') {
      return 'Mobile camera';
    }
    return name;
  }

  String getCameraDirection(CameraLensDirection direction) {
    if(direction == CameraLensDirection.front) {
      return 'Camera direction : Front';
    }
    else if(direction == CameraLensDirection.back) {
      return 'Camera direction : Back';
    }
    else if(direction == CameraLensDirection.external) {
      return 'Camera direction : External';
    }
    return 'Camera direction : Unknown';
  }

  Widget buildCameraDeviceItem(BuildContext context, int index) {
    CameraDescription des = _cameras[index];
    return Card(
      child: ListTile(
        leading:  CircleAvatar(
          backgroundColor: Colors.grey.shade300,
          foregroundColor: Colors.black,
          child: Text('${index + 1}',
            style: TextStyle(
                fontWeight: FontWeight.bold,
            ),
          )
        ),
        title : Text('${getCameraName(des.name)}'),
        subtitle: Text('${getCameraDirection(des.lensDirection)}'),
        trailing: IconButton(
          color: _colorsHolder.getBackgroundForI1(),
          onPressed: () => onCameraSelected(index),
          icon: Icon(
            Icons.videocam,
            size: 30,
          ),
        ),
      ),
    );
  }

  Widget buildSelectState(BuildContext context) {
    return Container(
        child: Column(
          children: [
            Container(
              margin: EdgeInsets.all(10.0),
              child: Text('Devices',
                style: TextStyle(
                  fontSize: 24,
                ),
              ),
            ),
            Expanded(
              child: ListView.builder(
                itemBuilder: (_, index) {
                  return buildCameraDeviceItem(context, index);
                },
                itemCount: _cameras.length,
              ),
            ),
          ],
        ),
    );
  }

  Widget buildConnectingToCamera(BuildContext context) {
    return  Container(
      margin: EdgeInsets.all(10.0),
      child: Column(
        children: [
          Card(
            child: Container(
              width: MediaQuery.of(context).size.width,
              padding: EdgeInsets.all(20.0),
              child: Column(
                children: [
                  CircularProgressIndicator(),
                  SizedBox(height: 10.0,),
                  Text('Connecting to camera device'),
                ],
              ),
            ),
          ),
          Expanded(child: Container())
        ],
      ),
    );
  }

  Widget buildFilmStart(BuildContext context) {
    if(_filmStates == FilmStates.Ready) {
      return Container(
          margin: EdgeInsets.all(10.0),
          alignment: Alignment.bottomCenter,
          child: FloatingActionButton(
            backgroundColor: _colorsHolder.getBackgroundForI6().withAlpha(100),
            child: Icon(
              Icons.fiber_manual_record_sharp,
              color: Colors.red,
              size: 45,
            ),
            onPressed: () {
              setState(() {
                _filmStates = FilmStates.Filming;
                _cameraController.startVideoRecording();
              });
            },
          )
      );
    }
    return Container();
  }

  Widget buildPauseBar(BuildContext context) {
    if(_filmStates == FilmStates.Filming) {
      return Container(
        margin: EdgeInsets.all(10.0),
        alignment: Alignment.bottomCenter,
        child: Column(
          // direction: Axis.vertical,
          children: [
            Expanded(
                child: Container()
            ),
            Icon(
              Icons.fiber_manual_record_sharp,
              color: Colors.red,
              size: 30,
            ),
            FloatingActionButton(
              backgroundColor: _colorsHolder.getBackgroundForI6(),
              child: Icon(
                Icons.pause,
                color: Colors.black,
                size: 35,
              ),
              onPressed: onCameraPause,
            ),
          ],
        ),
      );
    }
    return Container();
  }

  Widget buildStopBar(BuildContext context) {
    if(_filmStates == FilmStates.Pause) {
      return Container(
        margin: EdgeInsets.all(10.0),
        alignment: Alignment.bottomCenter,
        child: Wrap (
          spacing: 20,
          // direction: Axis.vertical,
          children: [
            FloatingActionButton(
              backgroundColor: _colorsHolder.getBackgroundForI6().withAlpha(100),
              child: Icon(
                Icons.fiber_manual_record,
                color: Colors.red,
                size: 35,
              ),
              onPressed: onCameraResume,
            ),
            FloatingActionButton(
              backgroundColor: _colorsHolder.getBackgroundForI6(),
              child: Icon(
                Icons.stop,
                color: Colors.black,
                size: 35,
              ),
              onPressed: onCameraStop,
            ),
          ],
        ),
      );
    }
    return Container();
  }

  Widget buildFilm(BuildContext context) {
    return Stack(children: [
      CameraPreview(_cameraController),
      Container(
        margin: EdgeInsets.all(5.0),
        alignment: Alignment.topLeft,
        child: FloatingActionButton(
          onPressed: () {
            setState(() {
              _screenState = ScreenStates.Select;
              _filmStates = null;
            });
          },
          foregroundColor: Colors.white,
          backgroundColor: _colorsHolder.getBackgroundForI6().withAlpha(100),
          child: Icon(
            Icons.close,
            size: 20,
          ),
        ),
      ),
      buildFilmStart(context),
      buildPauseBar(context),
      buildStopBar(context),
      ]
    );
  }

  Widget buildScreenState(BuildContext context) {
    if(_screenState == ScreenStates.Search) {
      return  buildSearchState(context);
    }
    else if(_screenState == ScreenStates.NoCameraFound) {
      return  buildNoCameraState(context);
    }
    else if(_screenState == ScreenStates.Select) {
      return buildSelectState(context);
    }
    else if(_screenState == ScreenStates.ConnectingToCamera) {
      return buildConnectingToCamera(context);
    }
    // else if(_screenState == ScreenStates.Film) {
    //   return buildFilm(context);
    // }
    else if(_screenState == ScreenStates.View) {
      //TODO
    }
    return Container(
      color: Colors.red,
    );
  }

  @override
  Widget build(BuildContext context) {
    if(_screenState == ScreenStates.Film) {
      return buildFilm(context);
    }
    return SafeArea(
      child: Scaffold(
      drawer: BasicDrawer(
        this.widget.args.appUser
      ),
      appBar: AppBar(
        backgroundColor: Colors.blue,
        title: Text("Film Swimming Video",),
      ),
      body: SingleChildScrollView(
        child: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          color: _colorsHolder.getBackgroundForI6(),
          padding: const EdgeInsets.all(16.0),
          child: buildScreenState(context)
          ),
        ),
      ),
    );
  }
}

enum ScreenStates {
  Search,
  NoCameraFound,
  Select,
  ConnectingToCamera,
  Film,
  View,
}

enum FilmStates {
  Ready,
  Filming,
  Pause,
  Finished,
}
