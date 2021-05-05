import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/Arguments/SwimmerHistoryPoolsArguments.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:universal_html/html.dart';
import 'Arguments/HistoryScreenArguments.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class WebSwimmerHistoryScreen extends StatefulWidget {

  final HistoryScreenArguments arguments;
  WebSwimmerHistoryScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebSwimmerHistoryScreenState createState() => _WebSwimmerHistoryScreenState();
}


class _WebSwimmerHistoryScreenState extends State<WebSwimmerHistoryScreen> {

  LogicManager _logicManager;
  ScreenState _screenState;
  WebColors _webColors;
  List<String> days;

  _WebSwimmerHistoryScreenState() {
    _logicManager = LogicManager.getInstance();
    _screenState = ScreenState.LoadingHistory;
    _webColors = WebColors.getInstance();
  }

  @override
  void initState() {
    super.initState();
    getSwimmerHistoryMap().then((days) {
        if(days != null) {
          this.setState(() {
            _screenState = ScreenState.ViewHistory;
            this.days = days;
          });
        }
        else {
          this.setState(() {
            _screenState = ScreenState.Error;
          });
        }
      }
    );
  }

  Future<List<String>> getSwimmerHistoryMap() async {
    Swimmer swimmer = this.widget.arguments.user.swimmer;
    List<String> days = await _logicManager.getSwimmerHistoryDays(swimmer);
    return days;
  }

  void onTapDay(int index) {
    Navigator.pushNamed(context, '/history/day',
        arguments: new SwimmerHistoryPoolsArguments(
            this.widget.arguments.user, this.days[index]));
  }

  Widget buildLoadingHistory(BuildContext context) {
    return Center(
      child: Container(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            CircularProgressIndicator(),
            Text( 'Loading History...',
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

  Widget buildHistoryListEmpty(BuildContext context) {
    return Center(
      child: Text('There is no swimming feedbacks. \n'
          'Try uploading a swimming video or film with Swim Analytics mobile application.',
          textAlign: TextAlign.center,
          style: TextStyle(
              fontSize: 24 * MediaQuery.of(context).textScaleFactor,
              color: Colors.black,
              fontWeight: FontWeight.normal,
              decoration: TextDecoration.none
          )
      ),
    );
  }

  Widget buildHistoryList(BuildContext context) {
    if(this.days.length == 0) {
      return buildHistoryListEmpty(context);
    }
    return Container(
      padding: EdgeInsets.all(10),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Padding(
            padding: const EdgeInsets.all(10.0),
            child: Text('History',
              style: TextStyle(
                fontSize: 32 * MediaQuery.of(context).textScaleFactor,
                color: Colors.black,
                fontWeight: FontWeight.normal,
                decoration: TextDecoration.none
              ),
            ),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: this.days.length,
              itemBuilder: (BuildContext context, int index) {
                return PoolDateTile(
                  date: this.days[index],
                  onTap: () => onTapDay(index),
                  color: _webColors.getBackgroundForI3(),
                );
              },
            ),
          ),
        ],
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

  Widget buildScreenStates(BuildContext context) {
    if(_screenState == ScreenState.LoadingHistory) {
      return buildLoadingHistory(context);
    }
    else if(_screenState == ScreenState.ViewHistory) {
      return buildHistoryList(context);
    }
    else if(_screenState == ScreenState.Error) {
      return buildError(context);
    }
    return Container();
  }


  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        color: _webColors.getBackgroundForI6(),
        child: Column(
          children: [
            MenuBar(
              user: this.widget.arguments.user,
            ),
            new Expanded(
              child: buildScreenStates(context),
            ),
          ]
        ),
      ),
    );
  }

}

class PoolDateTile extends StatelessWidget {

  final String date;
  final Function onTap;
  final Color color;

  PoolDateTile({ this.date, this.onTap, this.color });

  Widget buildTitle(BuildContext context) {
    return Text('$date',
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
    return Text('See the pools from $date',
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
            Icons.video_collection_outlined,
            color: Colors.black,
            size: 35,
          ),
          trailing: Icon(
            Icons.list,
            color: Colors.black,
            size: 35,
          ),
          tileColor: color.withAlpha(120),
          title: buildTitle(context),
          subtitle: buildDes(context),
          onTap: onTap
        ),
      ),
    );
  }
}

enum ScreenState {
  LoadingHistory,
  ViewHistory,
  Error,
}
