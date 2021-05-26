import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ImageCardButton extends StatefulWidget {

  String title;
  Color background;
  Color buttonBackground;
  String image;
  Function onClick;
  Function onHover;

  ImageCardButton({this.title, this.background, this.buttonBackground,
    this.image, this.onClick, this.onHover}) : super();

  @override
  _ImageCardButtonState createState() => _ImageCardButtonState();
}

class _ImageCardButtonState extends State<ImageCardButton> {

  Widget buildTopSide(BuildContext context) {
    return ConstrainedBox(
      constraints: BoxConstraints(
        maxHeight: 10
      ),
      child: Container(
        color: this.widget.background,
        width: MediaQuery.of(context).size.width,
        child: Image.asset(
          this.widget.image,
          fit: BoxFit.fill,
        ),
      ),
    );
  }

  Widget buildTitle(BuildContext context) {
    return Container(
        padding: EdgeInsets.only(left:10.0),
        // alignment: Alignment.topLeft,
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
    );
  }

  Widget buildExplanation(BuildContext context) {
    return Container(
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
        ),
      ),
    );
  }

  Widget buildMoreButton(BuildContext context) {
    return Container(
      alignment: Alignment.bottomRight,
      padding: EdgeInsets.only(top: 10, right:5.0, bottom: 7.0),
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
    );
  }

  Widget buildBottomSide(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        buildTitle(context),
        buildExplanation(context),
        buildMoreButton(context),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        border: Border.all(color: Colors.black, width: 2),
        color: this.widget.background,
      ),
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
        child: Column(
          children: [
            Expanded(
                child: buildTopSide(context)
            ),
            buildBottomSide(context),
          ],
        ),
    );
  }
}
