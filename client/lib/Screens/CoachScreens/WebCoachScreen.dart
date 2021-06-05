import 'package:client/Components/EmailInvitation.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Invitations/Invitation.dart';
import 'package:client/Domain/Team/SwimmerTeam.dart';
import 'package:client/Domain/Team/Team.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/CoachScreens/Arguments/CoachSwimmerScreenArguments.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import '../Holders/WebColors.dart';
import 'Arguments/CoachScreenArguments.dart';

class WebCoachScreen extends StatefulWidget {

  CoachScreenArguments args;

  WebCoachScreen(this.args);

  @override
  _WebCoachScreenState createState() => _WebCoachScreenState();
}

class _WebCoachScreenState extends State<WebCoachScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;
  LogicManager _logicManager ;
  ScreenState _screenState;

  Team _team;

  List<SwimmerTeam> _swimmers;
  Map<SortBySwimmer, OrderBy> _swimmerSortMap;
  SortBySwimmer _swimmerSortBy;

  List<Invitation> _invitations;
  Map<SortByInvitation, OrderBy> _invitationSortMap;
  SortByInvitation _invitationSortBy;

  TextEditingController _searchTextController;

  _WebCoachScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _logicManager = LogicManager.getInstance();
    _screenState = ScreenState.Loading;

    _searchTextController = TextEditingController();

    _swimmerSortBy = SortBySwimmer.None;
    _swimmerSortMap = {};
    for(SortBySwimmer sortBy in SortBySwimmer.values) {
      _swimmerSortMap[sortBy] = OrderBy.Ascending;
    }

    _invitationSortBy = SortByInvitation.None;
    _invitationSortMap = {};
    for(SortByInvitation sortBy in SortByInvitation.values) {
      _invitationSortMap[sortBy] = OrderBy.Ascending;
    }
  }


  @override
  void initState() {
    super.initState();
    _logicManager.getCoachTeam(this.widget.args.user.swimmer)
        .then((Team team) {
          if(team == null) {
            this.setState(() {
              _screenState = ScreenState.Error;
            });
          }
          else {
            this.setState(() {
              _team = team;
              _invitations = List.from(_team.invitations);
              _swimmers = List.from(_team.swimmers);
              _screenState = ScreenState.View;
            });
          }
    });
  }

  void onClickSwimmerTeam(BuildContext context, SwimmerTeam swimmerTeam) {
    SchedulerBinding.instance.addPostFrameCallback((_) {
      Navigator.pushNamed(context, '/coach/swimmer',
          arguments: new CoachSwimmerScreenArguments(this.widget.args.user, swimmerTeam));
    });
  }

  void updateTeam() {
    _logicManager.getCoachTeam(this.widget.args.user.swimmer)
        .then((Team team) {
      if(team != null) {
        this.setState(() {
          _team = team;
          _invitations = List.from(_team.invitations);
          _swimmers = List.from(_team.swimmers);
        });
      }
    });
  }

  void onAddSwimmer(BuildContext context) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
          content: EmailInvitation(
            this.widget.args.user,
            ()=>updateTeam()
          )
      ),
    );
  }

  void swimmerSortBy(SortBySwimmer newSortBy) {
    if(newSortBy == SortBySwimmer.Email) {
      _swimmers.sort((a, b)=>a.email.compareTo(b.email));
    }
    else if(newSortBy == SortBySwimmer.Feedbacks) {
      _swimmers.sort((a, b)=>a.feedbacksCounter.compareTo(b.feedbacksCounter));
    }
    else { //filterByState == FilterByState.None
      _swimmers = List.from(_team.swimmers);
    }
    //now ascending descending
    if(newSortBy != SortBySwimmer.None
        && _swimmerSortMap[newSortBy] == OrderBy.Descending) {
      _swimmers = List.from(_swimmers.reversed);
    }
    // None
    this.setState(() {
      _swimmerSortBy = newSortBy;
    });
  }

  void onClickSwimmersButtonSort(SortBySwimmer newSortBy) {
    if(newSortBy != SortBySwimmer.None && _swimmerSortBy == newSortBy) {
      if(_swimmerSortMap[newSortBy] == OrderBy.Descending){
        _swimmerSortMap[newSortBy] = OrderBy.Ascending;
      }
      else if(_swimmerSortMap[newSortBy] == OrderBy.Ascending) {
        _swimmerSortMap[newSortBy] = OrderBy.Descending;
      }
    }
    swimmerSortBy(newSortBy);
  }

  void sortByInvitation(SortByInvitation newSortBy) {
    if(newSortBy == SortByInvitation.ID) {
      _invitations.sort((a, b)=>a.id.compareTo(b.id));
    }
    else if(newSortBy == SortByInvitation.TeamId) {
      _invitations.sort((a, b)=>a.teamId.compareTo(b.teamId));
    }
    else if(newSortBy == SortByInvitation.Date) {
      _invitations.sort((a, b)=>a.date.compareTo(b.date));
    }
    else if(newSortBy == SortByInvitation.Status) {
      _invitations.sort((a,b)=>a.compareTo(b));
    }
    else { //filterByState == FilterByState.None
      _invitations = List.from(_team.invitations);
    }
    //now ascending descending
    if(newSortBy != SortByInvitation.None
        && _invitationSortMap[newSortBy] == OrderBy.Descending) {
      _invitations = List.from(_invitations.reversed);
    }
    // None
    this.setState(() {
      _invitationSortBy = newSortBy;
    });
  }

  void onClickButtonSortInvitation(SortByInvitation newSortBy) {
    if(newSortBy != SortByInvitation.None && _invitationSortBy == newSortBy) {
      if(_invitationSortMap[newSortBy] == OrderBy.Descending){
        _invitationSortMap[newSortBy] = OrderBy.Ascending;
      }
      else if(_invitationSortMap[newSortBy] == OrderBy.Ascending) {
        _invitationSortMap[newSortBy] = OrderBy.Descending;
      }
    }
    sortByInvitation(newSortBy);
  }

  void showMessageServerError(BuildContext context) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
          content: buildError(context),
          )
      );
  }

  void showMessageSwimmerFailedToRemove(BuildContext context, String email) {
    showDialog(
        context: context,
        builder: (_) => AlertDialog(
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Icon(
                Icons.warning_amber_outlined,
                color: Colors.yellow,
                size: 35,
              ),
              SizedBox(height: 10,),
              buildText(context, 'Failed to remove $email from team', 24, Colors.black, FontWeight.normal),
            ],
          ),
        )
    );
  }

  void showMessageSwimmerRemoved(BuildContext context, String email) {
    showDialog(
        context: context,
        builder: (_) => AlertDialog(
          content: buildText(context, 'Removed $email from team', 24, Colors.black, FontWeight.normal),
        )
    );
  }

  void onClickRemoveSwimmer(BuildContext context, SwimmerTeam swimmerTeam) {
    Swimmer coach = this.widget.args.user.swimmer;
    String swimmersEmail = swimmerTeam.email;
    _logicManager.coachRemoveSwimmer(coach, swimmersEmail).then(
        (bool removed) {
          if(removed == null) {
            showMessageServerError(context);
          }
          else if(removed) {
            updateTeam();
            showMessageSwimmerRemoved(context, swimmersEmail);
          }
          else {
            showMessageServerError(context);
          }
        }
    );
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
            buildText(context, 'Loading team...', 24, Colors.black, FontWeight.normal),
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

  Widget buildSwimmersEmptyList(BuildContext context) {
    return Center(
      child: buildText(
          context,
          'There are not swimmers yet. Try Adding Swimmers to your team.',
          24,
          Colors.black,
          FontWeight.normal
      ),
    );
  }

  Widget buildSwimmer(BuildContext context, SwimmerTeam swimmerTeam) {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Card(
        child: Padding(
          padding: const EdgeInsets.all(5.0),
          child: ListTile(
            leading: Icon(
              Icons.person,
              size: 35,
              color: _webColors.getBackgroundForI2(),
            ),
            title: buildText(
                context, swimmerTeam.email, 24, Colors.black,
                FontWeight.normal, textAlign: TextAlign.left),
            subtitle: buildText( context,
              'Number of feedbacks: ${swimmerTeam.feedbacksCounter}',
              21, Colors.black45, FontWeight.normal, textAlign: TextAlign.left
            ),
            trailing: IconButton(
              icon: Icon(Icons.delete_outline),
              iconSize: 35,
              color: Colors.red,
              onPressed: ()=>onClickRemoveSwimmer(context, swimmerTeam),
            ),
            onTap: ()=>onClickSwimmerTeam(context, swimmerTeam),
          ),
        ),
      ),
    );
  }

  Widget buildSwimmersList(BuildContext context) {
    if(_swimmers.isEmpty) {
      return buildSwimmersEmptyList(context);
    }
    return ListView.builder(
      itemCount: _swimmers.length,
      itemBuilder: (BuildContext context, int index) {
        return buildSwimmer(context, _swimmers[index]);
      });
  }

  Widget buildIconSortSwimmers(BuildContext context, SortBySwimmer sortByValue) {
    if(_swimmerSortMap[sortByValue] == OrderBy.Ascending) {
      return Icon(
        Icons.expand_more,
        size: 21,
        color: _swimmerSortBy == sortByValue ? _webColors.getBackgroundForI2() : Colors.black,
      );
    }
    else if(_swimmerSortMap[sortByValue] == OrderBy.Descending) {
      return Icon(
        Icons.expand_less,
        size: 21,
        color: _swimmerSortBy == sortByValue ? _webColors.getBackgroundForI2() : Colors.black,
      );
    }
    return Container();
  }

  Widget buildButtonSortSwimmers(BuildContext context, String title, SortBySwimmer sortByValue) {
    return TextButton(
        onPressed: ()=>onClickSwimmersButtonSort(sortByValue),
        child: Row(
          children: [
            buildText(context, title, 21,
                _swimmerSortBy == sortByValue ? _webColors.getBackgroundForI2() : Colors.black,
                FontWeight.normal
            ),
            buildIconSortSwimmers(context, sortByValue),
          ],
        )
    );
  }

  Widget buildBarSortSwimmers(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      color: _webColors.getBackgroundForI3(),
      padding: const EdgeInsets.only(right: 10, left: 10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Flexible(
              child: buildButtonSortSwimmers(context, 'Email', SortBySwimmer.Email)
          ),
          Flexible(
              child: buildButtonSortSwimmers(context, 'Feedbacks', SortBySwimmer.Feedbacks)
          ),
          IconButton(
              onPressed: ()=>onClickSwimmersButtonSort(SortBySwimmer.None),
              icon: Icon(
                Icons.cancel_outlined,
                color: Colors.redAccent,
              )
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: IconButton(
                onPressed: ()=>onAddSwimmer(context),
                color: _webColors.getBackgroundForI1(),
                splashColor: _webColors.getBackgroundForI4(),
                hoverColor: _webColors.getBackgroundForI6(),
                iconSize: 35,
                icon: Icon( Icons.person_add)
            ),
          ),
        ],
      ),
    );
  }

  Widget buildTeamInfo(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      padding: const EdgeInsets.all(5),
      color: _webColors.getBackgroundForI6(),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          buildText(context, _team.name, 42, _webColors.getBackgroundForI2(), FontWeight.normal),
          buildText(context, _team.date.toString(), 21, Colors.black87, FontWeight.normal),
          buildText(context, _team.coach, 21, Colors.black87, FontWeight.normal),
        ],
      ),
    );
  }

  Widget buildLeftSideView(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(
          left: 10.0, right: 5, top:5, bottom: 5),
      child: Column(
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          buildTeamInfo(context),
          SizedBox(height: 10,),
          buildBarSortSwimmers(context),
          Expanded(
            child: Container(
                color: Colors.black.withAlpha(120),
                child: buildSwimmersList(context)
            ),
          ),
        ],
      ),
    );
  }

  Widget buildEmptyInvitations(BuildContext context) {
    return Center(
      child: buildText(
          context,
          'There are not invitations yet. Try sending invitations.',
          24,
          Colors.black,
          FontWeight.normal
      ),
    );
  }

  Widget buildTrialingInvitation(BuildContext context, Invitation invitation) {
    if(invitation.isApprove) {
      return buildText(context, 'Approved', 18, Colors.green, FontWeight.bold);
    }
    if(invitation.isDenied) {
      return buildText(context, 'Denied', 18, Colors.redAccent, FontWeight.bold);
    }
    if(invitation.isPending) {
      return buildText(context, 'Pending', 18, Colors.orangeAccent, FontWeight.bold);
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
              size: 35,
              color: _webColors.getBackgroundForI2(),
            ),
            title: buildText(context, invitation.email, 21, Colors.black, FontWeight.normal,
                textAlign: TextAlign.left),
            subtitle: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                buildText(context, 'Id: ${invitation.id}', 18, Colors.black87, FontWeight.normal,
                    textAlign: TextAlign.left),
                buildText(context, 'Date: ${invitation.date.toString()}', 18, Colors.black87, FontWeight.normal,
                    textAlign: TextAlign.left),
                buildText(context, 'Team: ${invitation.teamId}', 18, Colors.black87, FontWeight.normal,
                    textAlign: TextAlign.left),
              ],
            ),
            trailing: buildTrialingInvitation(context, invitation),
          ),
        ),
      ),
    );
  }

  Widget buildInvitations(BuildContext context) {
    if(_invitations.isEmpty) {
      return buildEmptyInvitations(context);
    }
    return ListView.builder(
      itemCount: _invitations.length,
      itemBuilder: (BuildContext context, int index) {
        return buildInvitation(context, _invitations[index]);
      });
  }

  Widget buildIconSortInvitation(BuildContext context, SortByInvitation sortByValue) {
    if(_invitationSortMap[sortByValue] == OrderBy.Ascending) {
      return Icon(
        Icons.expand_more,
        size: 21,
        color: _invitationSortBy == sortByValue ? _webColors.getBackgroundForI2() : Colors.black,
      );
    }
    else if(_invitationSortMap[sortByValue] == OrderBy.Descending) {
      return Icon(
        Icons.expand_less,
        size: 21,
        color: _invitationSortBy == sortByValue ? _webColors.getBackgroundForI2() : Colors.black,
      );
    }
    return Container();
  }

  Widget buildButtonSortInvitation(BuildContext context, String title, SortByInvitation sortByValue) {
    return TextButton(
        onPressed: ()=>onClickButtonSortInvitation(sortByValue),
        child: Row(
          children: [
            buildText(context, title, 21,
                _invitationSortBy == sortByValue ? _webColors.getBackgroundForI2() : Colors.black,
                FontWeight.normal
            ),
            buildIconSortInvitation(context, sortByValue),
          ],
        )
    );
  }

  Widget buildBarSortInvitations(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      color: _webColors.getBackgroundForI3(),
      padding: const EdgeInsets.only(right: 10, left: 10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          buildButtonSortInvitation(context, 'ID', SortByInvitation.ID),
          buildButtonSortInvitation(context, 'Team', SortByInvitation.TeamId),
          buildButtonSortInvitation(context, 'Date', SortByInvitation.Date),
          buildButtonSortInvitation(context, 'Status', SortByInvitation.Status),
          IconButton(
              onPressed: ()=>onClickButtonSortInvitation(SortByInvitation.None),
              icon: Icon(
                Icons.cancel_outlined,
                color: Colors.redAccent,
              )
          ),
        ],
      ),
    );
  }

  Widget buildRightSideView(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(
          top:15, right: 5.0, left: 5, bottom: 5),
      child: Column(
        mainAxisSize: MainAxisSize.max,
        children: [
          buildText(context, 'Invitations', 26, _webColors.getBackgroundForI1(), FontWeight.normal),
          buildBarSortInvitations(context),
          Expanded(
              child: Container(
                  color: Colors.black.withAlpha(120),
                  child: buildInvitations(context)
              )
          ),
        ],
      ),
    );
  }

  Widget buildView(BuildContext context) {
    return Row(
      mainAxisSize: MainAxisSize.max,
      children: [
        Flexible(
          flex: 2,
          child: buildLeftSideView(context)
        ),
        Flexible(
            child: buildRightSideView(context),
        )
      ],
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

enum SortBySwimmer {
  None,
  Email,
  Feedbacks,
}

enum SortByInvitation {
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
