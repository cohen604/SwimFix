import 'package:client/Components/MenuBar.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'Arguments/NoPermissionScreenArguments.dart';

class WebNoPermissionScreen extends StatefulWidget {

  NoPermissionScreenArguments args;

  WebNoPermissionScreen({Key key, this.args}) : super(key: key);

  @override
  _WebNoPermissionScreenState createState() => _WebNoPermissionScreenState();

}

class _WebNoPermissionScreenState extends State<WebNoPermissionScreen> {

  Widget buildNoPermissionMsg(BuildContext context) {
    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(Icons.do_not_disturb,
            color: Colors.redAccent,
            size: 50,),
          Text('User can\'t access that page. ',
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 30 * MediaQuery.of(context).textScaleFactor,
              color: Colors.black,
              fontWeight: FontWeight.normal,
              decoration: TextDecoration.none,
            ),
          ),
          Text('For more information please content swimanalytics@gmail.com',
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 28 * MediaQuery.of(context).textScaleFactor,
              color: Colors.black,
              fontWeight: FontWeight.normal,
              decoration: TextDecoration.none,
            ),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        child: Column(
          children: [
            Flexible(
              child: MenuBar(user: this.widget.args.user,),
            ),
            Expanded(
                child: buildNoPermissionMsg(context)
            ),
          ],
        ),
      ),
    );
  }
}
