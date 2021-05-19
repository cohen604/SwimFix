import 'dart:ui';

import 'package:client/Screens/Holders/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AboutScreenMenuBar extends StatefulWidget {

  Function onLogo;
  Function onAbout;
  Function onDownload;
  Function onLogin;
  AboutScreenMenuBar({this.onLogo, this.onAbout, this.onDownload, this.onLogin});

  @override
  _AboutScreenMenuBarState createState() => _AboutScreenMenuBarState();
}

class _AboutScreenMenuBarState extends State<AboutScreenMenuBar> {

  WebColors _webColors;
  List<bool> _onHover;

  _AboutScreenMenuBarState() {
    _webColors = WebColors.getInstance();
    int size = 3;
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

  Widget buildOption2(BuildContext context, String optionName, int index,
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
                  fontSize: 18 * MediaQuery.of(context).textScaleFactor,
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

  Widget buildOption(BuildContext context, String optionName, IconData icon, int index,
      Function onClick) {
    return MouseRegion(
      onHover: (PointerEvent details) =>  setState(()=>_onHover[index] = true),
      onExit: (PointerEvent details) => setState(()=>_onHover[index] = false),
      child: GestureDetector(
        onTap: onClick != null ?
        onClick :
        buildFutureDialogSupport(context),
        child: Container(
          color: Colors.transparent,
          padding: EdgeInsets.only(right: 10, left: 10),
          child: Center(
            child: Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                Icon(
                  icon,
                  size: 35,
                  color: _onHover[index]? _webColors.getBackgroundForI2() : Colors.black,
                ),
                SizedBox(width: 3,),
                Text(optionName,
                  style: TextStyle(
                      fontSize: 18 * MediaQuery.of(context).textScaleFactor,
                      color: _onHover[index]? _webColors.getBackgroundForI2() : Colors.black,
                      fontWeight: FontWeight.bold,
                      decoration: TextDecoration.none
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget buildLinks(BuildContext context) {
    return Align(
      alignment: Alignment.topRight,
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          buildOption(context, "About", Icons.pageview_outlined, 0, this.widget.onAbout),
          buildOption(context, "Downloads", Icons.download_sharp, 1, this.widget.onDownload),
          buildOption(context, "Login", Icons.login, 2, this.widget.onLogin),
        ],
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
                size: 35,
                color: Colors.white,
              ),
              SizedBox(width: 5,),
              Flexible(
                child: Text( "Swim Analytics",
                  style: TextStyle(
                      fontSize: 26 * MediaQuery.of(context).textScaleFactor,
                      color:Colors.white,
                      fontWeight: FontWeight.bold,
                      fontStyle: FontStyle.italic,
                      decoration: TextDecoration.none
                  ),
                  overflow: TextOverflow.clip,
                ),
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
      height: 68,
      decoration: BoxDecoration(
        border: Border(
          bottom: BorderSide(
            color: Colors.black,
            width: 3.0,
          ),
        ),
        gradient: LinearGradient(
          colors: [
            _webColors.getBackgroundForI1(),
            _webColors.getBackgroundForI7()
          ]
        ),
      ),
      child: Row(
        children: [
          Expanded(
              child: buildLogo(context)
          ),
          buildLinks(context)
        ],
      ),
    );
  }
}
