import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

class AdminGraph extends StatefulWidget {

  String title;
  double total;
  double online;
  double offline;

  AdminGraph(this.title, this.total, this.online, this.offline);

  @override
  _AdminGraphState createState() => _AdminGraphState();
}

class _AdminGraphState extends State<AdminGraph> {

  int _selected;

  _AdminGraphState() {
    _selected = -1;
  }

  void onTouchCallBack(pieTouchResponse) {
    setState(() {
      final desiredTouch = pieTouchResponse.touchInput is! PointerExitEvent &&
          pieTouchResponse.touchInput is! PointerUpEvent;
      if (desiredTouch && pieTouchResponse.touchedSection != null) {
        _selected = pieTouchResponse.touchedSection.touchedSectionIndex;
      } else {
        _selected = -1;
      }
    });
  }

  PieChartSectionData buildSelectionOnline() {
    bool isTouched = 0 == _selected;
    double fontSize = isTouched ? 20.0 : 16.0;
    double radius = isTouched ? 110.0 : 100.0;
    double widgetSize = isTouched ? 55.0 : 40.0;
    return PieChartSectionData(
      color: Colors.green,
      value: this.widget.online,
      title: '${this.widget.online} (${100 * this.widget.online/ this.widget.total}%)',
      radius: radius,
      titleStyle: TextStyle(
          fontSize: fontSize, fontWeight: FontWeight.bold, color: const Color(0xffffffff)),
    );
  }

  PieChartSectionData buildSelectionOffline() {
    bool isTouched = 1 == _selected;
    double fontSize = isTouched ? 20.0 : 16.0;
    double radius = isTouched ? 110.0 : 100.0;
    double widgetSize = isTouched ? 55.0 : 40.0;
    return PieChartSectionData(
      color: Colors.redAccent,
      value: this.widget.offline,
      title: '${this.widget.offline} (${100 * this.widget.offline/ this.widget.total}%)',
      radius: radius,
      titleStyle: TextStyle(
          fontSize: fontSize, fontWeight: FontWeight.bold, color: const Color(0xffffffff)),
    );
  }

  List<PieChartSectionData> showingSections() {
    return [
      buildSelectionOnline() ,
      buildSelectionOffline(),
    ];
  }

  Widget buildText(
      BuildContext context,
      String text,
      int size,
      Color color,
      FontWeight fontWeight,
      {textAlign = TextAlign.left}) {
    return Text(text,
      textAlign: textAlign,
      style: TextStyle(
          fontSize: size * MediaQuery.of(context).textScaleFactor,
          color: color,
          fontWeight: fontWeight,
          decoration: TextDecoration.none
      ),
    );
  }

  Widget buildPie(BuildContext context) {
    return AspectRatio(
      aspectRatio: 1,
      child: PieChart(
        PieChartData(
            pieTouchData: PieTouchData(
                touchCallback: onTouchCallBack
            ),
            borderData: FlBorderData(
              show: false,
            ),
            sectionsSpace: 0,
            centerSpaceRadius: 0,
            sections: showingSections()),
      ),
    );
  }

  Widget buildIndicator(BuildContext context, Color color, String text,  bool isSquare, double size, Color textColor) {
    return Row(
      children: <Widget>[
        Container(
          width: size,
          height: size,
          decoration: BoxDecoration(
            shape: isSquare ? BoxShape.rectangle : BoxShape.circle,
            color: color,
          ),
        ),
        SizedBox(
          width: 4,
        ),
        buildText(context, text, 16, textColor, FontWeight.normal),
      ],
    );
  }

  Widget buildBar(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        buildIndicator(
          context,
          Colors.green,
          'Online',
          true,
          16,
          Colors.black
        ),
        SizedBox(height: 10,),
        buildIndicator(
            context,
            Colors.red,
            'Offline',
            true,
            16,
            Colors.black
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      color: Colors.white,
      child: Padding(
        padding: const EdgeInsets.all(10.0),
        child: Column(
          children: [
            buildText(context, this.widget.title, 24, Colors.black, FontWeight.normal),
            Expanded(
              child: Row(
                children: [
                  Expanded(
                    child: buildPie(context),
                  ),
                  buildBar(context),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

}