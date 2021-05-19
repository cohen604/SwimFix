import 'package:client/Components/Buttons/IconCardButton.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/AdminScreens/Arguments/AddAdminsScreenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/AddResearcherScreenArguments.dart';
import 'package:client/Screens/AdminScreens/Arguments/StatisticsScreenArguments.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

import 'Arguments/AdminSceenArguments.dart';

class WebAddAdminsScreen extends StatefulWidget {

  AddAdminsScreenArguments args;

  WebAddAdminsScreen(this.args);

  @override
  _WebAddAdminsScreenState createState() => _WebAddAdminsScreenState();
}

class _WebAddAdminsScreenState extends State<WebAddAdminsScreen> {

  WebColors _webColors;
  AssetsHolder _assetsHolder;
  LogicManager _logicManager;
  ScreenState _screenState;
  List<Swimmer> _users;


  @override
  void initState() {
    super.initState();
    getUsersList();
  }

  _WebAddAdminsScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _logicManager = LogicManager.getInstance();
    _screenState = ScreenState.Loading;
    _users = [];
  }

  void getUsersList() {
    _logicManager.getUsersThatNotAdmins(this.widget.args.user.swimmer).then(
        (users) {
          if(users == null) {
            setState(() {
              _screenState = ScreenState.Error;
            });
          }
          else {
            setState(() {
              _users = users;
              _screenState = ScreenState.View;
            });
          }
        }
    );
  }

  void OnAddSwimmerAsAdmin(Swimmer swimmer) {
    print('Swimmer added as admin');
  }

  void onSelectedSwimmer(BuildContext context, Swimmer swimmer) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        content: buildText(context,
          'Are you sure you want to add ${swimmer.name} as admin?',
          24, Colors.black, FontWeight.normal),
        actions: [
          TextButton(
              onPressed: ()=>Navigator.pop(context),
              child: buildText(context, 'No', 21, _webColors.getBackgroundForI2(), FontWeight.normal),
          ),
          TextButton(
              onPressed: ()=>OnAddSwimmerAsAdmin(swimmer),
              child: buildText(context, 'Yes', 21, _webColors.getBackgroundForI2(), FontWeight.normal),
          ),
          SizedBox(width: 5,)
        ],
      )
    );
  }

  Widget buildText(
      BuildContext context,
      String text,
      int size,
      Color color,
      FontWeight fontWeight,
      {textAlign = TextAlign.left}) {
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

  Widget buildLoadingState(BuildContext context) {
    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          CircularProgressIndicator(),
          SizedBox(height: 5.0,),
          buildText(context, 'Loading users...', 24, Colors.black, FontWeight.normal),
        ],
      )
    );
  }

  Widget buildErrorState(BuildContext context) {
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

  Widget buildEmptyList(BuildContext context) {
    return Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            buildText(context, 'All users are admins.\nTalk to your IT manager soon as possible.',
                24, Colors.black, FontWeight.normal,
              textAlign: TextAlign.center),
          ],
        )
    );
  }

  Widget buildUser(BuildContext context, Swimmer swimmer) {
    return Card(
      child: ListTile(
        leading: Icon(
          Icons.person,
          size: 55,
        ),
        title: buildText(context, '${swimmer.name}', 24, Colors.black, FontWeight.normal),
        subtitle: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            buildText(context, 'email: ${swimmer.email}', 18, Colors.black, FontWeight.normal),
            buildText(context, 'uid: ${swimmer.uid}', 18, Colors.black, FontWeight.normal),
          ],
        ),
        hoverColor: _webColors.getBackgroundForI3().withAlpha(120),
        onTap:()=>onSelectedSwimmer(context, swimmer),
      ),
    );
  }

  Widget buildUsersList(BuildContext context) {
    return Material(
      color: Colors.transparent,
      child: ListView.builder(
          itemCount: _users.length,
          itemBuilder: (BuildContext context, int index) {
            Swimmer swimmer = _users[index];
            return  Container(
                margin: const EdgeInsets.all(10.0),
                decoration: BoxDecoration(
                  boxShadow: [
                    BoxShadow(
                      color: Colors.grey.withOpacity(0.3),
                      spreadRadius: 1,
                      blurRadius: 3,
                      offset: Offset(0, 3), // changes position of shadow
                    ),
                  ],
                ),
                child: buildUser(context, swimmer)
            );
          }
      ),
    );
  }

  Widget buildViewState(BuildContext context) {
    if(_users.isEmpty) {
      return buildEmptyList(context);
    }
    return Padding(
      padding: EdgeInsets.all(10.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          buildText(context, 'Select user to add as admin', 32, Colors.black, FontWeight.normal),
          Expanded(
              child: buildUsersList(context)
          )
        ],
      ),
    );
  }

  Widget buildScreenState(BuildContext context) {
    if(_screenState == ScreenState.Loading) {
      return buildLoadingState(context);
    }
    if(_screenState == ScreenState.Error) {
      return buildErrorState(context);
    }
    if(_screenState == ScreenState.View) {
      return buildViewState(context);
    }
    return Container();
  }

  Widget buildMainArea(BuildContext context) {
    return Container(
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
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Column(
          children: [
            MenuBar(user: this.widget.args.user,),
            Expanded(
                child: buildMainArea(context)
            ),
          ],
        ),
      ),
    );
  }
}

enum ScreenState {
  Loading,
  View,
  Error
}
