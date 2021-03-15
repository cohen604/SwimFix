import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CircleButton extends StatefulWidget {

  String title;
  Color background;
  Icon icon;
  Function onHover;
  Function onClick;

  CircleButton({this.title, this.background, this.icon,
    this.onClick, this.onHover}) : super();

  @override
  _CircleButtonState createState() => _CircleButtonState();
}

class _CircleButtonState extends State<CircleButton> {

  ///TODO add here an icon
  Widget buildWithIcon(BuildContext context) {
    return Row(
      children: [
        buildWithOutIcon(context),
        Text('here will be icon'),
      ],
    );
  }

  Widget buildWithOutIcon(BuildContext context) {
      return Text( this.widget.title,
          style: TextStyle(
              fontSize: 24 * MediaQuery.of(context).textScaleFactor,
              color:Colors.black,
              fontWeight: FontWeight.bold,
              // fontStyle: FontStyle.italic,
              decoration: TextDecoration.none
          )
      );
  }

  Function buildFutureDialogSupport(BuildContext context) {
    return () => showDialog(
      context: context,
      builder: (_) => AlertDialog(
        content: Text('Coming Soon',
          textAlign: TextAlign.center,),
      )
    );
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: this.widget.onClick != null ? this.widget.onClick()
          : buildFutureDialogSupport(context),
      child: Container(
        margin: EdgeInsets.all(5.0),
        decoration: BoxDecoration(
          boxShadow: [
            BoxShadow(
              offset: Offset( 0.0, 20.0),
              blurRadius: 30.0,
              color: Colors.black12,
            ),
          ],
          border: Border.all(color: Colors.black),
          borderRadius: BorderRadius.circular(21.0),
          color: this.widget.background,
        ),
        padding: EdgeInsets.only(left: 20.0, right: 20.0),
        child: this.widget.icon != null ?
                  buildWithIcon(context) :
                  buildWithOutIcon(context),
      ),
    );
  }
}
