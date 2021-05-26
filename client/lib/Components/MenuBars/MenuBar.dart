import 'package:client/Domain/Users/WebUser.dart';
import 'package:client/Screens/AdminScreens/Arguments/AdminSrceenArguments.dart';
import 'package:client/Screens/Arguments/WelcomeScreenArguments.dart';
import 'package:client/Screens/CoachScreens/Arguments/CoachScreenArguments.dart';
import 'package:client/Screens/Holders/WebColors.dart';
import 'package:client/Screens/ResearcherScreens/Arguments/ResearcherScreenArguments.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/SwimmerScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
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

  LogicManager _logicManager;

  WebColors _webColors;
  List<bool> _onHover;

  _MenuBarState() {
    _logicManager = LogicManager.getInstance();
    int size = 5;
    _webColors = WebColors.getInstance();
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
        (serverLogout) {
          _logicManager.signOutWithGoogle().then( (googleLogout) {
            this.setState(() {
              SchedulerBinding.instance.addPostFrameCallback((_) {
                Navigator.pushNamed(context, '/');
              });
            });
          });
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
          Navigator.pushNamed(context, '/researcher',
              arguments: new ResearcherScreenArguments(this.widget.user));
        });
      });
    };
  }

  Function onCoach(BuildContext context) {
    //TODO get the coach team info from logic manger
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/coach',
              arguments: new CoachScreenArguments(this.widget.user, 'Team Name'));
        });
      });
    };
  }

  Function onAdmin(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/admin',
              arguments: new AdminScreenArguments(this.widget.user));
        });
      });
    };
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

  buildSwimmer(BuildContext context, int index) {
    if(this.widget.user.permissions.isSwimmer) {
      return buildOption(context, "Swimmer", Icons.person, index, onSwimmer(context));
    }
    return Container();
  }

  buildCoach(BuildContext context, int index) {
    if(this.widget.user.permissions.isCoach) {
      return buildOption(context, "Coach", Icons.timer, index, onCoach(context));
    }
    return Container();
  }

  buildAdmin(BuildContext context, int index) {
    if(this.widget.user.permissions.isAdmin) {
      return buildOption(context, "Admin", Icons.backpack_outlined, index, onAdmin(context));
    }
    return Container();
  }

  buildResearcher(BuildContext context, int index) {
    if(this.widget.user.permissions.isResearcher) {
      return buildOption(context, "Researcher", Icons.find_in_page_outlined, index, onResearcher(context));
    }
    return Container();
  }

  Widget buildLinks(BuildContext context) {
    return Align(
      alignment: Alignment.topRight,
      child: SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        child: Scrollbar(
          child: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              buildSwimmer(context, 0),
              buildCoach(context, 1),
              buildAdmin(context, 2),
              buildResearcher(context, 3),
              buildOption(context, "Logout", Icons.logout, 4, onLogout(context)),
            ],
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
          onPressed: onLogo(context),
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
      height: 65,
      decoration: BoxDecoration(
        border: Border(
          bottom: BorderSide(
            color: Colors.black,
            width: 2.0,
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
          buildLogo(context),
          Expanded(
              child: buildLinks(context)
          )
        ],
      )
    );
  }
}
