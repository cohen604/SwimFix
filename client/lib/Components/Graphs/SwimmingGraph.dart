import 'package:client/Domain/Graph/Point.dart';
import 'package:flutter/cupertino.dart';
import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';

class SwimmingGraph extends StatefulWidget {

  int size;
  Map<int, Point> map;
  Map<int, int> errors;

  SwimmingGraph(this.size, this.map, this.errors);

  @override
  _SwimmingGraphState createState() => _SwimmingGraphState(size, map, errors);
}

class _SwimmingGraphState extends State<SwimmingGraph> {

  GraphState _graphState;
  List<FlSpot> _xs;
  List<FlSpot> _ys;

  List<Color> _gradientColors = [
    const Color(0xff23b6e6),
    const Color(0xff02d39a),
  ];

  _SwimmingGraphState(int size, Map<int, Point> map, Map<int, int> errors) {
    _graphState = GraphState.View_Y;
    _xs = [];
    _ys = [];
    for(int i=0; i <size; i++) {
      if(map.containsKey(i)) {
        Point p = map[i];
        _xs.add(FlSpot(i.toDouble(), p.x.toDouble()));
        _ys.add(FlSpot(i.toDouble(), p.y.toDouble()));
      }
      else {
        _xs.add(FlSpot(i.toDouble(), 0));
        _ys.add(FlSpot(i.toDouble(), 0));
      }
    }
  }

  FlGridData getGridData() {
    return FlGridData(
      show: true,
      drawVerticalLine: true,
      getDrawingHorizontalLine: (value) {
        return FlLine(
          color: Colors.black,
          strokeWidth: 1,
        );
      },
      getDrawingVerticalLine: (value) {
        return FlLine(
          color: Colors.black,
          strokeWidth: 1,
        );
      },
    );
  }

  FlTitlesData getTitlesData() {
    return FlTitlesData(
      show: true,
      bottomTitles: SideTitles(
        showTitles: true,
        reservedSize: 22,
        getTextStyles: (value) {
          return TextStyle(
              color: Colors.white,
              fontWeight: FontWeight.bold,
              fontSize: 14);
        },
        getTitles: (value) {
          return '${value+1}';
        },
        margin: 8,
      ),
    );
  }

  LineChartBarData getLineChartBarData() {
    return LineChartBarData(
      spots: _graphState == GraphState.View_X ? _xs : _ys,
      isCurved: true,
      colors: _gradientColors,
      barWidth: 5,
      isStrokeCapRound: true,
      dotData: FlDotData(
        show: false,
      ),
      belowBarData: BarAreaData(
        show: true,
        colors: _gradientColors.map((color) => color.withOpacity(0.3)).toList(),
      ),
    );
  }

  FlBorderData getBorderData() {
    return FlBorderData(
        show: true,
        border: Border.all(
            color: const Color(0xff37434d),
            width: 1
        )
    );
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

  Widget buildTitle(BuildContext context) {
    if(_graphState == GraphState.View_X) {
      return buildText(context, 'X\'s', 26, Colors.white, FontWeight.bold);
    }
    else if(_graphState == GraphState.View_Y) {
      return buildText(context, 'Y\'s', 26, Colors.white, FontWeight.bold);
    }
    return Container();
  }

  Widget buildButton(BuildContext context, String title, GraphState graphState) {
    bool selected = graphState == _graphState;
    return Container(
      margin: const EdgeInsets.all(7),
      child: ElevatedButton(
          style:ElevatedButton.styleFrom(
            primary: selected ? Colors.white : const Color.fromARGB(255, 35, 45, 55),
          ),
          onPressed: selected? ()=>{} : ()=>this.setState(() =>_graphState = graphState),
          child: Padding(
            padding: const EdgeInsets.all(5.0),
            child: buildText(
                context,
                title,
                21,
                selected ? const Color.fromARGB(255, 35, 45, 55) : Colors.white,
                FontWeight.normal),
          )
      ),
    );
  }

  Widget buildBottomBar(BuildContext context) {
    return Row(
      children: [
        buildButton(context, 'Axis X', GraphState.View_X),
        buildButton(context, 'Axis Y', GraphState.View_Y),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        borderRadius: BorderRadius.all(
          Radius.circular(10),
        ),
        color: const Color.fromARGB(100, 35, 45, 55),
      ),
      padding: const EdgeInsets.only(right: 25, left: 15, top: 5, bottom: 5),
      child: Column(
        children: [
          buildTitle(context),
          ConstrainedBox(
            constraints: BoxConstraints(
              maxHeight: 350,
              maxWidth: 420,
            ),
            child: Padding(
              padding: EdgeInsets.only(top: 12, bottom: 12),
              child: LineChart(
                LineChartData(
                    borderData: getBorderData(),
                    titlesData: getTitlesData(),
                    lineBarsData: [
                      getLineChartBarData(),
                    ]
                ),
                swapAnimationDuration: Duration(milliseconds: 150), // Optional
                swapAnimationCurve: Curves.linear,
              ),
            ),
          ),
          buildBottomBar(context),
        ],
      ),
    );
  }

}

enum GraphState {
  View_X,
  View_Y,
}
