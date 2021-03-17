import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class RegularButton extends StatefulWidget {

  String title;
  int titleSize;
  int flex;
  Color background;
  Color hoverColor;
  Function onClick;

  RegularButton({this.title, this.titleSize, this.background,
    this.flex, this.hoverColor, this.onClick});

  @override
  _RegularButtonState createState() => _RegularButtonState();
}

class _RegularButtonState extends State<RegularButton> {

  bool _hover = false;

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
    return Flexible(
      flex: 1,
      fit: FlexFit.tight,
      child: MouseRegion(
        onHover: (PointerEvent details) =>
            setState(() => _hover = true),
        onExit: (PointerEvent details) =>
            setState(() => _hover = false),
        child: GestureDetector(
          onTap: this.widget.onClick != null ?
              this.widget.onClick:
              buildFutureDialogSupport(context),
          child: Container(
            color: _hover ? this.widget.hoverColor
                : this.widget.background,
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            child: Center(
              child: Text(this.widget.title,
                style: TextStyle(
                    fontSize: this.widget.titleSize * MediaQuery.of(context).textScaleFactor,
                    color: Colors.black, //_hover ? Colors.black : Colors.white,
                    fontWeight: FontWeight.bold,
                    decoration: TextDecoration.none
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
