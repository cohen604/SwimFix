import 'package:client/Domain/Graph/Point.dart';
import 'package:flutter/cupertino.dart';
import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';

class SwimmingGraph extends StatefulWidget {

  String title;
  int size;
  Map<int, Point> map;
  Map<int, int> errors;

  SwimmingGraph(this.title, this.size, this.map, this.errors);

  @override
  _SwimmingGraphState createState() => _SwimmingGraphState(size, map, errors);
}

class _SwimmingGraphState extends State<SwimmingGraph> {

  GraphState _graphState;
  List<FlSpot> _xs;
  List<FlSpot> _ys;

  List<Color> _gradientColors = [
    // const Color(0xff23b6e6),
    const Color(0xff02d39a),
  ];

  _SwimmingGraphState(int size, Map<int, Point> map, Map<int, int> errors) {
    _graphState = GraphState.Loading;
  }

  @override
  void initState() {
    super.initState();
    calcFLSpot(this.widget.size, this.widget.map).then(
      (value){
        this.setState(() {
          _xs = value[0];
          _ys = value[1];
          _graphState = GraphState.View_Y;
        });
      }
    );
  }

  Future<List<List<FlSpot>>> calcFLSpot(int size, Map<int, Point> map) async {
    List<FlSpot> xs = [];
    List<FlSpot> ys = [];
    for(int i=0; i <size; i++) {
      if(map.containsKey(i)) {
        Point p = map[i];
        xs.add(FlSpot(i.toDouble(), p.x.toDouble()));
        ys.add(FlSpot(i.toDouble(), p.y.toDouble()));
      }
      else {
        xs.add(FlSpot(i.toDouble(), 0));
        ys.add(FlSpot(i.toDouble(), 0));
      }
    }
    return [xs, ys];
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
              fontSize: 12);
        },
        getTitles: (value) {
          if(value % 10 == 0) {
            return '${value + 1}';
          }
          return "";
        },
        margin: 8,
      ),
      leftTitles: SideTitles(
        showTitles: true,
        reservedSize: 16,
        getTextStyles: (value) {
          return TextStyle(
              color: Colors.white,
              fontWeight: FontWeight.bold,
              fontSize: 12);
        },
        interval: 30,
        getTitles: (value) {
          return '$value';
        },
        margin: 8,
      )
    );
  }

  LineChartBarData getLineChartBarData() {
    return LineChartBarData(
      spots: _graphState == GraphState.View_X ? _xs : _ys,
      isCurved: true,
      colors: _gradientColors,
      barWidth: 3,
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
      return buildText(context, '${this.widget.title} X\'s', 26, Colors.white, FontWeight.bold);
    }
    else if(_graphState == GraphState.View_Y) {
      return buildText(context, '${this.widget.title} Y\'s', 26, Colors.white, FontWeight.bold);
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
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      children: [
        buildButton(context, 'X Axis', GraphState.View_X),
        buildButton(context, 'Y Axis', GraphState.View_Y),
      ],
    );
  }

  Widget buildView(BuildContext context) {
    return Column(
        children: [
          buildTitle(context),
          ConstrainedBox(
            constraints: BoxConstraints(
              maxHeight: 450,
              maxWidth: 450,
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
                swapAnimationCurve: Curves.linear,
              ),
            ),
          ),
          buildBottomBar(context),
        ]
    );
  }

  Widget buildGraphState(BuildContext context) {
    if(_graphState == GraphState.Loading) {
      return Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            CircularProgressIndicator(),
            SizedBox(height: 5.0,),
            buildText(context, 'Loading graph...', 21, Colors.white, FontWeight.normal),
          ],
        ),
      );
    }
    return buildView(context);
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        borderRadius: BorderRadius.all(
          Radius.circular(10),
        ),
        color: const Color.fromARGB(255, 35, 45, 55),
      ),
      padding: const EdgeInsets.only(right: 25, left: 15, top: 5, bottom: 5),
      child: buildGraphState(context)
    );
  }

}

enum GraphState {
  Loading,
  View_X,
  View_Y,
}
