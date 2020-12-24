import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class VideoStreamerCard extends StatelessWidget {

  final FeedbackVideoStreamer link;
  final int number;
  VideoStreamerCard({this.link, this.number}): super();

  String shortName(String name) {
    if(kIsWeb) {
      return name;
    }
    int maxLen = 28;
    if(name.length < maxLen) {
      return name;
    }
    //String prefix = name.substring(0, 5);
    //String sefix = name.substring(name.length - 5);
    //return "$prefix...$sefix";
    String output = name.substring(name.length - (maxLen - 3));
    return "...$output";
  }

  void onTap(context){
    print(this.link.getPath());
    Navigator.pushNamed(context, "/videoPreview",
          arguments: this.link);
  }

  Widget buildCardFields(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          Container(
            padding: EdgeInsets.only(left: 10, right: 10),
            child: Text("#${this.number}",
              style: TextStyle(fontSize: 21)
            )
          ),
          Text(this.shortName(this.link.getPath())),
          Spacer(flex:3),
        ],
      ),
    );
  }

  Widget buildCard(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(10),
      child: Card(
        color: Theme.of(context).primaryColor.withAlpha(30),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10.0),
        ),
        child: Builder(
          builder: (context) {
              if(link==null) {
                return Center(
                  child: CircularProgressIndicator(),
                );
              }
              return InkWell(
                borderRadius: BorderRadius.circular(10.0),
                splashColor: Theme.of(context).primaryColor.withAlpha(50),
                onTap: ()=>this.onTap(context),
                child: buildCardFields(context),
              );
          },
        ),
      ),
    );
  }


  @override
  Widget build(BuildContext context) {
    return buildCard(context);
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: ListTile(
        leading: Text("#${this.number}"),
        title: Text("Feedback"),
        tileColor: Colors.lightBlueAccent,
        hoverColor: Colors.lightBlueAccent,
        selectedTileColor: Colors.lightBlueAccent,
        subtitle: Column(
          children: [
            Builder(
              builder: (context) {
                if(link == null) {
                  return Center(
                    child: CircularProgressIndicator(),
                  );
                }
                return Text(this.shortName(this.link.getPath()));
              },
            ),
          ],
        ),
        onTap: ()=>this.onTap(context),
        enabled: (link == null)? false: true,
      ),
    );
  }

}
