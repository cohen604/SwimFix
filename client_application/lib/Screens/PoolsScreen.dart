import 'package:client_application/Domain/Pair.dart';
import 'package:client_application/Screens/Arguments/PoolsScreenArguments.dart';
import 'package:client_application/Screens/ColorsHolder.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'Drawers/BasicDrawer.dart';

class PoolsScreen extends StatefulWidget {

  PoolsScreenArguments args;

  PoolsScreen(this.args);

  @override
  _PoolsScreenState createState() => _PoolsScreenState(args.poolTimes);
}

class _PoolsScreenState extends State<PoolsScreen> {

  ColorsHolder _colorsHolder;
  List<Pool> pools;

  _PoolsScreenState(List<Pair<int,int>> times) {
    _colorsHolder = new ColorsHolder();
    pools = [];
    for(Pair<int , int> item in times) {
      pools.add(new Pool(item.key, item.value));
    }
    pools[0].state = PoolState.Analyzing;
  }

  String getTime() {
    TimeOfDay now = TimeOfDay.now();
    if(now.minute < 10) {
      return '${now.hour}:0${now.minute}';
    }
    return '${now.hour}:${now.minute}';
  }

  Widget buildNumber(BuildContext context, int index) {
    Pool pool = pools[index];
    Color backColor = Colors.grey.shade400;
    Color textColor = Colors.white;
    if(pool.state == PoolState.Analyzing) {
      backColor = _colorsHolder.getBackgroundForI2();
    }
    else if(pool.state == PoolState.Done) {
      backColor = _colorsHolder.getBackgroundForI1();
    }
    return CircleAvatar(
      backgroundColor: backColor,
      foregroundColor: textColor,
      radius: 25,
      child: Text('${index + 1}',
        style: TextStyle(
          fontSize: 22 * MediaQuery.of(context).textScaleFactor,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }

  Widget buildTitle(BuildContext context, String title) {
    return Text(title,
      textAlign: TextAlign.left,
      style: TextStyle(
        color: Colors.black,
        fontSize: 22 * MediaQuery.of(context).textScaleFactor,
        fontWeight: FontWeight.normal,
      ),
    );
  }

  Widget buildDes(BuildContext context, String description) {
    return Text(description,
      style: TextStyle(
        color: Colors.grey,
        fontSize: 18 * MediaQuery.of(context).textScaleFactor,
        fontWeight: FontWeight.normal,
      ),
    );
  }

  Widget buildPoolInfo(BuildContext context, int index) {
    Pool pool = pools[index];
    return Row(
      children: [
        buildNumber(context, index),
        Expanded(
          child: Container(
            padding: EdgeInsets.only(left: 10),
            child: Wrap(
              direction: Axis.vertical,
              children: [
                buildTitle(context, 'Pool'),
                buildDes(context, 'start: ${pool.start} seconds'),
                buildDes(context, 'end: ${pool.end} seconds'),
              ],
            ),
          ),
        )
      ],
    );
  }

  Widget buildPoolState(BuildContext context, int index) {
    Pool pool = pools[index];
    if(pool.state == PoolState.Pending) {
      return Container();
    }
    else if(pool.state == PoolState.Analyzing) {
      return Container(
        width: MediaQuery.of(context).size.width,
        margin: EdgeInsets.only(top: 15, bottom: 10),
        child: Column(
          children: [
            CircularProgressIndicator(),
            SizedBox(height: 5,),
            Text('Analyzing pool'),
          ],
        ),
      );
    }
    else if(pool.state == PoolState.Done) {
      return Container(
        width: MediaQuery.of(context).size.width,
        child:  ElevatedButton(
          child: Text('View feedback'),
        ),
      );
    }
    return Container();
  }

  Widget buildPool(BuildContext context, int index) {
    Pool pool = pools[index];
    return Card(
      child: Container(
        padding: EdgeInsets.all(15),
        child: Wrap(
            children: [
              buildPoolInfo(context, index),
              buildPoolState(context, index),
            ]),
      ),
    );
  }

  Widget buildPoolList(BuildContext context) {
    print(pools.length);
    return ListView.builder(
      itemCount: pools.length,
      itemBuilder: (context, index) {
        return buildPool(context, index);
      }
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        drawer: BasicDrawer(
            this.widget.args.appUser
        ),
        appBar: AppBar(
          backgroundColor: Colors.blue,
          title: Text("Pools ${getTime()}",),
        ),
        body: SingleChildScrollView(
          child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            color: _colorsHolder.getBackgroundForI6(),
            padding: const EdgeInsets.all(16.0),
            child: buildPoolList(context)
          ),
        ),
      ),
    );
  }
}

class Pool {

  PoolState state;
  int start;
  int end;

  Pool(this.start, this.end) {
    state = PoolState.Pending;
  }
}

enum PoolState {
  Pending,
  Analyzing,
  Done,
  Error,
}
