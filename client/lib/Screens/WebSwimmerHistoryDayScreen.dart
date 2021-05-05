import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Dates/DateTimeDTO.dart';
import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'package:client/Screens/Arguments/ViewFeedbackArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class WebSwimmerHistoryDayScreen extends StatefulWidget {

  final SwimmerHistoryPoolsArguments arguments;
  WebSwimmerHistoryDayScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebSwimmerHistoryScreenState createState() => _WebSwimmerHistoryScreenState();
}


class _WebSwimmerHistoryScreenState extends State<WebSwimmerHistoryDayScreen> {

  LogicManager _logicManager;
  ScreenState _screenState;
  Map<String, List<dynamic>> _feedbacks;

 _WebSwimmerHistoryScreenState() {
    _logicManager = LogicManager.getInstance();
    _screenState = ScreenState.LoadingDayHistory;
    getSwimmerHistoryMap();
 }

  void getSwimmerHistoryMap() {
    Swimmer swimmer = this.widget.arguments.webUser.swimmer;
    DateTimeDTO day = this.widget.arguments.date;
    _logicManager.getSwimmerHistoryPoolsByDay(swimmer, day).then(
        (feedbacks) {
          if(feedbacks == null) {
            setState(() {
              _screenState = ScreenState.Error;
            });
          }
          else {
            setState(() {
              _screenState = ScreenState.ViewDayHistory;
            });
          }
        }
    );
  }

  void removeTile() {
    setState(() {
    });
  }

  Widget buildHistoryDayList(BuildContext context) {
    return  ListView.builder(
        itemCount: _feedbacks.length,
        itemBuilder: (BuildContext context, int index) {
          String hour = _feedbacks.keys.elementAt(index);
          return new PoolHourTile
            ( hour: hour,
              link: _feedbacks[hour],
              user: this.widget.arguments.webUser,
              logicManager: _logicManager,
              remove: removeTile);
        },
      );
  }

  Widget buildTopScreen(BuildContext context) {
    return Align(
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
    );
  }

  Widget buildStateViewHistory(BuildContext context) {
    return Column(
      children: [
        buildTopScreen(context),
        buildHistoryDayList(context),
      ],
    );
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
                buildTopScreen(context),
                new Expanded(
                  child: buildHistoryDayList(context)
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
  final FeedBackLink link;
  final WebUser user;
  final LogicManager logicManager;
  final Function() remove;
  PoolHourTile({ this.hour, this.link, this.user, this.logicManager, this.remove});

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
                arguments: new ViewFeedBackArguments(user, link.path));
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

enum ScreenState {
  LoadingDayHistory,
  ViewDayHistory,
  Error,
}