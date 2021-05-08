import 'package:client/Screens/Holders/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MobileAboutScreenMenuBar extends StatefulWidget {

  Function onLogo;
  Function onAbout;
  Function onDownload;
  MobileAboutScreenMenuBar({this.onLogo, this.onAbout, this.onDownload});

  @override
  _MobileAboutScreenMenuBarState createState() => _MobileAboutScreenMenuBarState();
}

class _MobileAboutScreenMenuBarState extends State<MobileAboutScreenMenuBar> {

  WebColors _webColors;
  List<bool> _onHover;
  List<bool> _selected;

  _MobileAboutScreenMenuBarState() {
    _webColors = WebColors.getInstance();
    int size = 2;
    _onHover = List.generate(size, (index) => false);
    _selected = List.generate(size, (index) => false);
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
            buildOption(context, "About", 0, this.widget.onAbout),
            buildOption(context, "Downloads", 1, this.widget.onDownload),
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
          child: TextButton(
            onPressed: this.widget.onLogo,
            child: Text( "Swim Analytics",
                style: TextStyle(
                    fontSize: 20 * MediaQuery.of(context).textScaleFactor,
                    color:Colors.white,
                    fontWeight: FontWeight.bold,
                    fontStyle: FontStyle.italic,
                    decoration: TextDecoration.none
                )
            ),
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: 70,
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
          buildLinks(context, 1)
        ],
      )
    );
  }
}
