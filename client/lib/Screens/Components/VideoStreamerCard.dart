import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class VideoStreamerCard extends StatelessWidget {

  final FeedbackVideoStreamer link;
  final int number;
  VideoStreamerCard({this.link, this.number}): super();

  String shortName(String name) {
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

  Widget buildCard(BuildContext context) {
    return Container(
        child: Card(
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
                  splashColor: Colors.blue.withAlpha(60),
                  child: Text("Feedback"),
                );
            },
          ),
        ),
    );
  }


  @override
  Widget build(BuildContext context) {
    //return buildCard(context);
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
