import 'dart:html';

import 'package:client/Components/BetterNumberButton.dart';
import 'package:client/Components/MenuBar.dart';
import 'package:client/Components/NumberButton.dart';
import 'package:client/Screens/WebColors.dart';
import 'package:client/Services/LogicManager.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'Arguments/MultiReportScreenArguments.dart';
import 'PopUps/MessagePopUp.dart';

class WebMultiReportsScreen extends StatefulWidget {

  MultiReportScreenArguments args;

  WebMultiReportsScreen(this.args);

  @override
  _WebMultiReportsScreenState createState() => _WebMultiReportsScreenState();
}

class _WebMultiReportsScreenState extends State<WebMultiReportsScreen> {

  LogicManager logicManager;
  WebColors webColors;
  ScreenState screenState;
  // Searching Files vars
  List<File> videos;
  List<File> labels;
  // View vars
  List<ReportFile> reports;

  _WebMultiReportsScreenState() {
    logicManager = LogicManager.getInstance();
    webColors = new WebColors();
    screenState = ScreenState.FolderPicking;
  }

  void onNextStep() {
      ScreenState next;
      if(screenState == ScreenState.FolderPicking) {
        next = ScreenState.SearchingFiles;
      }
      else if(screenState == ScreenState.SearchingFiles) {
        next = ScreenState.ViewFiles;
      }
      else if(screenState == ScreenState.ViewFiles) {
        next = ScreenState.Reporting;
      }
      else if(screenState == ScreenState.Reporting) {
        next = ScreenState.Downloading;
      }
      if(next != null) {
        this.setState(() {
          screenState = next;
        });
      }
  }

  void onFilesError() {
    this.setState(() {
      screenState = ScreenState.FilesError;
    });
  }

  void onClickStepFolderPicking() {
    this.setState(() {
      screenState = ScreenState.FolderPicking;
    });
  }

  void onClickStepViewFiles() {
    this.setState(() {
      screenState = ScreenState.ViewFiles;
    });
  }

  void onClickStepDownloading() {
    this.setState(() {
      screenState = ScreenState.Downloading;
    });
  }

  void uploadFiles(String fileType, Function updateFiles) {
    InputElement uploadInput = FileUploadInputElement();
    uploadInput.multiple = true;
    uploadInput.draggable = true;
    uploadInput.accept = fileType;
    uploadInput.click();
    document.body.append(uploadInput);
    uploadInput.onChange.listen((e) {
      updateFiles(uploadInput.size, uploadInput.files);
    });
    uploadInput.remove();
  }

  void onSelectVideoFolder() {
    uploadFiles('video/*', (int size, List<File> files) {
      if(size > 0) {
        this.setState(() {
          videos = files;
        });
      }
      else {
        showDialog(
          context: context,
          builder: (BuildContext context) {
            return new MessagePopUp('Select at least one video');
          },
        );
      }
    });
  }

  void onSelectLabelsFolder() {
    uploadFiles('.csv', (int size, List<File> files) {
      if(size > 0) {
        this.setState(() {
          labels = files;
        });
      }
      else {
        showDialog(
          context: context,
          builder: (BuildContext context) {
            return new MessagePopUp('Select at least one csv');
          },
        );
      }
    });
  }

  void onClickRemoveReportFile(int index) {
    if(index < this.reports.length) {
      this.reports.removeAt(index);
      this.setState(() {});
    }
  }

  void onClickDownloadingReports() {

  }

  void onClickAgain() {
    this.setState(() {
      screenState = ScreenState.FolderPicking;
      // Searching Files vars
      videos = [];
      labels = [];
      // View vars
      reports = [];
    });
  }

  Widget buildTitle(BuildContext context, String title) {
    return Container(
      margin: EdgeInsets.only(bottom: 15),
      child: Text(title,
        style: TextStyle(
          color: Colors.black,
          fontSize: 46 * MediaQuery.of(context).textScaleFactor,
          decoration: TextDecoration.none,
        ),
      ),
    );
  }

  Widget buildTextButton(BuildContext context, String text, Function onPressed) {
    return TextButton(
      onPressed: onPressed,
      child: Text(text,
        style: TextStyle(
          fontSize: 22 * MediaQuery.of(context).textScaleFactor,
        ),
      ));
  }

  Widget buildElevatedButton(BuildContext context, String text, Function onPressed) {
    return ElevatedButton(
      onPressed: onPressed,
      child: Padding(
        padding: const EdgeInsets.all(5.0),
        child: Text(text,
          style: TextStyle(
            fontSize: 24 * MediaQuery.of(context).textScaleFactor,
          ),
        ),
      ));
  }

  Widget buildRoadMapFolderPicking(BuildContext context) {
    return BetterNumberButton(
      number: 1,
      title: 'Folders picking',
      background: webColors.getBackgroundForI7(),
      selected: screenState.index >= ScreenState.FolderPicking.index,
      selectedColor: webColors.getBackgroundForI1(),
      onClick: screenState.index >= ScreenState.FolderPicking.index?
        onClickStepFolderPicking : null,
      fontSize: 21,
      flex: 1
    );
  }

