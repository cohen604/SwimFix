import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ExampleScreen extends StatefulWidget {
  @override
  _ExampleScreenState createState() => _ExampleScreenState();
}

class _ExampleScreenState extends State<ExampleScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;
  _ExampleScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getSwimmerBackGround()),
          fit: BoxFit.fill,
          colorFilter: ColorFilter.mode(Colors.black.withAlpha(120), BlendMode.darken),
          // colorFilter: ColorFilter.mode(
          //     _webColors.getBackgroundForI6(),
          //     BlendMode.hardLight),
        ),
      ),
    );
  }
}
