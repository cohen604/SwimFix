import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Domain/Feedback/FeedbackInfo.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/CoachScreens/Arguments/CoachFeedbackScreenArguments.dart';
import 'package:client/Screens/CoachScreens/Arguments/CoachSwimmerScreenArguments.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class WebCoachSwimmerScreen extends StatefulWidget {

  final CoachSwimmerScreenArguments args;

  WebCoachSwimmerScreen(this.args);

  @override
  _WebCoachSwimmerScreenState createState() => _WebCoachSwimmerScreenState();
}

class _WebCoachSwimmerScreenState extends State<WebCoachSwimmerScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;
  LogicManager _logicManager;
  ScreenState _screenState;
  SortBy _sortBy;
  Map<SortBy, OrderBy> _sortMap;
  List<FeedbackInfo> _origin;
  List<FeedbackInfo> _feedbacks;

  _WebCoachSwimmerScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _logicManager = LogicManager.getInstance();
    _screenState = ScreenState.Loading;
    _sortBy = SortBy.None;
    _sortMap = {};
    for(SortBy sortBy in SortBy.values) {
      _sortMap[sortBy] = OrderBy.Ascending;
    }
  }

  @override
  void initState() {
    super.initState();
    Swimmer coach = this.widget.args.user.swimmer;
    String swimmersEmail = this.widget.args.swimmerTeam.email;
    _logicManager.coachGetSwimmerFeedbacks(coach, swimmersEmail).then(
        (List<FeedbackInfo> feedbacks) {
          if(feedbacks == null) {
            this.setState(() {
              _screenState = ScreenState.Error;
            });
          }
          else {
            this.setState(() {
              _feedbacks = feedbacks;
              _screenState = ScreenState.View;
            });
          }
        }
    );
  }

  void onClickFeedback(BuildContext context, FeedbackInfo info) {
    SchedulerBinding.instance.addPostFrameCallback((_) {
      Navigator.pushNamed(context, '/coach/swimmer/feedback',
          arguments: new CoachFeedbackScreenArguments(this.widget.args.user, info));
    });
  }

  void sortBy(SortBy newSortBy) {
    if(newSortBy == SortBy.Date) {
      _feedbacks.sort((a, b)=>a.date.compareTo(b.date));
    }
    else if(newSortBy == SortBy.SwimmingErrors) {
      _feedbacks.sort((a, b)=>a.numberOfErrors.compareTo(b.numberOfErrors));
    }
    else if(newSortBy == SortBy.Comments) {
      _feedbacks.sort((a, b)=>a.numberOfComments.compareTo(b.numberOfComments));
    }
    else { //filterByState == FilterByState.None
      _feedbacks = List.from(_origin);
    }
    //now ascending descending
    if(newSortBy != SortBy.None && _sortMap[newSortBy] == OrderBy.Descending) {
      _feedbacks = List.from(_feedbacks.reversed);
    }
    // None
    this.setState(() {
      _sortBy = newSortBy;
    });
  }

  void onClickButtonSort(SortBy newSortBy) {
    if(newSortBy != SortBy.None && _sortBy == newSortBy) {
      if(_sortMap[newSortBy] == OrderBy.Descending){
        _sortMap[newSortBy] = OrderBy.Ascending;
      }
      else if(_sortMap[newSortBy] == OrderBy.Ascending) {
        _sortMap[newSortBy] = OrderBy.Descending;
      }
    }
    sortBy(newSortBy);
  }

  Widget buildText(
      BuildContext context,
      String text,
      int size,
      Color color,
      FontWeight fontWeight,
      {textAlign = TextAlign.center}) {
    return Text(text,
      textAlign: textAlign,
      style: TextStyle(
          fontSize: size * MediaQuery.of(context).textScaleFactor,
          color: color,
          fontWeight: fontWeight,
          decoration: TextDecoration.none
      ),
    );
  }

  Widget buildLoading(BuildContext context) {
    return Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            CircularProgressIndicator(),
            SizedBox(height: 5.0,),
            buildText(context,
                'Loading ${this.widget.args.swimmerTeam.email} feedbacks...',
                24,
                Colors.black,
                FontWeight.normal),
          ],
        )
    );
  }

  Widget buildError(BuildContext context) {
    return Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(
              Icons.warning_amber_rounded,
              size: 45,
              color: Colors.red,
            ),
            SizedBox(height: 5.0,),
            buildText(context, 'Something is broken.\n'
                'Maybe the you don\'t have permissions or the servers are down.\n'
                'For more information contact swimAnalytics@gmail.com',
                24, Colors.black, FontWeight.normal,
                textAlign: TextAlign.center),
          ],
        )
    );
  }

  Widget buildEmptyFeedbacks(BuildContext context) {
    return Center(
      child: buildText(
          context,
          'Swimmer doesn\'t have feedbacks. Please select another swimmer',
          24,
          Colors.black,
          FontWeight.normal
      ),
    );
  }

  Widget buildFeedback(BuildContext context, FeedbackInfo feedbackInfo) {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Card(
        child: Padding(
          padding: EdgeInsets.all(5),
          child: ListTile(
            leading: Icon(
              Icons.pool,
              color: _webColors.getBackgroundForI1(),
              size: 45
            ),
            title: buildText(context, feedbackInfo.date.toString(), 24,
              Colors.black, FontWeight.normal, textAlign: TextAlign.left),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisSize: MainAxisSize.min,
              children: [
                buildText(context, 'Number of errors: ${feedbackInfo.numberOfErrors}',
                    21, Colors.black54, FontWeight.normal),
                buildText(context, 'Number of comments: ${feedbackInfo.numberOfComments}',
                    21, Colors.black54, FontWeight.normal)
              ],
            ),
            trailing: Icon(
                Icons.videocam,
                color: _webColors.getBackgroundForI2(),
                size: 45
            ),
            onTap: ()=>onClickFeedback(context, feedbackInfo),
          ),
        ),
      ),
    );
  }

  Widget buildFeedbacks(BuildContext context) {
    if(_feedbacks.isEmpty) {
      return buildEmptyFeedbacks(context);
    }
    return ListView.builder(
        itemCount: _feedbacks.length,
        itemBuilder: (BuildContext context, int index) {
          return buildFeedback(context, _feedbacks[index]);
      });
  }

  Widget buildIconSort(BuildContext context, SortBy sortByValue) {
    if(_sortMap[sortByValue] == OrderBy.Ascending) {
      return Icon(
        Icons.expand_more,
        size: 21,
        color: _sortBy == sortByValue ? _webColors.getBackgroundForI2() : Colors.black,
      );
    }
    else if(_sortMap[sortByValue] == OrderBy.Descending) {
      return Icon(
        Icons.expand_less,
        size: 21,
        color: _sortBy == sortByValue ? _webColors.getBackgroundForI2() : Colors.black,
      );
    }
    return Container();
  }

  Widget buildButtonSort(BuildContext context, String title, SortBy sortByValue) {
    return TextButton(
        onPressed: ()=>onClickButtonSort(sortByValue),
        child: Row(
          children: [
            buildText(context, title, 21,
                _sortBy == sortByValue ? _webColors.getBackgroundForI2() : Colors.black,
                FontWeight.normal
            ),
            buildIconSort(context, sortByValue),
          ],
        )
    );
  }

  Widget buildFeedbackBar(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      color: _webColors.getBackgroundForI3(),
      padding: const EdgeInsets.only(right: 10, left: 10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          buildButtonSort(context, 'Date', SortBy.Date),
          buildButtonSort(context, 'Comments', SortBy.Comments),
          buildButtonSort(context, 'Swimming errors', SortBy.SwimmingErrors),
          IconButton(
              onPressed: ()=>onClickButtonSort(SortBy.None),
              icon: Icon(
                Icons.cancel_outlined,
                color: Colors.redAccent,
              )
          ),
        ],
      ),
    );
  }

  Widget buildView(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Column(
        children: [
          buildText(context, '${this.widget.args.swimmerTeam.email} feedbacks',
              32, _webColors.getBackgroundForI2(), FontWeight.normal),
          SizedBox(height: 10,),
          buildFeedbackBar(context),
          Expanded(
              child: Container(
                color: Colors.black.withAlpha(120),
                child: buildFeedbacks(context),
              ),
          ),
        ],
      ),
    );
  }

  Widget buildScreenState(BuildContext context) {
    if(_screenState == ScreenState.Loading) {
      return buildLoading(context);
    }
    else if(_screenState == ScreenState.Error) {
      return buildError(context);
    }
    else if(_screenState == ScreenState.View) {
      return buildView(context);
    }
    return Container();
  }

  Widget buildMainArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getBackGroundImage()),
          fit: BoxFit.fill,
        ),
      ),
      child: buildScreenState(context),
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
                user: this.widget.args.user,
              ),
              Expanded(
                child: SingleChildScrollView(
                    child: Scrollbar(
                        child: buildMainArea(context)
                    )
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}

enum ScreenState {
  Loading,
  Error,
  View
}

enum SortBy {
  None,
  Date,
  Comments,
  SwimmingErrors,
}

enum OrderBy {
  Ascending,
  Descending
}