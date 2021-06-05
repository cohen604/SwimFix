import 'dart:html';
import 'package:client/Components/MenuBars/AboutScreenMenuBar.dart';
import 'package:client/Screens/Arguments/AboutScreenArguments.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'Holders/WebColors.dart';

class WebDownloadScreen extends StatefulWidget {
  @override
  _WebDownloadScreenState createState() => _WebDownloadScreenState();
}

class _WebDownloadScreenState extends State<WebDownloadScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;
  _WebDownloadScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
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

  Widget buildText(BuildContext context, String text, int size, Color color,
      FontWeight fontWeight) {
    return Text(
      text,
      style: TextStyle(
          fontSize: size * MediaQuery.of(context).textScaleFactor,
          color: color,
          fontWeight: fontWeight,
          decoration: TextDecoration.none),
    );
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
              SizedBox(height: 5,),
              Container(
                width: MediaQuery.of(context).size.width,
                child: ElevatedButton(
                  onPressed: onClick,
                  child: Padding(
                      padding: const EdgeInsets.all(5),
                      child: buildText(context, 'Download', 21, Colors.white, FontWeight.bold)
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget buildDownloadComingSoon(BuildContext context,
      String title,
      String des) {
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
              SizedBox(height: 5,),
              Container(
                width: MediaQuery.of(context).size.width,
                child: ElevatedButton(
                  onPressed: null,
                  child: Padding(
                      padding: const EdgeInsets.all(5),
                      child: buildText(context, 'Comming soon', 21, Colors.black87, FontWeight.normal)
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
              'Min SDK: 27\n',
              onDownloadAndroidArm64),
        ),
        Flexible(
          child: buildDownloadComingSoon(
              context,
              'iOS',
              'Min SDK: 14.5\n'
          ),
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
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage(_assetsHolder.getBackGroundImage()),
            fit: BoxFit.fill,
          ),
        ),
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
