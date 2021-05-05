import 'dart:html' as html;
import 'dart:html';
import 'file:///C:/Users/avrah/Desktop/semesterA/final_project/SwimFix/client/lib/Components/MenuBars/AboutScreenMenuBar.dart';
import 'package:client/Screens/Arguments/AboutScreenArguments.dart';
import 'file:///C:/Users/avrah/Desktop/semesterA/final_project/SwimFix/client/lib/Screens/Holders/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class WebDownloadScreen extends StatefulWidget {
  @override
  _WebDownloadScreenState createState() => _WebDownloadScreenState();
}

class _WebDownloadScreenState extends State<WebDownloadScreen> {

  WebColors webColors;

  _WebDownloadScreenState() {
    webColors = WebColors.getInstance();
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

  Function onLogin(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/',
              arguments: new AboutScreenArguments(loginOn: true));
        });
      });
    };
  }

  void onClickHref(String href) {
    AnchorElement(
        href: href)
      ..setAttribute("download", 'swimAnalytics.apk')
      ..click();
  }

  void onDownloadAndroidArm32() {
    onClickHref('/assets/assets/releases/app-armeabi-v7a-release.apk');
  }

  void onDownloadAndroidArm64() {
    onClickHref('/assets/assets/releases/app-arm64-v8a-release.apk');
  }
  void onDownloadAndroidx86_64() {
    onClickHref('/assets/assets/releases/app-x86_64-release.apk');
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
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        Flexible(child: Container()),
        Flexible(
          child: buildDownload(
              context,
              'Android',
              'Min SDK: 27\nSystem: ARM 32 bit',
              onDownloadAndroidArm32),
        ),
        Flexible(
          child: buildDownload(
              context,
              'Android',
              'Min SDK: 27\nSystem: ARM 64 bit',
              onDownloadAndroidArm64),
        ),
        Flexible(
          child: buildDownload(
              context,
              'Android',
              'Min SDK: 27\nSystem: x86 64 bit',
              onDownloadAndroidArm64),
        ),
        Flexible(child: Container()),
      ]
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
            AboutScreenMenuBar(
              onLogo: onLogo(context),
              onAbout: onAbout(context),
              onDownload: onDownload(context),
              onLogin: onLogin(context),
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
