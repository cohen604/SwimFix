import 'package:client_application/Domain/Users/AppUser.dart';
import 'package:client_application/Domain/Users/Swimmer.dart';
import 'package:client_application/Screens/Arguments/CameraScreenArguments.dart';
import 'package:client_application/Screens/Arguments/HistoryScreenArguments.dart';
import 'package:client_application/Screens/Arguments/InvitationsScreenArguments.dart';
import 'package:client_application/Screens/Arguments/MyTeamScreenArguments.dart';
import 'package:client_application/Screens/Arguments/UploadScreenArguments.dart';
import 'package:client_application/Services/LogicManager.dart';
import 'package:flutter/material.dart';
import '../Holders/ColorsHolder.dart';

class BasicDrawer extends StatelessWidget {

  AppUser appUser;
  ColorsHolder _colorsHolder;

  BasicDrawer(this.appUser) {
    _colorsHolder = new ColorsHolder();
  }

  void onMenu(BuildContext context) {
    Navigator.of(context).popUntil((route) => route.settings.name == '/welcome');
  }

  void onCamera(BuildContext context) {
    Navigator.pushNamed(context, "/film",
        arguments: new CameraScreenArguments(appUser));
  }

  void onUpload(BuildContext context) {
    Navigator.pushNamed(context, "/upload",
        arguments: new UploadScreenArguments(appUser));
  }

  void onHistory(BuildContext context) {
    Navigator.pushNamed(context, "/history",
        arguments: new HistoryScreenArguments(appUser));
  }

  void onLogout(BuildContext context) {
    LogicManager.getInstance().logout(appUser.swimmer);
    Navigator.of(context).popUntil((route) => route.isFirst);
  }

  void onInvitations(BuildContext context) {
    Navigator.pushNamed(context, "/invitations",
        arguments: new InvitationsScreenArguments(appUser));
  }

  void onMyTeam(BuildContext context) {
    Navigator.pushNamed(context, "/team",
        arguments: new MyTeamScreenArguments(appUser));
  }

  Widget buildAvatar(BuildContext context) {
    return  Center(
      child: CircleAvatar(
        backgroundImage: NetworkImage(appUser.swimmerPhotoURL),
        radius: 30,
        backgroundColor: Colors.green,
        child: ElevatedButton(
          onPressed: ()=>onMenu(context),
          style: ElevatedButton.styleFrom(
              shape: CircleBorder(),
              primary: Colors.transparent
          ),
          child: Container(),
        ),
      ),
    );
  }

  Widget buildNameAndAvatar(BuildContext context) {
    return DrawerHeader(
      decoration: BoxDecoration(
        image: DecorationImage(
          fit: BoxFit.fill,
          image: AssetImage('assets/images/menubar_image.png'),
          colorFilter: ColorFilter.mode(Colors.black.withAlpha(100), BlendMode.darken),
        ),
      ),
      child: Container (
        child: Column(
          children: <Widget>[
            buildAvatar(context),
            SizedBox(width: 10.0),
            Center(
              child: Text(
                appUser.swimmer.name,
                style: TextStyle(color: Colors.white, fontSize: 35,
                    fontWeight: FontWeight.bold),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildMennu(BuildContext context) {
    return ListTile(
      leading: Icon(Icons.menu_outlined),
      title: Text('Menu'),
      onTap: () => onMenu(context),
    );
  }

  Widget buildUploadVideo(BuildContext context) {
    return ListTile(
      leading: Icon(Icons.add_a_photo),
      title: Text('Upload Video'),
      onTap: () => onUpload(context),
    );
  }

  Widget buildFilmVideo(context) {
    return  ListTile(
      leading: Icon(Icons.videocam),
      title: Text('Film Video'),
      onTap: () => onCamera(context),
    );
  }

  Widget buildHistory(context) {
    return  ListTile(
      leading: Icon(Icons.history),
      title: Text('History'),
      onTap: () => onHistory(context),
    );
  }

  Widget buildInvitations(context) {
    return  ListTile(
      leading: Icon(Icons.email),
      title: Text('Invitations'),
      onTap: () => onInvitations(context),
    );
  }

  Widget buildMyTeam(context) {
    return  ListTile(
      leading: Icon(Icons.pool),
      title: Text('My team'),
      onTap: () => onMyTeam(context),
    );
  }

  Widget buildLogout(context) {
    return ListTile(
      leading: Icon(Icons.exit_to_app),
      title: Text('Logout'),
      onTap: () => onLogout(context),
    );
  }

  @override
  Widget build(BuildContext context) {

    return Drawer(
      child: Container(
        color: _colorsHolder.getBackgroundForI6(),
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            buildNameAndAvatar(context),
            buildMennu(context),
            buildUploadVideo(context),
            buildFilmVideo(context),
            buildHistory(context),
            buildInvitations(context),
            buildMyTeam(context),
            buildLogout(context),
          ],
        ),
      ),
    );
  }
}
