import 'package:flutter/material.dart';

class BasicTheme {

}

ThemeData basicTheme() {
  TextTheme _basicTextTheme(TextTheme base) {
      return base.copyWith(
        headline1: base.headline1.copyWith(
          fontSize: 22.0,
          color: Colors.black,
        ),
      );
  }
  return null;
}

