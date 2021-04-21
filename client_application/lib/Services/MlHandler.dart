import 'dart:typed_data';
import 'package:camera/camera.dart';

class MlHandler {

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

  double getBlueRate(CameraImage img) {
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
    return validBlueCounter / blueChannel.length;
  }

}