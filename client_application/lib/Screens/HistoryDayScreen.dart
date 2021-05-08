import 'package:client_application/Domain/DTO/DateTimeDTO.dart';
import 'package:client_application/Domain/Users/AppUser.dart';
import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Domain/Video/FeedBackLink.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'Arguments/SwimmerHistoryPoolsArguments.dart';
import 'Arguments/ViewFeedbackArguments.dart';
import 'Drawers/BasicDrawer.dart';
import 'Holders/ColorsHolder.dart';
import 'PopUps/MessagePopUp.dart';

class HistoryDayScreen extends StatefulWidget {

  final HistoryDayScreenArguments arguments;
  HistoryDayScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebSwimmerHistoryScreenState createState()
      => _WebSwimmerHistoryScreenState(
          arguments.user.swimmer,
          arguments.date);
}


class _WebSwimmerHistoryScreenState extends State<HistoryDayScreen> {

  LogicManager _logicManager;
  ColorsHolder _webColors;
  ScreenState _screenState;
  List<FeedbackLink> _feedbacks;

 _WebSwimmerHistoryScreenState(Swimmer swimmer, DateTimeDTO date) {
    _logicManager = LogicManager.getInstance();
    _webColors = new ColorsHolder();
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
   Swimmer swimmer = this.widget.arguments.user.swimmer;
   DateTimeDTO date = this.widget.arguments.date;
   FeedbackLink link = _feedbacks[index];
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
     AppUser user = this.widget.arguments.user;
     FeedbackLink link = _feedbacks[index];
     link.path = "/"+link.path.replaceAll("\\", "/");
     Navigator.pushNamed(context, '/viewFeedback',
        arguments: new ViewFeedBackArguments(user, link));
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
        user: this.widget.arguments.user,
        remove: ()=>onDeleteFeedback(index),
        view: ()=>onViewFeedback(index),
        color: _webColors.getBackgroundForI3(),
        borderColor: _webColors.getBackgroundForI1());
      },
   );
  }

  Widget buildHistoryTitle(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.only(top: 10.0, bottom: 5),
        child: Text('Swimming feedbacks',
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
      ),
    );
  }

  Widget buildHistroyDesTitle(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.only(bottom: 5.0),
        child: Text('Select the swimming feedback to view',
            textAlign: TextAlign.center,
            style: TextStyle(
                fontSize: 16 * MediaQuery
                    .of(context)
                    .textScaleFactor,
                color: Colors.grey,
                fontWeight: FontWeight.normal,
                decoration: TextDecoration.none
            )
        ),
      ),
    );
  }

  Widget buildStateViewHistory(BuildContext context) {
   return Padding(
     padding: const EdgeInsets.only(top: 20.0),
     child: Column(
        children: [
          buildHistoryTitle(context),
          buildHistroyDesTitle(context),
          Expanded(
              child: buildHistoryDayList(context)
          ),
        ],
      ),
   );
  }

  Widget buildScreenState(BuildContext context) {
   Widget child = Container();
   if(_screenState == ScreenState.LoadingDayHistory) {
      return buildLoadingHistory(context);
   }
   else if(_screenState == ScreenState.Error) {
      return buildError(context);
   }
   else if(_screenState == ScreenState.ViewDayHistory) {
      return buildStateViewHistory(context);
   }
   return Container();
  }

  @override
  Widget build(BuildContext context) {
    DateTimeDTO date = this.widget.arguments.date;
    return SafeArea(
      child: Scaffold(
        drawer: BasicDrawer(
            this.widget.arguments.user
        ),
        appBar: AppBar(
          backgroundColor: Colors.blue,
          title: Text("History ${date.day}.${date.month}.${date.year}",),
        ),
        body: SingleChildScrollView(
          child: Container(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height,
              color: _webColors.getBackgroundForI6(),
              child: buildScreenState(context)
          ),
        ),
      ),
    );
  }

}


class PoolHourTile extends StatelessWidget {

  final DateTimeDTO date;
  final FeedbackLink link;
  final AppUser user;
  final Function remove;
  final Function view;
  final Color color;
  final Color borderColor;

  PoolHourTile({ this.date, this.link, this.user,
    this.remove, this.view,
    this.color, this.borderColor});

  String getStringTitle(String path) {
    int start = path.lastIndexOf("\\") + 1;
    int end = path.lastIndexOf(".");
    String output = path.substring(start, end);
    return output.replaceAll("-", ".");
  }

  Widget buildTitle(BuildContext context) {
    return Text('${getStringTitle(link.path)}',
      style: TextStyle(
          fontSize: 16 * MediaQuery.of(context).textScaleFactor,
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
              side: BorderSide(color: borderColor, width: 1),
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