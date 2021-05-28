import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Invitations/Invitation.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/InvitationHistoryArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class WebInvitationHistoryScreen extends StatefulWidget {

  InvitationHistoryArguments args;

  WebInvitationHistoryScreen(this.args);

  @override
  _WebInvitationHistoryScreenState createState() => _WebInvitationHistoryScreenState();
}

class _WebInvitationHistoryScreenState extends State<WebInvitationHistoryScreen> {


  WebColors _webColors;
  AssetsHolder _assetsHolder;
  LogicManager _logicManager;
  ScreenState _screenState;
  List<Invitation> _origin;
  List<Invitation> _invitations;
  Map<SortBy, OrderBy> _sortMap;
  SortBy _sortBy;

  _WebInvitationHistoryScreenState() {
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
    _logicManager.getInvitationsHistory(this.widget.args.user.swimmer).then(
            (List<Invitation> invitations) {
          if(invitations == null) {
            this.setState(() {
              _screenState = ScreenState.Error;
            });
          }
          else {
            this.setState(() {
              _screenState = ScreenState.View;
              _origin = invitations;
              _invitations = List.from(invitations);
            });
          }
        }
    );
  }

  void sortBy(SortBy newSortBy) {
    if(newSortBy == SortBy.ID) {
      _invitations.sort((a, b)=>a.id.compareTo(b.id));
    }
    else if(newSortBy == SortBy.TeamId) {
      _invitations.sort((a, b)=>a.teamId.compareTo(b.teamId));
    }
    else if(newSortBy == SortBy.Date) {
      _invitations.sort((a, b)=>a.date.compareTo(b.date));
    }
    else if(newSortBy == SortBy.Status) {
      _invitations.sort((a,b)=>a.compareTo(b));
    }
    else { //filterByState == FilterByState.None
      _invitations = List.from(_origin);
    }
    //now ascending descending
    if(newSortBy != SortBy.None
        && _sortMap[newSortBy] == OrderBy.Descending) {
      _invitations = List.from(_invitations.reversed);
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
            buildText(context, 'Loading pending invitations...', 24, Colors.black, FontWeight.normal),
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

  Widget buildBarSort(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      color: _webColors.getBackgroundForI3(),
      padding: const EdgeInsets.only(right: 10, left: 10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          buildButtonSort(context, 'ID', SortBy.ID),
          buildButtonSort(context, 'Team', SortBy.TeamId),
          buildButtonSort(context, 'Date', SortBy.Date),
          buildButtonSort(context, 'Status', SortBy.Status),
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

  Widget buildTrialingInvitation(BuildContext context, Invitation invitation) {
    if(invitation.isApprove) {
      return buildText(context, 'Approved', 24, Colors.green, FontWeight.normal);
    }
    if(invitation.isDenied) {
      return buildText(context, 'Denied', 24, Colors.redAccent, FontWeight.normal);
    }
    if(invitation.isPending) {
      return buildText(context, 'Pending', 24, Colors.orangeAccent, FontWeight.normal);
    }
    return Container();
  }

  Widget buildInvitation(BuildContext context, Invitation invitation) {
    return Padding(
      padding: const EdgeInsets.all(5.0),
      child: Card(
        child: Padding(
          padding: const EdgeInsets.all(5.0),
          child: ListTile(
            leading: Icon(
              Icons.mail_outline,
              size: 45,
              color: _webColors.getBackgroundForI2(),
            ),
            title: buildText(context, invitation.id, 24, _webColors.getBackgroundForI2(), FontWeight.normal,
                textAlign: TextAlign.left),
            subtitle: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                buildText(context, 'Date: ${invitation.date.toString()}', 21, Colors.black87, FontWeight.normal,
                    textAlign: TextAlign.left),
                buildText(context, 'Team: ${invitation.teamId}', 21, Colors.black87, FontWeight.normal,
                    textAlign: TextAlign.left),
              ],
            ),
            trailing: buildTrialingInvitation(context, invitation),
          ),
        ),
      ),
    );
  }

  Widget buildInvitationsEmpty(BuildContext context) {
    return Material(
      color: Colors.black.withAlpha(100),
      child: Center(
        child: Card(
          child: Padding(
            padding: const EdgeInsets.all(10.0),
            child: buildText(
                context,
                'There are no invitations yet. Talk to your coach.',
                26,
                Colors.black,
                FontWeight.normal),
          ),
        ),
      ),
    );
  }

  Widget buildInvitationList(BuildContext context) {
    if(_invitations.isEmpty) {
      return buildInvitationsEmpty(context);
    }
    return Material(
        color: Colors.black.withAlpha(100),
        child: ListView.builder(
            itemCount: _invitations.length,
            itemBuilder: (BuildContext context, int index) {
              return buildInvitation(context,_invitations[index]);
            })
    );
  }

  Widget buildView(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.only(top: 10.0, bottom: 10),
            child: buildText(context, 'Invitations history', 32, Colors.black, FontWeight.normal),
          ),
          buildBarSort(context),
          Expanded(
              child: buildInvitationList(context)
          ),
        ],
      ),
    );
  }

  Widget buildScreenState(BuildContext context) {
    if(_screenState == ScreenState.Loading) {
      return buildLoading(context);
    }
    else if(_screenState == ScreenState.View) {
      return buildView(context);
    }
    else if(_screenState == ScreenState.Error) {
      return buildError(context);
    }
    return Container();
  }

  Widget buildMainArea(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(_assetsHolder.getSwimmerBackGround()),
          fit: BoxFit.fill,
          colorFilter: ColorFilter.mode(
              _webColors.getBackgroundForI6(),
              BlendMode.hardLight),
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
  View,
  Error,
}

enum SortBy {
  None,
  ID,
  TeamId,
  Date,
  Status,
}

enum OrderBy {
  Ascending,
  Descending
}

