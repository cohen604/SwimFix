import 'package:flutter/material.dart';

class TitleButton extends StatefulWidget {

  final String title;
  final String buttonText;
  final Function onPress;

  TitleButton({this.title, this.buttonText, this.onPress, Key key}):
        super(key:key);

  @override
  State<StatefulWidget> createState() {
    return _TitleButtonState();
  }

}

class _TitleButtonState extends State<TitleButton> {

  @override
  Widget build(BuildContext context) {
    return Column(
        children: [
          Text(
              this.widget.title,
              style: TextStyle(fontSize: 18.0)
          ),
          SizedBox(height: 10,),
          RaisedButton(
              onPressed: this.widget.onPress,
              child:Text(this.widget.buttonText)
          ),
        ],
      );
  }


}