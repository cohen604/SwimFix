import 'package:client/Domain/ScreenArguments/WelcomeScreenArguments.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:client/Web/Components/CardButton.dart';
import 'package:client/Web/Components/MenuBar.dart';
import 'package:client/Web/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

class WebWelcomeScreen extends StatefulWidget {

  WelcomeScreenArguments arguments;
  WebWelcomeScreen({Key key, this.arguments}) : super(key: key);

  @override
  _WebWelcomeScreenState createState() => _WebWelcomeScreenState();
}


class _WebWelcomeScreenState extends State<WebWelcomeScreen> {

  LogicManager _logicManger = LogicManager.getInstance();
  WebColors _webColors = new WebColors();

  Widget buildTopSide(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.tight,
      child: MenuBar(onlogout: onLogout,),
    );
  }

  void onLogout() {
    _logicManger.logout(this.widget.arguments.swimmer).then(
      (value) {
        if(value) {
          this.setState(() {
            SchedulerBinding.instance.addPostFrameCallback((_) {
              Navigator.pushNamed(context, '/login');
            });
          });
        }
        else {
          showDialog(
              context: context,
              builder: (_) => AlertDialog(
              content: Text('Cant Logout, Please Try again later',
                textAlign: TextAlign.center,),
            )
          );
        }
      }
    );
  }

  Widget buildWelcomeTitle(BuildContext context, int flex) {
      return Text('Welcome ${this.widget.arguments.swimmer.name}',
        style: TextStyle(
            fontSize: 32 * MediaQuery.of(context).textScaleFactor,
            color: Colors.black,
            fontWeight: FontWeight.bold,
            decoration: TextDecoration.none
        ),
      );
  }

  Widget buildMainButton(BuildContext context, int flex, String title, Function onClick) {
    return Flexible(
      flex: flex,
      fit: FlexFit.loose,
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height / 1.5,
        padding: EdgeInsets.only(left: 20.0, right: 20.0),
        child: CardButton(
          title: title,
          background: _webColors.getBackgroundForI3(),
          buttonBackground: _webColors.getBackgroundForI5(),
          onClick: onClick,
        ),
      ),
    );
  }

  Function onClick(String path) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, path);
        });
      });
    };
  }

  Widget buildMainButtons(BuildContext context, int flex) {
    return Container(
      margin: EdgeInsets.only(top: 20.0),
      child: Row(
          children: [
            buildMainButton(context, 1, "Swimmer", onClick('/swimmer')),
            buildMainButton(context, 1, "Coach", null),
            buildMainButton(context, 1, "Admin", null),
            buildMainButton(context, 1, "Researcher", null),
          ],
      ),
    );
  }

  Widget buildBottomSide(BuildContext context, int flex) {
    return Flexible(
      flex: flex,
      fit: FlexFit.loose,
      child: Container(
          padding: EdgeInsets.only(top:10.0, bottom: 10.0),
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          color: _webColors.getBackgroundForI4(),
          child: Column(
            children: [
              buildWelcomeTitle(context, 1),
              buildMainButtons(context, 6),
            ],
          )),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
          child: Column(
            children: [
              buildTopSide(context, 1),
              buildBottomSide(context, 10)
            ],
          ),
        ),
    );
  }
}
