import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'Arguments/SwimmerScreenArguments.dart';
import 'package:client/Components/MenuBar.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';



class WebSwimmerHistoryScreen extends StatefulWidget {

  final SwimmerScreenArguments arguments;
  WebSwimmerHistoryScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebSwimmerHistoryScreenState createState() => _WebSwimmerHistoryScreenState();
}


class _WebSwimmerHistoryScreenState extends State<WebSwimmerHistoryScreen> {

  LogicManager _logicManager = LogicManager.getInstance();
  Future<List<String>> getSwimmerDays;

  Future<List<String>> getSwimmerHistoryMap() async {
    Swimmer swimmer = this.widget.arguments.user.swimmer;
    List<String> days = await _logicManager.getSwimmerHistoryDays(swimmer);
    return days;
  }

  @override
  void initState() {
    super.initState();
    this.getSwimmerDays = getSwimmerHistoryMap();
  }

  @override
  Widget build(BuildContext context) {

    return SafeArea(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Column(
          children: [
            MenuBar(
              user: this.widget.arguments.user,
            ),
            new Expanded
              (child: FutureBuilder(
              initialData: [],
              future: this.getSwimmerDays,
              builder: (context, swimmerSnap) {
                if (swimmerSnap == null || swimmerSnap.connectionState == ConnectionState.none ||
                    swimmerSnap.hasData == null) {
                  return Container();
                }
                else {
                  // List<String> days = swimmerSnap.data as List<String>;
                  return ListView.builder(
                    itemCount: swimmerSnap.data.length,
                    itemBuilder: (BuildContext context, int index) {
                      String date = swimmerSnap.data.elementAt(index);
                      return new PoolDateTile(
                        date: date, arguments: this.widget.arguments,);
                    },
                  );
                }
              },
            ),
            ),
          ]
        ),
      ),
    );
  }

}


class PoolDateTile extends StatelessWidget {

  final String date;
  final SwimmerScreenArguments arguments;
  PoolDateTile({ this.date, this.arguments });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(top: 8.0),
      child: Card(
        margin: EdgeInsets.fromLTRB(110.0, 6.0, 110.0, 0.0),
        child: ListTile(
          leading: Icon(
            Icons.view_array
          ),
          title: Text(date),
          tileColor: Colors.blueGrey[50],
          subtitle: Text('See the pools from $date'),
          onTap: () {
            Navigator.pushNamed(context, '/historyDay',
                arguments: new SwimmerHistoryPoolsArguments(this.arguments.user, date));
          },
        ),
      ),
    );
  }
}
