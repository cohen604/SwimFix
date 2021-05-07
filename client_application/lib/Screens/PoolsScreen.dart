import 'package:client_application/Domain/Pair.dart';
import 'package:client_application/Domain/Users/AppUser.dart';
import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Domain/Video/FeedBackLink.dart';
import 'package:client_application/Screens/Arguments/FeedbackScreenArguments.dart';
import 'package:client_application/Screens/Arguments/PoolsScreenArguments.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'Holders/ColorsHolder.dart';
import 'Drawers/BasicDrawer.dart';

class PoolsScreen extends StatefulWidget {

  PoolsScreenArguments args;

  PoolsScreen(this.args);

  @override
  _PoolsScreenState createState() => _PoolsScreenState(
    args.appUser.swimmer,
    args.videoPath,
    args.poolTimes);
}

class _PoolsScreenState extends State<PoolsScreen> {

  LogicManager _logicManager;
  ColorsHolder _colorsHolder;
  Swimmer swimmer;
  String videoPath;
  List<Pool> pools;
  int currentPool;
  TimeOfDay poolTime;

  _PoolsScreenState(Swimmer swimmer, String videoPath, List<Pair<int,int>> times, ) {
    _logicManager = LogicManager.getInstance();
    _colorsHolder = new ColorsHolder();
    pools = [];
    this.swimmer = swimmer;
    this.videoPath = videoPath;
    for(Pair<int , int> item in times) {
      pools.add(new Pool(item.key, item.value));
    }
    currentPool = 0;
    nextPool();
    poolTime = TimeOfDay.now();
  }

  void nextPool() {
    if(currentPool < pools.length) {
      Pool pool = pools[currentPool];
      pool.state = PoolState.Analyzing;
      _logicManager.getFeedbackFromTimes(
          this.swimmer, this.videoPath, pool.start, pool.end)
          .then((feedback) {
        if (feedback != null) {
          pool.state = PoolState.Done;
          pool.link = feedback;
        }
        else {
          pool.state = PoolState.Error;
        }
        this.setState(() {
          currentPool++;
          nextPool();
        });
      });
    }
  }

  String getTime() {
    TimeOfDay now = poolTime; //TimeOfDay.now();
    if(now.minute < 10) {
      return '${now.hour}:0${now.minute}';
    }
    return '${now.hour}:${now.minute}';
  }

  void onViewFeedback(int index) {
    AppUser appUser = this.widget.args.appUser;
    Pool pool = pools[index];
    Navigator.pushNamed(context, "/feedback",
      arguments: new FeedbackScreenArguments(
        appUser,
        pool.link,
        poolTime,
        index + 1,
        pool.start,
        pool.end
      ),
    );
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
    else if(pool.state == PoolState.Error) {
      backColor = Colors.yellow;
      textColor = Colors.black;
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

  Widget buildPoolPending(BuildContext context, int index) {
    return Container();
  }

  Widget buildPoolAnalyzing(BuildContext context, int index) {
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

  Widget buildPoolDone(BuildContext context, int index) {
    return Container(
      width: MediaQuery.of(context).size.width,
      child:  ElevatedButton(
        onPressed: ()=>onViewFeedback(index),
        child: Text('View feedback'),
      ),
    );
  }

  Widget buildPoolError(BuildContext context, int index) {
    return Container(
      width: MediaQuery.of(context).size.width,
      padding: EdgeInsets.all(5.0),
      child: Row(
          children: [
            Icon(
              Icons.error_outline,
              size: 35,
              color: Colors.red,
            ),
            Text('Error while trying to receive feedback'),
          ],
      ),
    );
  }

  Widget buildPoolState(BuildContext context, int index) {
    Pool pool = pools[index];
    if(pool.state == PoolState.Pending) {
      buildPoolPending(context, index);
    }
    else if(pool.state == PoolState.Analyzing) {
      return buildPoolAnalyzing(context, index);
    }
    else if(pool.state == PoolState.Done) {
      return buildPoolDone(context, index);
    }
    else if(pool.state == PoolState.Error) {
      return buildPoolError(context, index);
    }
    return Container();
  }

  Widget buildPool(BuildContext context, int index) {
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
      shrinkWrap: true,
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
        body: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          color: _colorsHolder.getBackgroundForI6(),
          padding: const EdgeInsets.all(16.0),
          child: buildPoolList(context),
        ),
      ),
    );
  }
}

class Pool {

  PoolState state;
  int start;
  int end;
  FeedbackLink link;

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
