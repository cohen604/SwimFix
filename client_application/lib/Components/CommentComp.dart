import 'package:client_application/Domain/Video/FeedbackComment.dart';
import 'package:client_application/Screens/Holders/ColorsHolder.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CommentComp extends StatefulWidget {

  final FeedbackComment comment;

  CommentComp(this.comment);

  @override
  _CommentCompState createState() => _CommentCompState();
}

class _CommentCompState extends State<CommentComp> {

  ColorsHolder _webColors;
  bool _isExpanded;

  _CommentCompState() {
    _webColors = new ColorsHolder();
    _isExpanded = false;
  }

  void onClickExpanded() {
    this.setState(() {
      _isExpanded = true;
    });
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

  Widget buildTrailing(BuildContext context) {
    IconData data = Icons.expand_more;
    if (_isExpanded) {
      data = Icons.expand_less;
    }
    return Icon(data, size: 24, color: Colors.grey);
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(1.0),
      child: Card(
        child: ExpansionTile(
            title: buildText(context, this.widget.comment.coachId,
                16, Colors.black, FontWeight.normal),
            subtitle: buildText(context, this.widget.comment.dateDTO.toString(),
                14, Colors.black54, FontWeight.normal),
            trailing: buildTrailing(context),
            initiallyExpanded: _isExpanded,
            expandedCrossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: const EdgeInsets.all(5.0),
                child: buildText(context, this.widget.comment.comment, 14, Colors.black, FontWeight.normal),
              ),
            ],
            expandedAlignment: Alignment.topLeft,
            onExpansionChanged: (isExpanded) => this.setState(() {
              _isExpanded = isExpanded;
            }),
          ),
        ),
    );
  }
}