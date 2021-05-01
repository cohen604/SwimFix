import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'package:client/Screens/Arguments/ViewFeedbackArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Components/MenuBar.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';



class WebSwimmerHistoryDayScreen extends StatefulWidget {

  final SwimmerHistoryPoolsArguments arguments;
  WebSwimmerHistoryDayScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebSwimmerHistoryScreenState createState() => _WebSwimmerHistoryScreenState();
}


class _WebSwimmerHistoryScreenState extends State<WebSwimmerHistoryDayScreen> {

  LogicManager _logicManager = LogicManager.getInstance();

  Future<Map<String, dynamic>> getSwimmerHistoryMap(String day) async {
    Swimmer swimmer = this.widget.arguments.webUser.swimmer;
    Map pools = await _logicManager.getSwimmerHistoryPoolsByDay(swimmer, day);
    return pools;
  }

  void removeTile() {
    setState(() {
    });
  }

  @override
  Widget build(BuildContext context) {

    return SafeArea(
      child: Scaffold(
        body: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          child: Column(
              children: [
                MenuBar(
                  user: this.widget.arguments.webUser,
                ),
                SizedBox(height: 10.0),
                Align(
                  alignment: Alignment.topLeft,
                  child: Ink(
                    decoration: const ShapeDecoration(
                      color: Colors.lightBlue,
                      shape: CircleBorder(),
                    ),
                    child: IconButton(
                      onPressed: () {
                        Navigator.pop(context);
                      },
                      icon: Icon(
                        Icons.arrow_back,
                        color: Colors.white,
                      ),
                      iconSize: 20.0,
                    ),
                  ),
                ),
                SizedBox(height: 5.0),
                new Expanded    
                  (child: FutureBuilder(
                  initialData: [],
                  future: getSwimmerHistoryMap(this.widget.arguments.date),
                  builder: (context, swimmerSnap) {
                    if (swimmerSnap.connectionState == ConnectionState.none &&
                        swimmerSnap.hasData == null) {
                      return Container();
                    }
                    else {
                      return ListView.builder(
                        itemCount: swimmerSnap.data.length,
                        itemBuilder: (BuildContext context, int index) {
                          String hour = swimmerSnap.data.keys.elementAt(index);
                          FeedBackLink feedbackLink =
                            new FeedBackLink.fromJson(swimmerSnap.data[hour]);
                          String path = feedbackLink.getPath();
                          print('feedback link is ' + path);
                          return new PoolHourTile
                            (hour: hour,
                            path: path,
                            user: this.widget.arguments.webUser,
                            logicManager: _logicManager,
                            remove: removeTile);
                        },
                      );
                    }
                  },
                ),
                ),
              ]
          ),
        ),
      ),
    );
  }

}


class PoolHourTile extends StatelessWidget {

  final String hour;
  final String path;
  final WebUser user;
  final LogicManager logicManager;
  final Function() remove;
  PoolHourTile({ this.hour, this.path, this.user, this.logicManager, this.remove});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(top: 8.0),
      child: Card(
        margin: EdgeInsets.fromLTRB(110.0, 6.0, 110.0, 0.0),
        child: ListTile(
          leading: Icon(
              Icons.pool
          ),
          title: Text(hour),
          tileColor: Colors.blueGrey[50],
          subtitle: Text('See the pools from $hour'),
          onTap: () {
            Navigator.pushNamed(context, '/viewFeedback',
                arguments: new ViewFeedBackArguments(user, path));
          },
          trailing: IconButton(
            icon: Icon(Icons.delete),
            onPressed: () async {
              bool deleted = await logicManager.deleteFeedback(
                  user.swimmer, path.substring(1).split('/'));
              if(deleted) {
                remove();
              }
            }
          ),
        ),
      ),
    );
  }
}
