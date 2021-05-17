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

  _MobileAboutScreenMenuBarState() {
    _webColors = WebColors.getInstance();
    int size = 2;
    _onHover = List.generate(size, (index) => false);
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

  Widget buildLinks(BuildContext context) {
    return Align(
      alignment: Alignment.topRight,
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          buildOption(context, "About", 0, this.widget.onAbout),
          buildOption(context, "Downloads", 1, this.widget.onDownload),
        ],
      ),
    );
  }

  Widget buildOption(BuildContext context, String optionName, int index,
      Function onClick) {
    return MouseRegion(
      onHover: (PointerEvent details) =>  setState(()=>_onHover[index] = true),
      onExit: (PointerEvent details) => setState(()=>_onHover[index] = false),
      child: GestureDetector(
        onTap: onClick != null ?
        onClick :
        buildFutureDialogSupport(context),
        child: Container(
          color: _onHover[index] ? _webColors.getBackgroundForI3() : _webColors.getBackgroundForI1(),
          padding: EdgeInsets.only(right: 10, left: 10),
          child: Center(
            child: Text(optionName,
              style: TextStyle(
                  fontSize: 16 * MediaQuery.of(context).textScaleFactor,
                  color: _onHover[index] ? Colors.black : Colors.white,
                  fontWeight: FontWeight.bold,
                  decoration: TextDecoration.none
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget buildLogo(BuildContext context) {
    return Align(
      alignment: Alignment.centerLeft,
      child: Padding(
        padding: const EdgeInsets.only(left: 10.0),
        child: TextButton(
          onPressed: this.widget.onLogo,
          child: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              Icon(
                Icons.pool,
                size: 25,
                color: Colors.white,
              ),
              SizedBox(width: 5,),
              Text( "Swim Analytics",
                  style: TextStyle(
                      fontSize: 20 * MediaQuery.of(context).textScaleFactor,
                      color:Colors.white,
                      fontWeight: FontWeight.bold,
                      fontStyle: FontStyle.italic,
                      decoration: TextDecoration.none
                  ),
                overflow: TextOverflow.fade,
              ),
            ],
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
          Expanded(
              child: buildLogo(context)
          ),
          buildLinks(context)
        ],
      )
    );
  }
}
