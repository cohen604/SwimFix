import 'package:camera/camera.dart';
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

  ConcurrentQueue<CameraImage> _queue; //TODO remove this
  CameraController _cameraController;
  int _thresholdInvalidImages;
  int _counterInvalidImages;
  int _thresholdFrames;
  bool _cameraStateAvailable;
  //TODO add new state for _recordState that marks the process of the feedback
  bool _recordState;


  ColorsHolder _colorsHolder = new ColorsHolder();
  CameraStates _screenState = CameraStates.Search;
  List<CameraDescription> _cameras;

  _CameraScreenState() {
    _logicManager = LogicManager.getInstance();
    _poolWithNoFeedBack = new List();
    _pool = new List();
    _queue = new ConcurrentQueue();
    _cameraController = null;
    // TODO must remember:  _thresholdFrames > _thresholdInvalidImages
    _thresholdFrames = 1;
    _thresholdInvalidImages = 0;
    _counterInvalidImages = 0;
    _cameraStateAvailable = false;
    _recordState = false;
  }

  @override
  void initState() {
    super.initState();
    availableCameras().then((cameras) async {
      if (cameras == null || cameras.length < 1) {
        setState(() {
          _screenState = CameraStates.NoCameraFound;
        });
        print('No cameras found! BUG!');
      }
      else {
        setState(() {
          _screenState = CameraStates.Select;
          _cameras = cameras;
        });
      //   _cameraController = new CameraController(
      //     cameras[0],
      //     ResolutionPreset.high,
      //   );
      //   await _cameraController.initialize();
      //   if (!mounted) {
      //     return;
      //   }
      //   setState(() {
      //     _cameraStateAvailable = true;
      //   });
      }
    });
  }

  /// Todo delete this
  /// The function inserts a camera img to the queue
  /// param img - the camera img
  Future<void> enqueueCameraImage(CameraImage img) async {
    bool result = await _queue.enqueue(img);
    if (result) {
      print('Added new Img');
    }
  }

  VideoListImages createVideoListImageFromCameraImage(List<CameraImage> pool) {
    List<CameraImage> bytes = new List();
    bytes.addAll(pool);
    String name = 'pool${_poolWithNoFeedBack.length + 1}';
    return new VideoListImages(bytes, name);
  }

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

  void onPressRecord() {
    if(_cameraStateAvailable) {
      if (!_recordState) {
        startRecording();
        _recordState = true;
      }
      else {
        stopRecording();
      }
      setState(() {});
    }
  }

  String getPressRecordLabel() {
    if(_cameraStateAvailable) {
      if (!_recordState) {
        return 'Start';
      }
      else {
        return 'Finish';
      }
    }
    return 'X';
  }

  /// The function build the camera preview if the camera is available
  Widget buildCameraPreview(BuildContext context) {
    if(_cameraStateAvailable) {
      return CameraPreview(_cameraController);
    }
    return Container(
      color: Colors.white,
      child: Align(
        alignment: Alignment.center,
        child: Container(
          height: 200,
          child: Column(
            children: [
              CircularProgressIndicator(),
              SizedBox(
                height: 50,
              ),
              Text('Waiting for Camera',
                style: TextStyle(fontSize: 24.0, color:Colors.black,
                )),
            ],
          ),
        )
      ),
    );
  }

  @override
  void dispose() {
    _cameraController?.dispose();
    super.dispose();
  }

  @override
  Widget build2(BuildContext context) {
    if (_cameraController == null || !_cameraController.value.isInitialized) {
      return Container(
        child: Text ("No camera found!"),
      );
    }
    return Scaffold(
      body:
        Stack(
            children: [
              buildCameraPreview(context),
              //CameraPreview(cameraController),
              Container(
                child: Align(
                  alignment: Alignment.bottomCenter,
                  child: Container(
                    width: 100,
                    height: 65,
                    padding: EdgeInsets.only(bottom: 10),
                    child: FloatingActionButton.extended(
                      onPressed: onPressRecord,
                      //     (){
                      //   createListOfPoolsFromQueue();
                      //   cameraController.stopImageStream();
                      // },
                      backgroundColor: Colors.redAccent,
                      label: Text(getPressRecordLabel()),
                    ),
                    // TitleButton(title: 'Stop', buttonText:'Stop', onPress:() {
                    //   createListOfPoolsFromQueue();
                    //   cameraController.stopImageStream();
                    // }),
                  ),
                ),
              ),
            ],
        ),
    );
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
              child: Text('No camera devices'),
            ),
          ),
          Expanded(child: Container())
        ],
      ),
    );
  }

  Widget buildSelectState(BuildContext context) {
    return Container(
        child: Text('Cameras Found')
    );
  }


  Widget buildScreenState(BuildContext context) {
    if(_screenState == CameraStates.Search) {
      return buildSearchState(context);
    }
    else if(_screenState == CameraStates.NoCameraFound) {
      return buildNoCameraState(context);
    }
    else if(_screenState == CameraStates.Select) {
      return buildSelectState(context);
    }
    return Container(
      color: Colors.red,
    );
  }

  @override
  Widget build(BuildContext context) {
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

enum CameraStates {
  Search,
  NoCameraFound,
  Select,
  Film,
  View,
}
