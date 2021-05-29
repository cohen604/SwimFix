import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Screens/CoachScreens/Arguments/CoachSwimmerScreenArguments.dart';
import 'package:client/Screens/Holders/AssetsHolder.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

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

  _WebCoachSwimmerScreenState() {
    _webColors = WebColors.getInstance();
    _assetsHolder = AssetsHolder.getInstance();
    _logicManager = LogicManager.getInstance();
    _screenState = ScreenState.Loading;
  }

  Widget buildLoading(BuildContext context) {
    return Container();
  }

  Widget buildError(BuildContext context) {
    return Container();
  }

  Widget buildView(BuildContext context) {
    return Container();
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
  Error,
  View
}

enum SortBy {
  None,
  Name,
  Comments,
  SwimmingErrors,
}

enum OrderBy {
  Ascending,
  Descending
}