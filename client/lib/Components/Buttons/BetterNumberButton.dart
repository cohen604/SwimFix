import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class BetterNumberButton extends StatefulWidget {

  int number;
  String title;
  bool selected;
  Color background;
  Color selectedColor;
  Function onClick;
  int fontSize;
  int flex;

  BetterNumberButton({this.number, this.title, this.selected,
                this.background, this.selectedColor,
                this.onClick, this.fontSize, this.flex});

  @override
  _BetterNumberButtonState createState() => _BetterNumberButtonState();
}

class _BetterNumberButtonState extends State<BetterNumberButton> {

  Widget buildNumber(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(12),
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        color: this.widget.selected ?
          this.widget.selectedColor:
          Colors.grey
      ),
      child: Text('${this.widget.number}',
          textAlign: TextAlign.center,
          style: TextStyle(
            fontSize: this.widget.fontSize * MediaQuery.of(context).textScaleFactor,
            color: Colors.white,
            fontWeight: this.widget.selected ?
              FontWeight.bold :
              FontWeight.normal,
            decoration: TextDecoration.none,
          )
        ),
      );
  }

  Widget buildTitle(BuildContext context) {
      return Container(
        margin: EdgeInsets.only(left: 10.0,),
        child: Text(
          this.widget.title,
          style: TextStyle(
              fontSize: this.widget.fontSize * MediaQuery.of(context).textScaleFactor,
              color: this.widget.selected ?
                Colors.black:
                Colors.grey,
              fontWeight: this.widget.selected ?
                FontWeight.bold :
                FontWeight.normal,
              decoration: TextDecoration.none
          )
        ),
      );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.all(10.0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10.0),
        color: this.widget.background,
        border: Border.all(color: Colors.grey),
        boxShadow: [
          BoxShadow(
            offset: Offset( 0.0, 10.0),
            blurRadius: 5.0,
            color: Colors.black12,
          )
        ],
      ),
      child: OutlinedButton(
        onPressed: this.widget.onClick,
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            buildNumber(context),
            buildTitle(context)
          ],
        ),
      )
    );
  }
}