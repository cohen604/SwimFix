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
    Color textColor = Colors.white;
    Color backgroundColor = background;
    FontWeight fontWeight = FontWeight.normal;
    if(isSelected()) {
      backgroundColor = backgroundSelected;
      textColor = Colors.black;
      fontWeight = FontWeight.bold;
    }
    return ElevatedButton(
      onPressed: ()=> {
        if(onClick != null) {
          onClick(context)
        }
      },
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
    Widget child = Text(title,
      style: TextStyle(
        fontSize: 21 * MediaQuery.of(context).textScaleFactor,
        color: textColor,
        fontWeight: fontWeight,
        decoration: TextDecoration.none,
      ),
    );
    if(onClick!=null) {
      child = ElevatedButton(
          onPressed: ()=>onClick(context),
          style: ElevatedButton.styleFrom(
              shape: CircleBorder(),
              primary: backgroundColor
          ),
          child: child,
      );
    }
    return CircleAvatar(
      radius: 25,
      backgroundColor: backgroundColor,
      child: child,
    );
  }
}
