import 'package:flutter/material.dart';

abstract class Screen extends StatefulWidget {
  final String title;
  Screen({this.title,Key key}) : super(key: key);
}