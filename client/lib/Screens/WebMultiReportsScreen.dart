import 'dart:convert';
import 'dart:html';

import 'package:client/Components/BetterNumberButton.dart';
import 'file:///C:/Users/avrah/Desktop/semesterA/final_project/SwimFix/client/lib/Components/MenuBars/MenuBar.dart';
import 'package:client/Components/NumberButton.dart';
import 'package:client/Domain/Files/FileDonwloaded.dart';
import 'package:client/Domain/Users/ResearcherReport.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'file:///C:/Users/avrah/Desktop/semesterA/final_project/SwimFix/client/lib/Screens/Holders/WebColors.dart';
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
    webColors = WebColors.getInstance();
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

  String getName(String path) {
    print(path);
    int dotIndex = path.lastIndexOf(".");
    if(dotIndex == -1) {
      return null;
    }
    return path.substring(0, dotIndex);
  }

  void onNextStepSearchingFiles() {
    List<ReportFile> output = [];
    for(int i=0; i<this.videos.length; i++) {
      File video = this.videos[i];
      String videoName = getName(video.name);
      ReportFile reportFile = new ReportFile(video);
      if(this.labels != null) {
        for (int j = 0; j < this.labels.length; j++) {
          File label = this.labels[j];
          String labelName = getName(label.name);
          print('($i) $videoName = ($j)$labelName');
          if (videoName != null
              && labelName != null
              && videoName == labelName) {
            reportFile.label = label;
          }
        }
      }
      output.add(reportFile);
    }
    this.setState(() {
      reports = output;
      screenState = ScreenState.ViewFiles;
    });
  }

  void getReportFromServer(
      int index,
      String videoPath,
      dynamic videoBytes,
      String labelPath,
      dynamic labelBytes,
      Swimmer swimmer) {
    logicManager.postVideoAndCsvForAnalyze(videoPath,
        videoBytes, labelPath, labelBytes, swimmer).then(
        (ResearcherReport report) {
          if(report != null) {
            this.setState(() {
              this.reports[index].setDone(report);
              readFiles(index + 1);
            });
          }
          else {
            this.setState(() {
              this.reports[index].setError();
              readFiles(index + 1);
            });
          }
        }
    );
  }

  void readFiles(int index) {
    if(index < this.reports.length) {
      ReportFile file = this.reports[index];
      this.setState(() {
        file.reportState = ReportState.Analyzing;
      });
      String videoPath = file.video.name;
      Swimmer swimmer = this.widget.args.user.swimmer;
      readFile(file.video, (videoBytes) {
        if (file.label != null) {
          String labelPath = file.label.name;
          readFile(file.label, (labelBytes) {
            getReportFromServer(
                index,
                videoPath,
                videoBytes,
                labelPath,
                labelBytes,
                swimmer);
          });
        }
        else {
          getReportFromServer(
              index, videoPath, videoBytes, null, null, swimmer);
        }
      });
    }
    else {
      this.setState(() {
        screenState = ScreenState.Downloading;
      });
    }
  }

  void onNextStepReporting() {
    readFiles(0);
    this.setState(() {
      screenState = ScreenState.Reporting;
    });
  }

  void readFile(File file, Function callback) {
    final reader = new FileReader();
    reader.readAsDataUrl(file.slice(0, file.size, file.type));
    reader.onLoadEnd.listen((e) {
      var bytes = reader.result;
      // print('before');
      // print(bytes.toString());
      bytes = Base64Decoder().convert(reader.result.toString().split(",").last);
      callback(bytes);
    });
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

  void onSelectVideos() {
    uploadFiles('video/*', (int size, List<File> files) {
      if(size > 0) {
        this.setState(() {
          print(files);
          videos = List.castFrom(files);
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

  void onSelectLabels() {
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

  void onFileClick(String fileLink) {
    Swimmer swimmer = this.widget.args.user.swimmer;
    logicManager.getFileForDownload(
      swimmer,
      fileLink
    ).then((FileDownloaded fileDownloaded) {
      String content = base64Encode(fileDownloaded.bytes);
      AnchorElement(
          href: "data:application/octet-stream;charset=utf-16le;base64,$content")
        ..setAttribute("download", fileDownloaded.fileName)
        ..click();
    }
    );
  }

  void onClickFeedback(int index) {
    ReportFile file = this.reports[index];
    onFileClick(file.report.videoLink);
  }

  void onClickCsv(int index) {
    ReportFile file = this.reports[index];
    onFileClick(file.report.csvLink);
  }

  void onClickPDF(int index) {
    ReportFile file = this.reports[index];
    onFileClick(file.report.pdfLink);
  }

  void onClickDownloadingReports() {
    List<String> files = [];
    for(ReportFile reportFile in this.reports) {
      var report = reportFile.report;
      files.add(report.pdfLink);
      files.add(report.videoLink);
      files.add(report.csvLink);
    }
    Swimmer swimmer = this.widget.args.user.swimmer;
    logicManager.getZipFileForDownload(swimmer, files)
        .then((FileDownloaded fileDownloaded) {
      String content = base64Encode(fileDownloaded.bytes);
      AnchorElement(
          href: "data:application/octet-stream;charset=utf-16le;base64,$content")
        ..setAttribute("download", fileDownloaded.fileName)
        ..click();
    });
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

  Widget buildTitle(BuildContext context, String title,
      {size = 46, bottom = 15}) {
    return Container(
      margin: EdgeInsets.only(bottom: bottom),
      child: Text(title,
        style: TextStyle(
          color: Colors.black,
          fontSize: size * MediaQuery.of(context).textScaleFactor,
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
      title: 'Files picking',
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

  Widget buildFolderSelected(BuildContext context, String title, List files) {
    return Card(
      child: Padding(
        padding: EdgeInsets.all(15.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(title,
              style: TextStyle(
                  color: Colors.black,
                  fontSize: 24 * MediaQuery.of(context).textScaleFactor
              ),
            ),
            SizedBox(height: 10,),
            Text('Files: ${files.length}',
              style: TextStyle(
                  color: Colors.grey,
                  fontSize: 18 * MediaQuery.of(context).textScaleFactor
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildWarningFiles(BuildContext context, String title, List files) {
    if(files!=null) {
      return buildFolderSelected(context, title, files);
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
          child: buildElevatedButton(context, 'Next', () {
            onNextStep();
            onNextStepSearchingFiles();
          })
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
        buildTextButton(context, 'Select videos', onSelectVideos),
        buildTextButton(context, 'Select labels', onSelectLabels),
        SizedBox(height: 10,),
        buildWarningFiles(context, 'Videos', videos),
        buildWarningFiles(context, 'Labels', labels),
        SizedBox(height: 10,),
        buildNextButtonFolderPicking(context),
      ],
    );
  }

  Widget buildSearchingFiles(BuildContext context) {
    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          CircularProgressIndicator(),
          Text('Verify videos and labels',
            style: TextStyle(
              color: Colors.grey,
              fontSize: 24 * MediaQuery.of(context).textScaleFactor,
              decoration: TextDecoration.none,
              fontWeight: FontWeight.normal,
            ),
          )
        ]
      )
    );
  }

  Widget buildDes(BuildContext context, String des) {
    return Text(des,
      style: TextStyle(
        color: Colors.grey,
        fontSize: 18 * MediaQuery.of(context).textScaleFactor,
        decoration: TextDecoration.none,
        fontWeight: FontWeight.normal,
      ),
    );
  }

  Widget buildViewFile(BuildContext context, int index) {
    ReportFile file = this.reports[index];
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(10.0),
        child: Column(
          children: [
            buildTitle(context, 'Report file', size: 24),
            buildDes(context, 'Video: ${file.getVideoName()}'),
            buildDes(context, 'Label: ${file.getLabelName()}'),
            IconButton(
                onPressed: ()=>onClickRemoveReportFile(index),
                icon: Icon(
                  Icons.delete_outline,
                  color: Colors.redAccent,
                )),
          ]
        ),
      )
    );
  }

  Widget buildViewFiles(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Column(
        children: [
          Expanded(
            flex: 8,
            child: Scrollbar(
              radius: Radius.circular(20),
              isAlwaysShown: true,
              child: ListView.separated(
                padding: const EdgeInsets.all(8),
                itemCount: this.reports.length,
                shrinkWrap: true,
                separatorBuilder: (BuildContext context, int index) => const Divider(),
                itemBuilder: (BuildContext context, int index) {
                  return buildViewFile(context, index);
                },
              ),
            ),
          ),
          Flexible(
            fit: FlexFit.loose,
            flex: 1,
            child: Container(
              alignment: Alignment.bottomRight,
              padding: const EdgeInsets.all(8.0),
              child: buildElevatedButton(context, 'Next', onNextStepReporting),
            )
          ),
        ],
      ),
    );
  }

  Widget buildFilesError(BuildContext context) {
    return Container(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(Icons.error,
            color: Colors.redAccent,
            size: 35,
          ),
          buildDes(context, 'Something is broken, please check your files again.'),
        ],
      )
    );
  }

  Widget buildReportingFileTrailing(BuildContext context, int index) {
    ReportFile file = this.reports[index];
    if(file.reportState == ReportState.Analyzing) {
      return CircularProgressIndicator();
    }
    if(file.reportState == ReportState.Done) {
      return Row(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          buildTextButton(context, "Feedback", ()=>onClickFeedback(index)),
          SizedBox(width: 10,),
          buildTextButton(context, "Csv", ()=>onClickCsv(index)),
          SizedBox(width: 10,),
          buildTextButton(context, "PDF", ()=>onClickPDF(index)),
        ],
      );
    }
    return SizedBox();
  }

  Widget buildReportingFileIcon(BuildContext context, int index) {
    ReportFile file = this.reports[index];
    Color background = Colors.grey;
    Color text = Colors.white;
    if(file.reportState == ReportState.Error) {
      return Icon(Icons.error_outline,
        color: Colors.red,
        size: 35,
      );
    }
    if(file.reportState == ReportState.Done
        || file.reportState == ReportState.Analyzing) {
      background = this.webColors.getBackgroundForI2();
    }
    return CircleAvatar(
      backgroundColor: background,
      child: Text('$index',
        style: TextStyle(
          color: text,
          fontWeight: FontWeight.bold,
          fontSize: 20 * MediaQuery.of(context).textScaleFactor,
        ),
      ),
    );
  }

  Widget buildReportingFile(BuildContext context, int index) {
    ReportFile file = this.reports[index];
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(15.0),
        child: Row(
          children: [
            buildReportingFileIcon(context, index),
            SizedBox(width: 10,),
            Expanded(
              child: Wrap(
                direction: Axis.vertical,
                children: [
                  buildTitle(context, 'Report file', size: 24, bottom: 5),
                  buildDes(context, 'Video: ${file.getVideoName()}'),
                  buildDes(context, 'Label: ${file.getLabelName()}'),
                ],
              ),
            ),
            buildReportingFileTrailing(context, index),
          ],
        ),
      ),
    );
  }

  Widget buildReportingFiles(BuildContext context) {
    return Scrollbar(
      radius: Radius.circular(20),
      isAlwaysShown: true,
      child: ListView.separated(
        padding: const EdgeInsets.all(8),
        itemCount: this.reports.length,
        shrinkWrap: true,
        separatorBuilder: (BuildContext context, int index) => const Divider(),
        itemBuilder: (BuildContext context, int index) {
          return buildReportingFile(context, index);
        },
      ),
    );
  }

  Widget buildReporting(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: buildReportingFiles(context),
    );
  }

  Widget buildDownloading(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Column(
        children: [
          Container(
            alignment: Alignment.topRight,
            padding: EdgeInsets.all(5),
            child: buildElevatedButton(context, 'Download', onClickDownloadingReports)),
          Expanded(
              child: buildReportingFiles(context)
          ),
        ],
      ),
    );
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
      child: Scaffold(
        body: Container(
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

  File video;
  File label;
  ReportState reportState;
  ResearcherReport report;

  ReportFile(this.video) {
    reportState = ReportState.Pending;
  }

  String getVideoName() {
    return video.name;
  }

  String getLabelName() {
    if(label == null) {
      return "No label for this reprot";
    }
    return label.name;
  }

  void setDone(ResearcherReport report) {
    this.report = report;
    this.reportState = ReportState.Done;
  }

  void setError() {
    this.reportState = ReportState.Error;
  }

}

enum ReportState {
  Pending,
  Analyzing,
  Error,
  Done
}
