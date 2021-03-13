import 'package:client/Web/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MenuBar extends StatefulWidget {

  @override
  _MenuBarState createState() => _MenuBarState();
}

class _MenuBarState extends State<MenuBar> {

  WebColors _webColors = new WebColors();
  List<bool> _onHover = List.generate(4, (index) => false);
  List<bool> _selected = List.generate(4, (index) => false);

  Widget buildOption(BuildContext context, String optionName, int index) {
    return Flexible(
      flex: 1,
      child: MouseRegion(
        onHover: (PointerEvent details) =>  setState(()=>_onHover[index] = true),
        onExit: (PointerEvent details) => setState(()=>_onHover[index] = false),
        child: Container(
          color: _onHover[index] ? _webColors.getBackgroundForI2() : _webColors.getBackgroundForI1(),
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          child: Center(
            child: Text(optionName,
                style: TextStyle(
                  fontSize: 24 * MediaQuery.of(context).textScaleFactor,
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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Row(
      children: [
          buildOption(context, "Swimmer", 0),
          buildOption(context, "Coach", 1),
          buildOption(context, "Admin", 2),
          buildOption(context, "Researcher", 3),
        ],
        //scrollDirection: Axis.horizontal,
      ),
    );
  }
}
