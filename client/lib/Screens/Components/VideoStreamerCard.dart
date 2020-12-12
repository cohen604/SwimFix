import 'package:client/Domain/FeedBackVideoStreamer.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class VideoStreamerCard extends StatelessWidget {

  final FeedbackVideoStreamer link;
  VideoStreamerCard({this.link}): super();

  @override
  Widget build(BuildContext context) {
    if(link == null) {
      return Padding(
        padding: const EdgeInsets.all(8.0),
        child: Card(
          child: Center(
            child: CircularProgressIndicator(),
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
            child: Text(this.link.getPath()),
        ),
      ),
    );
  }
}