  Widget buildRoadMapViewFiles(BuildContext context) {
    return BetterNumberButton(
        number: 2,
        title: 'View files',
        background: webColors.getBackgroundForI7(),
        selected: screenState.index >= ScreenState.ViewFiles.index,
        selectedColor: webColors.getBackgroundForI1(),
        onClick: screenState.index >= ScreenState.ViewFiles.index?
        onClickStepViewFiles : null,
        fontSize: 21,
        flex: 1
    );
  }

  Widget buildRoadMapDownloading(BuildContext context) {
    return BetterNumberButton(
        number: 3,
        title: 'Downloading reports',
        background: webColors.getBackgroundForI7(),
        selected: screenState.index >= ScreenState.Downloading.index,
        selectedColor: webColors.getBackgroundForI1(),
        onClick: screenState.index >= ScreenState.Downloading.index?
        onClickStepDownloading : null,
        fontSize: 21,
        flex: 1
    );
  }

  Widget buildRoadMap(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisSize: MainAxisSize.min,
      children: [
        buildRoadMapFolderPicking(context),
        buildRoadMapViewFiles(context),
        buildRoadMapDownloading(context),
      ],
    );
  }

  Widget buildFolderSelected(BuildContext context, String title, String folderPath) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(title,
              style: TextStyle(
                  color: Colors.black,
                  fontSize: 26 * MediaQuery.of(context).textScaleFactor
              ),
            ),
            Text(folderPath,
              style: TextStyle(
                  color: Colors.grey,
                  fontSize: 24 * MediaQuery.of(context).textScaleFactor
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildWarningFiles(BuildContext context, String title, List files) {
    if(files!=null) {
      return Container();
    }
    else {
      return Card(
        child: Padding(
          padding: EdgeInsets.all(15.0),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Icon(Icons.warning_amber_rounded,
                size: 25,
                color: Colors.yellow,
              ),
              SizedBox(width: 10,),
              Text("Didn't selected $title folder yet",
                style: TextStyle(
                  color: Colors.grey,
                  fontSize: 21 * MediaQuery.of(context).textScaleFactor
                ),
              ),
            ],
          ),
        ),
      );
    }
  }

  Widget buildNextButtonFolderPicking(BuildContext context) {
    if(videos != null) {
      return Align(
          alignment: Alignment.bottomRight,
          child: buildElevatedButton(context, 'Next', onNextStep)
      );
    }
    return Container();
  }

  Widget buildFolderPicking(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisSize: MainAxisSize.min,
      children: [
        buildTitle(context, 'Files selection'),
        buildTextButton(context, 'Select videos', onSelectVideoFolder),
        buildTextButton(context, 'Select labels', onSelectLabelsFolder),
        SizedBox(height: 10,),
        buildWarningFiles(context, 'Videos', videos),
        buildWarningFiles(context, 'Labels', labels),
        SizedBox(height: 10,),
        buildNextButtonFolderPicking(context),
      ],
    );
  }

  Widget buildSearchingFiles(BuildContext context) {
    return Container();
  }

  Widget buildViewFile(BuildContext context, ReportFile file) {
    return Container();
  }

  Widget buildViewFiles(BuildContext context) {
    return Container();
  }

  Widget buildFilesError(BuildContext context) {
    return Container();
  }

  Widget buildReportingFile(BuildContext context, ReportFile file) {
    return Container();
  }

  Widget buildReporting(BuildContext context) {
    return Container();
  }

  Widget buildDownloading(BuildContext context) {
    return Container();
  }

  Widget buildStep(BuildContext context) {
    if(screenState == ScreenState.FolderPicking) {
      return buildFolderPicking(context);
    }
    else if(screenState == ScreenState.SearchingFiles) {
      return buildSearchingFiles(context);
    }
    else if(screenState == ScreenState.ViewFiles) {
      return buildViewFiles(context);
    }
    else if(screenState == ScreenState.FilesError) {
      return buildFilesError(context);
    }
    else if(screenState == ScreenState.Reporting) {
      return buildReporting(context);
    }
    else if(screenState == ScreenState.Downloading) {
      return buildDownloading(context);
    }
    return Container();
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        color: webColors.getBackgroundForI6(),
        child: Column(
          children: [
            MenuBar(user: this.widget.args.user,),
            Expanded(
              child: Padding(
                padding: EdgeInsets.only(top: 20),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    buildRoadMap(context),
                    Expanded(
                      child: Padding(
                        padding: EdgeInsets.only(left: 15, right: 15),
                        child: buildStep(context),
                      ),
                    ),
                  ],
                ),
              )
            )
          ],
        ),
      ),
    );
  }
}

enum ScreenState {
  FolderPicking,
  SearchingFiles,
  ViewFiles,
  FilesError,
  Reporting,
  Downloading,
}

class ReportFile {

  String videoPath;
  String labelsPath;
  ReportState reportState;

  ReportFile(this.videoPath) {
    reportState = ReportState.Pending;
  }
}

enum ReportState {
  Pending,
  Analyzing,
  Error,
  Done
}
