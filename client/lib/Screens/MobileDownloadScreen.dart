import 'dart:html' as html;
import 'package:client/Components/AboutScreenMenuBar.dart';
import 'package:client/Components/MobileAboutScreenMenuBar.dart';
import 'package:client/Screens/Arguments/AboutScreenArguments.dart';
import 'package:client/Screens/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class MobileDownloadScreen extends StatefulWidget {
  @override
  _MobileDownloadScreenState createState() => _MobileDownloadScreenState();
}

class _MobileDownloadScreenState extends State<MobileDownloadScreen> {

  WebColors webColors;

  _MobileDownloadScreenState() {
    webColors = new WebColors();
  }

  Function onLogo(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/',
              arguments: new AboutScreenArguments());
        });
      });
    };
  }

  Function onAbout(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/',
              arguments: new AboutScreenArguments(aboutOn: true));
        });
      });
    };
  }

  Function onDownload(BuildContext context) {
    return ()=>{};
  }

  void onDownloadAndroidArm32() async{
    html.window.open('/assets/releases/app-armeabi-v7a-release.apk', "apk");
  }

  void onDownloadAndroidArm64() async{
    html.window.open('/assets/releases/app-arm64-v8a-release.apk', "apk");
  }
  void onDownloadAndroidx86_64() async{
    html.window.open('/assets/releases/app-x86_64-release.apk', "apk");
  }

  Widget buildTitle(BuildContext context, String text) {
    return Text(text,
      style: TextStyle(
        fontSize: 32 * MediaQuery.of(context).textScaleFactor,
        color: Colors.black,
      ),
    );
  }

  Widget buildDes(BuildContext context, String des) {
    return Text(des,
      style: TextStyle(
        fontSize: 20 * MediaQuery.of(context).textScaleFactor,
        color: Colors.grey,
      ),
    );
  }

  Widget buildDownload(BuildContext context,
      String title,
      String des,
      Function onClick,) {
    return Container(
      margin: EdgeInsets.all(5),
      child: Card(
        child: Padding(
          padding: EdgeInsets.all(25),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              buildTitle(context, title),
              buildDes(context, des),
              SizedBox(height: 10,),
              Container(
                width: MediaQuery.of(context).size.width,
                child: ElevatedButton(
                  onPressed: onClick,
                  child: Text('Download',
                    style: TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget buildDownloadArea(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          buildDownload(
              context,
              'Android',
              'Min SDK: 27\nSystem: ARM 32 bit',
              onDownloadAndroidArm32),
          buildDownload(
              context,
              'Android',
              'Min SDK: 27\nSystem: ARM 64 bit',
              onDownloadAndroidArm64),
          buildDownload(
              context,
              'Android',
              'Min SDK: 27\nSystem: x86 64 bit',
              onDownloadAndroidArm64),
        ]
      ),
    );
  }

  @override
  Widget build(BuildContext context) {

    return SafeArea(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        color: webColors.getBackgroundForI6(),
        child: Column(
          children: [
            MobileAboutScreenMenuBar(
              onLogo: onLogo(context),
              onAbout: onAbout(context),
              onDownload: onDownload(context),
            ),
            Expanded(
                child: buildDownloadArea(context)
            ),
          ],
        ),
      ),
    );
  }
}
