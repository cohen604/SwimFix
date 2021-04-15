import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Domain/Users/UserPermissions.dart';
import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/Arguments/ResearcherScreenArguments.dart';
import 'package:client/Screens/Arguments/SwimmerScreenArguments.dart';
import 'package:client/Screens/Arguments/WelcomeScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Screens//WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class MenuBar extends StatefulWidget {

  WebUser user;

  MenuBar({this.user});

  @override
  _MenuBarState createState() => _MenuBarState();
}

class _MenuBarState extends State<MenuBar> {

  LogicManager _logicManager = LogicManager.getInstance();
  Function onCoach;
  Function onAdmin;

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

  Function onLogo(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/welcome',
            arguments: new WelcomeScreenArguments(this.widget.user));
        });
      });
    };
  }

  Function onLogout(BuildContext context) {
    return () {
      _logicManager.logout(this.widget.user.swimmer).then(
              (value) {
            if (value) {
              this.setState(() {
                SchedulerBinding.instance.addPostFrameCallback((_) {
                  Navigator.pushNamed(context, '/');
                });
              });
            }
            else {
              showDialog(
                context: context,
                builder: (_) =>
                  AlertDialog(
                    content: Text('Cant Logout, Please Try again later',
                      textAlign: TextAlign.center,),
                  )
              );
            }
          }
      );
    };
  }

  Function onSwimmer(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/swimmer',
              arguments: new SwimmerScreenArguments(this.widget.user));
        });
      });
    };
  }

  Function onResearcher(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/researcher/report',
              arguments: new ReprotScreenArguments(this.widget.user));
        });
      });
    };
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

  buildSwimmer(BuildContext context, int index) {
    if(this.widget.user.permissions.isSwimmer) {
      return buildOption(context, "Swimmer", index, onSwimmer(context));
    }
    return Container();
  }

  buildCoach(BuildContext context, int index) {
    if(this.widget.user.permissions.isCoach) {
      return buildOption(context, "Coach", index, onCoach);
    }
    return Container();
  }

  buildAdmin(BuildContext context, int index) {
    if(this.widget.user.permissions.isAdmin) {
      return buildOption(context, "Admin", index, onAdmin);
    }
    return Container();
  }

  buildResearcher(BuildContext context, int index) {
    if(this.widget.user.permissions.isResearcher) {
      return buildOption(context, "Researcher", index, onResearcher(context));
    }
    return Container();
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
            buildSwimmer(context, 0),
            buildCoach(context, 1),
            buildAdmin(context, 2),
            buildResearcher(context, 3),
            buildOption(context, "Logout", 4, onLogout(context)),
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
            onPressed: onLogo(context),
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
          buildLinks(context, 4)
        ],
      )
    );
  }
}
