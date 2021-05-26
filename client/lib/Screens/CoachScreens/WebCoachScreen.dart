import 'package:client/Components/EmailInvitation.dart';
import 'package:client/Components/MenuBars/MenuBar.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
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
  TextEditingController _searchTextController;

  _WebCoachScreenState() {
    _webColors = WebColors.getInstance();
    _searchTextController = TextEditingController();
  }

  void onAddSwimmer(BuildContext context) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        content: EmailInvitation(this.widget.args.user)
      ),
    );
  }

  Widget buildTopBar(BuildContext context) {
    return Container(
      margin: EdgeInsets.all(10),
      child: Row(
        children: [
          Expanded(
              child: buildGroupInfo(context)
          ),
          IconButton(
              onPressed: ()=>{},
              color: _webColors.getBackgroundForI1(),
              iconSize: 45,
              icon: Icon(
                Icons.settings,
              )
          ),
          IconButton(
            onPressed: ()=>{},
            color: _webColors.getBackgroundForI1(),
            iconSize: 45,
            icon: Icon(
              Icons.cancel,
            )
          )
        ],
      ),

    );
  }

  Widget buildGroupInfo(BuildContext context) {
    return Text('Group Name',
      style: TextStyle(
        fontSize: 32 * MediaQuery.of(context).textScaleFactor,
        fontWeight: FontWeight.bold,
      )
    );
  }

  Widget buildActivities(BuildContext context) {
    return ListView.separated(
      padding: const EdgeInsets.all(8),
      itemCount: 3,
      shrinkWrap: true,
      itemBuilder: (BuildContext context, int index) {
        return Container(
          width: MediaQuery.of(context).size.width,
          child: Row(
            children: [
              CircleAvatar(
                child: Text('${index+1}'),
              ),
              SizedBox(width: 10,),
              Expanded(
                  child: Text('Activity info'))
            ],
          ),
        );
      },
      separatorBuilder: (BuildContext context, int index) => const Divider(),
    );
  }

  Widget buildLastGroupActivities(BuildContext context) {
    return Container(
      margin: EdgeInsets.all(5),
      child: Card(
          child: Padding(
              padding: const EdgeInsets.all(10),
              child: Wrap(
                  children: [
                    Padding(
                      padding: const EdgeInsets.all(10.0),
                      child: Text('Last activities',
                        style: TextStyle(
                          fontSize: 20 * MediaQuery.of(context).textScaleFactor,
                          fontWeight: FontWeight.bold
                        ),
                      ),
                    ),
                    buildActivities(context)
                  ]
              )
          )
      ),
    );
  }

  Widget buildGroupBar(BuildContext context) {
    return Row(
      children: [
        Expanded(
          child: Padding(
            padding: const EdgeInsets.all(3.0),
            child: Text('Swimmers',
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 20 * MediaQuery.of(context).textScaleFactor,
              ),
            ),
          ),
        ),
        Container(
          width: 200,
          child: TextField(
            controller: _searchTextController,
            textAlign: TextAlign.center,
            decoration: InputDecoration(
              hintText: "search swimmer",
            ),
          ),
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
    );
  }

  Widget buildSwimmers(BuildContext context) {
    return ListView.separated(
      itemCount: 3,
      shrinkWrap: true,
      itemBuilder: (BuildContext context, int index) {
        return Container(
          width: MediaQuery.of(context).size.width,
          child: Row(
            children: [
              CircleAvatar(
                child: Text('${index+1}'),
              ),
              SizedBox(width: 10,),
              Expanded(
                  child: Text('Swimmers info'))
            ],
          ),
        );
      },
      separatorBuilder: (BuildContext context, int index) => const Divider(),
    );
  }

  Widget buildSwimmingGroup(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(5),
      child: Card(
        child: Container(
          padding: EdgeInsets.all(15.0),
          child: Column(
            children: [
              buildGroupBar(context),
              buildSwimmers(context),
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
        // buildGroupInfo(context),
        buildLastGroupActivities(context),
        buildSwimmingGroup(context),
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
          color: _webColors.getBackgroundForI4(),
          child: Column(
            children: [
              MenuBar(
                user: this.widget.args.user,
              ),
              Expanded(
                child: SingleChildScrollView(
                  child: Scrollbar(
                      child: buildGroupView(context)
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
