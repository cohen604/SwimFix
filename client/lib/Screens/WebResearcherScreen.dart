import 'package:client/Components/IconCardButton.dart';
import 'package:client/Components/MenuBar.dart';
import 'package:client/Domain/Users/ResearcherReport.dart';
import 'package:client/Screens/Arguments/ReportScreenArguments.dart';
import 'package:client/Screens/Arguments/ResearcherScreenArguments.dart';
import 'package:client/Screens/WebColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';

import 'Arguments/MultiReportScreenArguments.dart';

class WebResearcherScreen extends StatefulWidget {

  ResearcherScreenArguments args;

  WebResearcherScreen(this.args);

  @override
  _WebResearcherScreenState createState() => _WebResearcherScreenState();
}

class _WebResearcherScreenState extends State<WebResearcherScreen> {

  WebColors _webColors;

  _WebResearcherScreenState() {
    _webColors = new WebColors();
  }

  Function onClickReport(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/researcher/report',
              arguments: new ReportScreenArguments(this.widget.args.user));
        });
      });
    };
  }

  Function onClickMultiReport(BuildContext context) {
    return () {
      this.setState(() {
        SchedulerBinding.instance.addPostFrameCallback((_) {
          Navigator.pushNamed(context, '/researcher/multireport',
              arguments: new MultiReportScreenArguments(this.widget.args.user));
        });
      });
    };
  }

  Widget buildResearcherFeatures(BuildContext context) {
    return Container(
      color: _webColors.getBackgroundForI7(),
      child: ListView(
        scrollDirection: Axis.vertical,
        children: [
          IconCardButton("Report",
              "Get single report for swimming video", onClickReport(context), Icons.upload_file),
          IconCardButton("Multi report",
              "Get multiply reports for swimming videos", onClickMultiReport(context), Icons.drive_folder_upload),
          // IconCardButton("Edit Swimming error",
          //     "Edit the angles of the swimming errors", null, Icons.edit),
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
            MenuBar(user: this.widget.args.user,),
            Expanded(
                child: buildResearcherFeatures(context)
            )
          ],
        ),
      ),
    );
  }

}
