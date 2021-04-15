import 'package:client/Components/MenuBar.dart';
import 'package:client/Screens/Arguments/CoachScreenArguments.dart';
import 'package:client/Screens/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class WebCoachScreen extends StatefulWidget {

  CoachScreenArguments args;

  WebCoachScreen(this.args);

  @override
  _WebCoachScreenState createState() => _WebCoachScreenState();
}

class _WebCoachScreenState extends State<WebCoachScreen> {

  WebColors _webColors;
  TextEditingController _searchTextController;

  _WebCoachScreenState() {
    _webColors = new WebColors();
    _searchTextController = TextEditingController();
  }

  Widget buildTopBar(BuildContext context) {
    return Container(
      alignment: Alignment.topRight,
      child: Wrap(
        children: [
          IconButton(
              onPressed: null,
              color: _webColors.getBackgroundForI1(),
              icon: Icon(
                Icons.settings,
                size: 45,
              )
          ),
          IconButton(
            onPressed: null,
            color: _webColors.getBackgroundForI1(),
            icon: Icon(
              Icons.cancel,
              size: 45,
            )
          )
        ],
      ),

    );
  }

  Widget buildGroupInfo(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      padding: EdgeInsets.all(10),
      child: Text('Group Name'),
    );
  }

  Widget buildLastGroupActivities(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      child: Text('Last Activities'),
    );
  }

  Widget buildGroupBar(BuildContext context) {
    return Container(
      alignment: Alignment.topRight,
      child: Wrap(
        children: [
          TextField(
            controller: _searchTextController,
            decoration: InputDecoration(hintText: "search swimmer"),
          ),
        ],
      ),
    );
  }

  Widget buildSwimmers(BuildContext context) {
    return Container();
  }

  Widget buildSwimmingGroup(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      margin: EdgeInsets.all(5.0),
      child: Card(
        child: Padding(
          padding: EdgeInsets.all(8.0),
          child: Column(
            children: [
              buildGroupBar(context),
              Expanded(
                  child: buildSwimmers(context)
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget buildGroupView(BuildContext context) {
    return Column(
      children: [
        buildTopBar(context),
        buildLastGroupActivities(context),
        buildSwimmingGroup(context),
      ],
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
            MenuBar(
              user: this.widget.args.user,
            ),
            Expanded(
                child: buildSwimmingGroup(context)
            ),
          ],
        ),
      ),
    );
  }
}
