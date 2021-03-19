import 'package:client/Web/Components/CircleButton.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CardButton extends StatefulWidget {

  String title;
  Color background;
  Color buttonBackground;
  String image;
  Function onClick;
  Function onHover;

  CardButton({this.title, this.background, this.buttonBackground,
    this.image, this.onClick, this.onHover}) : super();

  @override
  _CardButtonState createState() => _CardButtonState();
}

class _CardButtonState extends State<CardButton> {

  Widget buildWithOutIcon(BuildContext context) {
      return Text( this.widget.title,
          style: TextStyle(
              fontSize: 24 * MediaQuery.of(context).textScaleFactor,
              color:Colors.black,
              fontWeight: FontWeight.bold,
              fontStyle: FontStyle.italic,
              decoration: TextDecoration.none
          )
      );
  }

  Widget buildTopSide(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        color: this.widget.background,
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Image.asset(
          this.widget.image,
          fit: BoxFit.fill,
        ),
      ),
    );
  }

  Widget buildTitle(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        padding: EdgeInsets.only(left:10.0),
        child: Text(
          this.widget.title,
          textAlign: TextAlign.left,
          style: TextStyle(
              fontSize: 28 * MediaQuery.of(context).textScaleFactor,
              color:Colors.black,
              // fontWeight: FontWeight.bold,
              fontStyle: FontStyle.italic,
              decoration: TextDecoration.none,
          )
        ),
      ),
    );
  }

  Widget buildExplanation(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        padding: EdgeInsets.only(left:10.0),
        child: Text(
            'Additional information about ${this.widget.title}',
            textAlign: TextAlign.left,
            style: TextStyle(
              fontSize: 20 * MediaQuery.of(context).textScaleFactor,
              color:Colors.black54,
              // fontWeight: FontWeight.bold,
              // fontStyle: FontStyle.italic,
              decoration: TextDecoration.none,
            )
        ),
      ),
    );
  }

  Widget buildBottomSide(BuildContext context, int flex) {
      return Flexible(
        flex: flex,
        fit: FlexFit.tight,
        child: Column(
          children: [
            buildTitle(context, 1),
            buildExplanation(context, 1),
            Flexible(
              flex: 1,
              child: Align(
                alignment: Alignment.bottomRight,
                child: Container(
                  padding: EdgeInsets.only(right:5.0, bottom: 7.0),
                  child: ElevatedButton(
                    onPressed: this.widget.onClick,
                    style: ButtonStyle(
                      foregroundColor: MaterialStateColor.resolveWith(
                              (states) => Colors.white),
                      backgroundColor:  MaterialStateColor.resolveWith(
                              (states) => this.widget.buttonBackground),
                    ),
                    child: Container(
                      padding: EdgeInsets.only(right:2.0, left:2.0),
                      child: Text('More',
                        style: TextStyle(
                            fontSize: 21 * MediaQuery.of(context).textScaleFactor,
                            fontWeight: FontWeight.bold,
                            decoration: TextDecoration.none
                        ),
                      ),
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      );
  }

  @override
  Widget build(BuildContext context) {
    if(this.widget.image == null) {
      return Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        decoration: BoxDecoration(
          border: Border.all(color: Colors.black),
        ),
        child: buildBottomSide(context, 1),
      );
    }
    return Container(
      decoration: BoxDecoration(
        border: Border.all(color: Colors.black),
        color: this.widget.background,
      ),
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      // child: Card(
      //   shadowColor: Colors.black12,
        child: Column(
          children: [
            buildTopSide(context, 5),
            buildBottomSide(context, 2),
          ],
        ),
    );
  }
}
