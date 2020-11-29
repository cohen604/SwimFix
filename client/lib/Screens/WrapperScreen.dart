import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'Screen.dart';

class WrapperScreen extends Screen {
  final Screen screen;

  WrapperScreen({this.screen,Key key}) : super(key: key);

  @override
  _WrapperScreenState createState() => _WrapperScreenState();
}

class _WrapperScreenState extends State<WrapperScreen> {

  _WrapperScreenState();

  void onClick(String path) {
    Navigator.popAndPushNamed(context, path);
  }

  Widget buildMenu(BuildContext context) {
    return AppBar (
        titleSpacing: 0 ,
        title: Text('No App Bar Yet'),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: buildMenu(context),
      body: Center(
          child: this.widget.screen
      ),
    );
  }
}
