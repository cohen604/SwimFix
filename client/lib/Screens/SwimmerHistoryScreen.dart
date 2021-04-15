import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Services/LogicManager.dart';

import 'Arguments/SwimmerScreenArguments.dart';
import 'Arguments/UploadScreenArguments.dart';
import 'package:client/Components/IconCardButton.dart';
import 'package:client/Components/MenuBar.dart';
import 'package:client/Screens/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';



class SwimmerHistoryScreen extends StatefulWidget {

  final SwimmerScreenArguments arguments;
  SwimmerHistoryScreen({Key key, this.arguments}) : super(key: key);

  @override
  _SwimmerHistoryScreenState createState() => _SwimmerHistoryScreenState();
}


class _SwimmerHistoryScreenState extends State<SwimmerHistoryScreen> {

  LogicManager _logicManager = LogicManager.getInstance();

  @override
  Widget build(BuildContext context) {

    return SafeArea(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Column(
          children: [
            MenuBar(
              swimmer: this.widget.arguments.swimmer,
            ),
            new Expanded(child: FutureBuilder(
              builder: (context, swimmerSnap) {
                print(swimmerSnap);
                if (swimmerSnap.connectionState == ConnectionState.none &&
                    swimmerSnap.hasData == null) {
                  return Container();
                }
                return ListView.builder(
                  itemCount: swimmerSnap.data.length,
                  itemBuilder: (BuildContext context, int index) {
                    String date = swimmerSnap.data.keys.elementAt(index);
                    return new PoolDateTile(date: date,);
                  },
                );
              },
              future: getSwimmerHistoryMap(),
            ),
            ),
          ]
        ),
      ),
    );
  }

  
  
  
  Future<Map> getSwimmerHistoryMap() async {
    Swimmer swimmer = this.widget.arguments.swimmer;
    Map map = await _logicManager.getSwimmerHistory(swimmer);
    return map;
  }


}


class PoolDateTile extends StatelessWidget {

  final String date;
  PoolDateTile({ this.date });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(top: 8.0),
      child: Card(
        margin: EdgeInsets.fromLTRB(20.0, 6.0, 20.0, 0.0),
        child: ListTile(
          leading: CircleAvatar(
            radius: 25.0,
            backgroundColor: Colors.blueGrey[300],
          ),
          title: Text(date),
          subtitle: Text('See the pools from $date'),
        ),
      ),
    );
  }
}
