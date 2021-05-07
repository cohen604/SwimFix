import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Dates/DateTimeDTO.dart';
import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'package:client/Screens/Arguments/ViewFeedbackArguments.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:universal_html/html.dart';

import 'PopUps/MessagePopUp.dart';

class WebSwimmerHistoryDayScreen extends StatefulWidget {

  final SwimmerHistoryPoolsArguments arguments;
  WebSwimmerHistoryDayScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebSwimmerHistoryScreenState createState()
      => _WebSwimmerHistoryScreenState(
          arguments.webUser.swimmer,
          arguments.date);
}


class _WebSwimmerHistoryScreenState extends State<WebSwimmerHistoryDayScreen> {

  LogicManager _logicManager;
  WebColors _webColors;
  ScreenState _screenState;
  List<FeedBackLink> _feedbacks;

 _WebSwimmerHistoryScreenState(Swimmer swimmer, DateTimeDTO date) {
    _logicManager = LogicManager.getInstance();
    _webColors = WebColors.getInstance();
    _screenState = ScreenState.LoadingDayHistory;
    getSwimmerHistoryByDay(swimmer, date);
 }

  void getSwimmerHistoryByDay(Swimmer swimmer, DateTimeDTO day) {
    _logicManager.getSwimmerHistoryPoolsByDay(swimmer, day).then(
        (feedbacks) {
          if(feedbacks == null) {
            setState(() {
              _screenState = ScreenState.Error;
            });
          }
          else {
            setState(() {
              _feedbacks = feedbacks;
              _screenState = ScreenState.ViewDayHistory;
            });
          }
        }
    );
  }

  void onDeleteFeedback(int index) {
   Swimmer swimmer = this.widget.arguments.webUser.swimmer;
   DateTimeDTO date = this.widget.arguments.date;
   FeedBackLink link = _feedbacks[index];
    _logicManager.deleteFeedback(swimmer, date, link)
        .then((deleted) {
          if(deleted) {
            getSwimmerHistoryByDay(swimmer, date);
          }
          else {
            showDialog(
              context: context,
              builder: (BuildContext context) {
                return new MessagePopUp('Feedback failed to be deleted.\n'
                    'For more information please contact help@swimanalytics.com');
              },
            );
          }
    });
  }

  void onViewFeedback(int index) {
     WebUser user = this.widget.arguments.webUser;
     FeedBackLink link = _feedbacks[index];
     link.path = "/"+link.path.replaceAll("\\", "/");
     Navigator.pushNamed(context, '/viewFeedback',
        arguments: new ViewFeedBackArguments(user, link));
  }

  void onBack(BuildContext context) {
    Navigator.pop(context);
  }

  Widget buildLoadingHistory(BuildContext context) {
    return Center(
      child: Container(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            CircularProgressIndicator(),
            Text( 'Loading swimming day...',
              style: TextStyle(
                  fontSize: 24 * MediaQuery.of(context).textScaleFactor,
                  color: Colors.black,
                  fontWeight: FontWeight.normal,
                  decoration: TextDecoration.none
              ),
            )
          ],
        ),
      ),
    );
  }


  Widget buildError(BuildContext context) {
    return Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(Icons.error,
              color:Colors.yellow,
              size: 45,
            ),
            Text( 'Error as accrued, please try again later.',
              style: TextStyle(
                  fontSize: 24 * MediaQuery.of(context).textScaleFactor,
                  color: Colors.black,
                  fontWeight: FontWeight.normal,
                  decoration: TextDecoration.none
              ),
            )
          ],
        )
    );
  }

  Widget buildHistoryListEmpty(BuildContext context) {
    return Center(
      child: Text('There is no swimming feedbacks for the selected date.',
          textAlign: TextAlign.center,
          style: TextStyle(
              fontSize: 24 * MediaQuery
                  .of(context)
                  .textScaleFactor,
              color: Colors.black,
              fontWeight: FontWeight.normal,
              decoration: TextDecoration.none
          )
      ),
    );
  }

  Widget buildHistoryDayList(BuildContext context) {
   if(_feedbacks.length == 0) {
     return buildHistoryListEmpty(context);
   }
   DateTimeDTO date = this.widget.arguments.date;
   return ListView.builder(
    itemCount: _feedbacks.length,
    itemBuilder: (BuildContext context, int index) {
      return PoolHourTile(
        date: date,
        link: _feedbacks[index],
        user: this.widget.arguments.webUser,
        remove: ()=>onDeleteFeedback(index),
        view: ()=>onViewFeedback(index),
        color: _webColors.getBackgroundForI3(),);
      },
   );
  }

  Widget buildStateViewHistory(BuildContext context) {
   DateTimeDTO date = this.widget.arguments.date;
   return Column(
      children: [
        Center(
          child: Text('History ${date.day}.${date.month}.${date.year}',
            textAlign: TextAlign.center,
            style: TextStyle(
                fontSize: 28 * MediaQuery
                    .of(context)
                    .textScaleFactor,
                color: Colors.black,
                fontWeight: FontWeight.normal,
                decoration: TextDecoration.none
            )
          ),
        ),
        Expanded(
            child: buildHistoryDayList(context)
        ),
      ],
    );
  }

  Widget buildBackButton(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(15.0),
      child: Align(
        alignment: Alignment.topLeft,
        child: IconButton(
            icon: Icon(Icons.arrow_back,
              size: 30,
            ),
            onPressed: ()=>onBack(context),
        ),
      ),
    );
  }

  Widget buildScreenState(BuildContext context) {
   Widget child = Container();
   if(_screenState == ScreenState.LoadingDayHistory) {
      child = buildLoadingHistory(context);
   }
   else if(_screenState == ScreenState.Error) {
      child = buildError(context);
   }
   else if(_screenState == ScreenState.ViewDayHistory) {
      child = buildStateViewHistory(context);
   }
   return Column(
     children: [
       buildBackButton(context),
       Expanded(
           child: child
       ),
     ],
   );
   return Container();
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
                new Expanded(
                  child: buildScreenState(context)
                ),
              ]
          ),
        ),
      ),
    );
  }

}


class PoolHourTile extends StatelessWidget {

  final DateTimeDTO date;
  final FeedBackLink link;
  final WebUser user;
  final Function remove;
  final Function view;
  final Color color;

  PoolHourTile({ this.date, this.link, this.user,
    this.remove, this.view,
    this.color});

  Widget buildTitle(BuildContext context) {
    return Text('${link.path}',
      style: TextStyle(
          fontSize: 21 * MediaQuery.of(context).textScaleFactor,
          color: Colors.black,
          fontWeight: FontWeight.normal,
          decoration: TextDecoration.none
      ),
    );
    return Text('See the pools from $date');
  }

  Widget buildDes(BuildContext context) {
    return Text('View swimming feedback',
      style: TextStyle(
          fontSize: 16 * MediaQuery.of(context).textScaleFactor,
          color: Colors.black54,
          fontWeight: FontWeight.normal,
          decoration: TextDecoration.none
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(10),
      child: Material(
        child: ListTile(
            shape:RoundedRectangleBorder(
              side: BorderSide(color: Colors.green, width: 1),
              borderRadius: const BorderRadius.all(
                Radius.circular(20.0),
              ),
            ),
            leading: Icon(
              Icons.pool,
              color: Colors.black,
              size: 35,
            ),
            trailing: IconButton(
                icon: Icon(Icons.delete),
                onPressed: remove
            ),
            tileColor: color.withAlpha(120),
            title: buildTitle(context),
            subtitle: buildDes(context),
            onTap: view
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