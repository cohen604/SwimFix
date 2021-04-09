import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Avatar extends StatelessWidget {

  String title;
  Function isSelected;
  Color background;
  Color backgroundSelected;
  Function onClick;

  Avatar(this.title, this.isSelected, this.background, this.backgroundSelected,
      {this.onClick});

  @override
  Widget build(BuildContext context) {
    Color textColor = Colors.black;
    Color backgroundColor = background;
    FontWeight fontWeight = FontWeight.normal;
    if(isSelected()) {
      backgroundColor = background;//backgroundSelected;
      textColor = Colors.white;
      fontWeight = FontWeight.bold;
    }
    return ElevatedButton(
      onPressed: isSelected() ?
        ()=> onClick(context):
        null
      ,
      style: ElevatedButton.styleFrom(
          shape: CircleBorder(),
          primary: backgroundColor
      ),
      child: Padding(
        padding: EdgeInsets.all(10),
        child: Text(title,
          style: TextStyle(
            fontSize: 21 * MediaQuery.of(context).textScaleFactor,
            color: textColor,
            fontWeight: fontWeight,
            decoration: TextDecoration.none,
          ),
        ),
      )
    );
  }
}
