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

  @override
  Widget build(BuildContext context) {
    if(link == null) {
      return Padding(
        padding: const EdgeInsets.all(8.0),
        child: Card(
          child: Column(
            children: [
              Text("Feedback ${this.number}"),
              Center(
                child: CircularProgressIndicator(),
              ),
              ],
          ),
        ),
      );
    }
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: GestureDetector(
        onTap: () {
            print(this.link.getPath());
            Navigator.pushNamed(context, "/videoPreview",
              arguments: this.link);
            },
        child: Card(
            child: Column(
              children: [
                Text("Feedback ${this.number}"),
                Text(this.shortName(this.link.getPath())),
                Text("Date"),
              ],
            ),
        ),
      ),
    );
  }
}
