import 'dart:typed_data';
import 'package:camera/camera.dart';
import 'package:client/Domain/Feedback/FeedBackVideoStreamer.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:image/image.dart';
import 'dart:io';

import 'VideoWithoutFeedback.dart';

class VideoListImages extends VideoWithoutFeedback {

  List <CameraImage> _bytes;
  String _type;
  String _name;

  VideoListImages(List <CameraImage> bytes, String name) {
    _bytes = bytes;
    _type = 'png';
    _name = name;
  }

  @override
  Future<FeedbackVideoStreamer> getFeedbackVideo(
      LogicManager logicManager) async {
    List<Uint8List> data = new List();
    print('Before process pool with ${_bytes.length} frames ${DateTime.now()}');
    for (CameraImage cameraImage in _bytes) {
      print('W: ${cameraImage.width} H: ${cameraImage.height}');
      print('Before convert ${DateTime.now()}');
      Uint8List tmp = convertYUV420toImageColor(cameraImage);
      print('After convert ${DateTime.now()}');
      data.add(tmp);
    }
    print('After process pool with ${_bytes.length} frames ${DateTime.now()}');
    return await logicManager.postListImagesForStreaming(
        data,
        _type,
        _name,
        null //TODO change this null need to be swimmer
    );
  }


  /// taken from:
  /// https://stackoverflow.com/questions/54230291/how-to-convert-flutter-cameraimage-to-a-base64-encoded-binary-data-object-from-a
  /// works only for android!
  /// better with:
  /// https://medium.com/@hugand/capture-photos-from-camera-using-image-stream-with-flutter-e9af94bc2bee
  Uint8List convertYUV420toImageColor(CameraImage image) {
    try {
      final int width = image.width;
      final int height = image.height;
      final int uvRowStride = image.planes[1].bytesPerRow;
      final int uvPixelStride = image.planes[1].bytesPerPixel;

      // imgLib -> Image package from https://pub.dartlang.org/packages/image
      var img = Image(width, height); // Create Image buffer

      // Fill image buffer with plane[0] from YUV420_888
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          final int uvIndex = uvPixelStride * (x / 2).floor() +
              uvRowStride * (y / 2).floor();
          final int index = y * width + x;

          final yp = image.planes[0].bytes[index];
          final up = image.planes[1].bytes[uvIndex];
          final vp = image.planes[2].bytes[uvIndex];
          // Calculate pixel color
          int r = (yp + vp * 1436 / 1024 - 179).round().clamp(0, 255);
          int g = (yp - up * 46549 / 131072 + 44 - vp * 93604 / 131072 + 91)
              .round()
              .clamp(0, 255);
          int b = (yp + up * 1814 / 1024 - 227).round().clamp(0, 255);
          // color: 0x FF  FF  FF  FF
          //           A   B   G   R
          img.data[index] = (0xFF << 24) | (b << 16) | (g << 8) | r;
        }
      }

      PngEncoder pngEncoder = new PngEncoder(level: 0, filter: 0);
      List<int> png = pngEncoder.encodeImage(img);
      return Uint8List.fromList(png);
    } catch (e) {
      print(">>>>>>>>>>>> ERROR:" + e.toString());
    }
    return null;
  }

  //TODO create a video from list function
  Future<FeedbackVideoStreamer> getFeedbackVideo2(
      LogicManager logicManager) async {
    List<Uint8List> data = new List();
    print('Before process pool with ${_bytes.length} frames ${DateTime.now()}');
    for (CameraImage cameraImage in _bytes) {
      Uint8List tmp = convertYUV420toImageColor(cameraImage);
      data.add(tmp);
    }
    print('After process pool with ${_bytes.length} frames ${DateTime.now()}');
    return await logicManager.postListImagesForStreaming(
        data,
        _type,
        _name,
        null //TODO change this null need to be swimmer
    );
  }
  Future<File> saveFile(List<int> png) async {
    String path = 'testDir';
    print('creating dir');
    Directory dir = await Directory(path).createTemp();
    print('dir created');
    if(dir != null) {
      print('creating file');
      File file = new File('${dir.path}/testFile.png');
      file.writeAsBytesSync(png);
      print('created file in ${file.path}');
      return file;
    }
    else {
      print('Error');
    }
    return null;
  }



}