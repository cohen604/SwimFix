import 'package:client/Web/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MenuBar extends StatefulWidget {

  Function onlogout;

  MenuBar({this.onlogout});

  @override
  _MenuBarState createState() => _MenuBarState();
}

class _MenuBarState extends State<MenuBar> {

  WebColors _webColors = new WebColors();
  List<bool> _onHover = List.generate(5, (index) => false);
  List<bool> _selected = List.generate(5, (index) => false);

  Function buildFutureDialogSupport(BuildContext context) {
    return () => showDialog(
        context: context,
        builder: (_) => AlertDialog(
          content: Text('Coming Soon',
            textAlign: TextAlign.center,),
        )
    );
  }

  Widget buildOption(BuildContext context, String optionName, int index,
      Function onClick) {
    return Flexible(
      flex: 1,
      child: MouseRegion(
        onHover: (PointerEvent details) =>  setState(()=>_onHover[index] = true),
        onExit: (PointerEvent details) => setState(()=>_onHover[index] = false),
        child: GestureDetector(
          onTap: onClick != null ?
            onClick :
            buildFutureDialogSupport(context),
          child: Container(
            color: _onHover[index] ? _webColors.getBackgroundForI3() : _webColors.getBackgroundForI1(),
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            child: Center(
              child: Text(optionName,
                  style: TextStyle(
                    fontSize: 18 * MediaQuery.of(context).textScaleFactor,
                    color: _onHover[index] ? Colors.black : Colors.white,
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

  Widget buildLinks(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Scaffold(
        body: Row(
        children: [
            Flexible(
              flex: 7,
              child: Container(
                color: _webColors.getBackgroundForI1(),
              ),
            ),
            buildOption(context, "Swimmer", 0, null),
            buildOption(context, "Coach", 1, null),
            buildOption(context, "Admin", 2, null),
            buildOption(context, "Researcher", 3, null),
            buildOption(context, "Logout", 4, this.widget.onlogout),
          ],
          //scrollDirection: Axis.horizontal,
        ),
      ),
    );
  }

  Widget buildLogo(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Center(
          child: Text( "Swim Analytics",
              style: TextStyle(
                  fontSize: 26 * MediaQuery.of(context).textScaleFactor,
                  color:Colors.white,
                  fontWeight: FontWeight.bold,
                  fontStyle: FontStyle.italic,
                  decoration: TextDecoration.none
              )
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      decoration: BoxDecoration(
        border: Border(
          bottom: BorderSide(
            color: Colors.black,
            width: 2.0,
          ),
        ),
        color: _webColors.getBackgroundForI1(),
      ),
      child: Row(
        children: [
          buildLogo(context, 1),
          buildLinks(context, 4)
        ],
      )
    );
  }
}
