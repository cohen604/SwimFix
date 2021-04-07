import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Screens/ColorsHolder.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:flutter/material.dart';

class BasicDrawer extends StatelessWidget {

  Swimmer _swimmer;
  String _photoURL;
  ColorsHolder _colorsHolder;

  BasicDrawer(this._swimmer, this._photoURL) {
    _colorsHolder = new ColorsHolder();
  }

  @override
  Widget build(BuildContext context) {

    return Drawer(
      child: Container(
        color: _colorsHolder.getBackgroundForI6(),
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            DrawerHeader(
              decoration: BoxDecoration(
                image: DecorationImage(
                  fit: BoxFit.fill,
                  image: AssetImage('assets/images/menubar_image.png'),
                  colorFilter: ColorFilter.mode(Colors.black.withAlpha(100), BlendMode.darken),
                ),
              ),
              child: Container (
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Center(
                      child: CircleAvatar(
                        backgroundImage: NetworkImage(_photoURL),
                        radius: 30,
                        backgroundColor: Colors.transparent,
                      ),
                    ),
                    SizedBox(width: 10.0),
                    Center(
                      child: Text(
                        _swimmer.name,
                        style: TextStyle(color: Colors.white, fontSize: 35,
                            fontWeight: FontWeight.bold),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            ListTile(
              leading: Icon(Icons.add_a_photo),
              title: Text('Upload'),
              onTap: () async {
                LogicManager.getInstance().logout(_swimmer);
                Navigator.of(context).popUntil((route) => route.isFirst);
              },
            ),
            ListTile(
              leading: Icon(Icons.camera_alt),
              title: Text('Camera'),
              onTap: () async {
                LogicManager.getInstance().logout(_swimmer);
                Navigator.of(context).popUntil((route) => route.isFirst);
              },
            ),
            ListTile(
              leading: Icon(Icons.exit_to_app),
              title: Text('Logout'),
              onTap: () async {
                LogicManager.getInstance().logout(_swimmer);
                Navigator.of(context).popUntil((route) => route.isFirst);
              },
            ),
          ],
        ),
      ),
    );
  }
}
