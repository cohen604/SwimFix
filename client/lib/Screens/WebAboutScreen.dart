import 'package:client/Components/AboutScreenMenuBar.dart';
import 'package:client/Screens/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class WebAboutScreen extends StatefulWidget {
  @override
  _WebAboutScreenState createState() => _WebAboutScreenState();
}

class _WebAboutScreenState extends State<WebAboutScreen> {

  WebColors _webColors = new WebColors();

  Widget buildText(BuildContext context,
      String text,
      double fontSize,
    { Color color = Colors.white,
      FontWeight fontWeight = FontWeight.normal}) {
    return Text(text,
      textAlign: TextAlign.left,
      style: TextStyle(
          fontSize: fontSize * MediaQuery.of(context).textScaleFactor,
          color: color,
          fontWeight: fontWeight,
          decoration: TextDecoration.none
      ));
  }

  Widget buildLeftLoginArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      padding: EdgeInsets.only(left: 30),
      child: Column(
        children: [
          Flexible(
            fit: FlexFit.tight,
            child: Container(
              alignment: Alignment.bottomLeft,
              child: buildText(context, "Swim Analytics", 84,
                  fontWeight: FontWeight.bold))
          ),
          Container(
              alignment: Alignment.centerLeft,
              child: buildText(
                  context, "Swimming training solutions", 36,
              )
          ),
          Expanded(
            child: Container(
              alignment: Alignment.topLeft,
              child: buildText(context,"The best platform to improve your swimming abilities.\n"
                  "The platform allows any swimmer to record, view, train,\n"
                  "receive swimming videos, feedback's and many more.\n", 21,),
            ),
          ),
        ],
      ),
    );
  }

  Widget buildRightLoginArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Column(
        children: [
          Flexible(
            flex: 4,
            child: Container(
            ),
          ),
          Flexible(
            child: Container(
              padding: EdgeInsets.only(right:20),
              alignment: Alignment.topRight,
              child: ElevatedButton(
                style: ButtonStyle(
                  shadowColor: MaterialStateColor.resolveWith((states) =>Colors.black),
                  backgroundColor: MaterialStateColor.resolveWith((states) => _webColors.getBackgroundForI1()),
                  shape: MaterialStateProperty.resolveWith((states) => RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(30.0),
                  )),
                ),
                child: Container(
                    padding: EdgeInsets.all(5),
                    child: buildText(context, 'Sign Up', 28,
                      fontWeight: FontWeight.bold)
                ),
              )
            )
          )
        ],
      ),
    );
  }

  Widget buildLoginArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage('assets/images/about_screen_background.png'),
          fit: BoxFit.cover,
          colorFilter: ColorFilter.mode(Colors.black.withAlpha(120), BlendMode.darken),
        ),
      ),
      child: Row(
        children: [
          Flexible(
            child: buildLeftLoginArea(context)
          ),
          Flexible(
            child: buildRightLoginArea(context),
          )
        ],
      ),
    );
  }

  Widget buildAboutArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
    );
  }

  Widget buildAreas(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      color: _webColors.getBackgroundForI6(),
      child: Scrollbar(
        child: SingleChildScrollView(
          child: Column(
              children: [
                buildLoginArea(context),
                buildAboutArea(context),
              ],
          ),
        ),
      )
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Column(
          children: [
            AboutScreenMenuBar(),
            Expanded(
                child: buildAreas(context)
            ),
          ],
        ),
      ),
    );
  }

}
