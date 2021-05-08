import 'dart:ui';
import 'package:flutter/material.dart';

class ColorsHolder {
  /// I1 means important section rate 1
  /// I2 means important section rate 2
  /// ...

  Color getBackgroundForI1() {
    return Color.fromRGBO(4, 131, 209, 1.0);
  }

  Color getBackgroundForI2() {
    return Color.fromRGBO(26, 115, 232, 1.0);
  }

  Color getBackgroundForI3() {
    return Color.fromRGBO(179, 225, 253, 1.0);
  }

  Color getBackgroundForI4() {
    return Color.fromRGBO(219, 241, 255, 1.0);
  }

  Color getBackgroundForI5() {
    return Color.fromRGBO(214, 255, 238, 1.0);
  }

  Color getBackgroundForI6() {
    return Color.fromRGBO(245, 247, 249, 1.0);
  }

  Color getBackgroundForI7() {
    return Color.fromRGBO(248, 249, 250, 1.0);
  }

  Color getBackgroundForI8() {
    return Colors.white;
  }

  Color getBackgroundPopUp() {
    return Color.fromRGBO(194, 215, 228, 1.0);
  }

  MaterialColor createMaterialColor(Color color, {
    double start=0.05,
    double delta =0.1,
    int count = 10,
    }) {
    List strengths = <double>[start];
    Map swatch = <int, Color>{};

    for (int i = 1; i < count; i++) {
      strengths.add(delta * i);
    }
    strengths.forEach((strength) {
      final double ds = 0.5 - strength;
      swatch[(strength * 1000).round()] = Color.fromRGBO(
        color.red + ((ds < 0 ? color.red : (255 - color.red)) * ds).round(),
        color.green + ((ds < 0 ? color.green : (255 - color.green)) * ds).round(),
        color.blue + ((ds < 0 ? color.blue : (255 - color.blue)) * ds).round(),
        1,
      );
    });
    return MaterialColor(color.value, swatch);
  }

}