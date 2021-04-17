import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class IconCardButton extends StatefulWidget {

  String title;
  String description;
  Function onClick;
  IconData icon;

  IconCardButton(this.title, this.description, this.onClick, this.icon);

  @override
  _IconCardButtonState createState() => _IconCardButtonState();
}

class _IconCardButtonState extends State<IconCardButton> {

  Widget buildTop(BuildContext context) {
    return ListTile(
      leading: Icon(
        this.widget.icon,
        color: Colors.blueAccent,
        size: 75,
      ),
      title: Text( this.widget.title,
        style: TextStyle(
          fontSize: 22 * MediaQuery.of(context).textScaleFactor,
          color: Colors.black,
          fontWeight: FontWeight.normal,
          decoration: TextDecoration.none,
        ),
      ),
      subtitle: Text( this.widget.description,
        style: TextStyle(
            fontSize: 20 * MediaQuery.of(context).textScaleFactor,
            color: Colors.grey,
            fontWeight: FontWeight.normal,
            decoration: TextDecoration.none
        ),
      ),
    );
  }

  Widget buildBottom(BuildContext context) {
    return Align(
      alignment: Alignment.topRight,
      child: TextButton(
        onPressed: this.widget.onClick,
        child: Text('More',
          style: TextStyle(
              fontSize: 20 * MediaQuery.of(context).textScaleFactor,
              color: Colors.blue,
              fontWeight: FontWeight.bold,
              decoration: TextDecoration.none
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.all(10.0),
      decoration: BoxDecoration(
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.3),
            spreadRadius: 1,
            blurRadius: 3,
            offset: Offset(0, 3), // changes position of shadow
          ),
        ],
      ),
      child: Card(
        child: Container(
          padding: EdgeInsets.all(10.0),
          child: Column(
            children: [
              buildTop(context),
              buildBottom(context),
            ],
          ),
        ),
      ),
    );
  }

}
