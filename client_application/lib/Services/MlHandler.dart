import 'dart:typed_data';

import 'package:tflite/tflite.dart';
import 'package:camera/camera.dart';

class MlHandler {

  MlHandler() {
    //loadTfModel();
  }

  loadTfModel() async {
    await Tflite.loadModel(
      model: "assets/models/ssd_mobilenet.tflite",
      labels: "assets/models/labels.txt",
    );
  }

  /// predict function of an frame
  Future<List<dynamic>> predict(CameraImage img) async {
    /// TODO open new thread for recognition ??
    print('in predict');
    try {
      List<dynamic> recognitions = await Tflite.detectObjectOnFrame(
        bytesList: img.planes.map((plane) {
          return plane.bytes;
        }).toList(),
        model: "SSDMobileNet",
        imageHeight: img.height,
        imageWidth: img.width,
        imageMean: 127.5,
        imageStd: 127.5,
        numResultsPerClass: 1,
        threshold: 0.4,
      );
      return recognitions;
    }
    catch(e) {
      print(e);
    }
    return new List();
  }

  Future<bool> predictValidFrame(CameraImage img) async {
    List<dynamic> preds = await predict(img);
    //TODO check if preds is valid
    print('preds: $preds');
    if(preds.isNotEmpty) {
      print('found image');
      return true;
    }
    return false;
  }

  //TODO need to do research about yuv and blue color recognition in pool
  //TODO while the swimmer come closer to the camera
  bool predictValidFrameBlue(CameraImage img) {
    Uint8List yChannel = img.planes[0].bytes; // y'
    Uint8List blueChannel = img.planes[1].bytes; // u
    Uint8List redChannel = img.planes[2].bytes; // v
    int thresholdValidBlue = 90; // 0 - 255
    int validBlueCounter = 0;
    double percentageBlue = 0.4;
    for(int i=0; i<blueChannel.length; i++) {
      if(blueChannel[i] > thresholdValidBlue) {
        validBlueCounter ++;
      }
    }
    //print('precent ${validBlueCounter / blueChannel.length}');
    if(validBlueCounter / blueChannel.length > percentageBlue) {
      //print('I am blue dabdidbadia');
      return true;
    }
    return false;
  }

}