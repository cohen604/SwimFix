import 'dart:io';
import 'dart:typed_data';
import 'package:client/Domain/Dates/DateDayDTO.dart';
import 'package:client/Domain/Feedback/FeedBackLink.dart';
import 'package:client/Domain/Feedback/FeedbackData.dart';
import 'package:client/Domain/Feedback/FeedbackInfo.dart';
import 'package:client/Domain/Files/FileDonwloaded.dart';
import 'package:client/Domain/Files/FilesDownloadRequest.dart';
import 'package:client/Domain/Invitations/Invitation.dart';
import 'package:client/Domain/Summaries/MyTeam.dart';
import 'package:client/Domain/Summaries/Summary.dart';
import 'package:client/Domain/Swimmer/SwimmerHistoryFeedback.dart';
import 'package:client/Domain/Team/AddingTeamResponse.dart';
import 'package:client/Domain/Team/InvitationResponse.dart';
import 'package:client/Domain/Team/Team.dart';
import 'package:client/Domain/Users/ResearcherReport.dart';
import 'package:client/Domain/Users/Swimmer.dart';
import 'package:client/Screens/SwimmersScreens/Arguments/SwimmerOpenTeamArguments.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:http/http.dart' as http;
import 'package:client/Domain/ServerResponse.dart';
import 'package:client/Services/ConnectionHandler.dart';
import 'package:client/Domain/Users/UserPermissions.dart';
import 'dart:convert' show utf8;

import 'GoogleAuth.dart';

class LogicManager {

  static LogicManager logicManager;
  ConnectionHandler connectionHandler;
  GoogleAuth googleAuth;

  LogicManager() {
    this.connectionHandler = new ConnectionHandler();
    this.googleAuth = new GoogleAuth();
  }

  static LogicManager getInstance() {
    if(logicManager == null) {
      logicManager = new LogicManager();
    }
    return logicManager;
  }

  Future<User> signInWithGoogle() async {
    return await googleAuth.signIn();
  }

  Future<bool> signOutWithGoogle() async {
    return await googleAuth.signOut();
  }

  Future<bool> login(Swimmer swimmer) async{
    String path = "/login";
    try {
      ServerResponse response = await connectionHandler.postMessage(
          path,
          swimmer.toJson());
      if (response != null && response.isSuccess()) {
        // Map map = response.value as Map;
        return true;
      }
    } catch(e, stacktrace) {
      print('error in login ${e.toString()} $stacktrace');
    }
    return false;
  }

  Future<bool> logout(Swimmer swimmer) async {
    String path = '/logout';
    try {
      ServerResponse response = await connectionHandler.postMessage(
          path,
          swimmer.toJson());
      if (response != null && response.isSuccess() && response.value) {
          return true;
      }
    } catch(e, stacktrace) {
      print('error in logout ${e.toString()} $stacktrace');
    }
    return false;
  }

  Future<UserPermissions> getPermissions(Swimmer swimmer) async {
    String path = '/permissions';
    try {
      ServerResponse response = await connectionHandler.postMessage(
          path,
          swimmer.toJson());
      if (response != null && response.isSuccess()) {
        Map map = response.value as Map;
        print(map);
        return UserPermissions.factory(map);
      }
    } catch(e, stacktrace) {
      print('error in permissions ${e.toString()} $stacktrace');
    }
    return null;
  }

  /// The function send a post request for receiving a feedback link
  /// fileBytes -
  /// length -
  /// filePath -
  /// return
  Future<FeedBackLink> postVideoForStreaming(
      Uint8List fileBytes,
      int length,
      String filePath,
      Swimmer swimmer) async {
    try {
      String path = "/swimmer/feedback/link";
      http.MultipartFile multipartFile = http.MultipartFile.fromBytes(
        'file',
        fileBytes,
        filename: filePath,
      );
      //ServerResponse response = await this.connectionHandler.postMultiPartFile(path, multipartFile);
      ServerResponse response = await this.connectionHandler
          .postMultiPartFileWithID(path, multipartFile,
          swimmer.uid, swimmer.email, swimmer.name);
      //TODO check if response is valid
      Map map = response.value as Map;
      return FeedBackLink.factory(map);
    }
    catch(e, stacktrace) {
      print('error in post video for stream ${e.toString()} $stacktrace');
    }
    return null;
  }

  String getStreamUrl() {
    return connectionHandler.getStreamUrl();
  }

  Future<ResearcherReport> postVideoAndCsvForAnalyze(
      String videoPath, videoBytes, String labelPath, labelsBytes,
      Swimmer swimmer) async {
    String path = "/researcher/report";
    try {
      http.MultipartFile multipartVideo = http.MultipartFile.fromBytes(
        'video', videoBytes, filename: videoPath,
      );
      http.MultipartFile multipartLabels;
      if(labelsBytes != null && labelPath != null) {
        multipartLabels = http.MultipartFile.fromBytes(
          'labels', labelsBytes, filename: labelPath,
        );
      }
      ServerResponse response = await this.connectionHandler.postMultiPartFiles(path,
          multipartVideo, multipartLabels, swimmer.uid, swimmer.email, swimmer.name);
      if (response.value != null) {
        Map mapResponse = response.value as Map;
        return ResearcherReport.factory(mapResponse);
      }
    }
   catch(e, stacktrace) {
     print('error in $path with ${e.toString()} $stacktrace');
   }
    return null;
  }

  Future<FileDownloaded> getFileForDownload(
      Swimmer swimmer,
      String fileLink) async {
    String path = "/researcher/$fileLink";
    Map<String, dynamic> map = swimmer.toJson();
    return await this.connectionHandler.downloadFile(path, map);
  }

  Future<FileDownloaded> getZipFileForDownload(
      Swimmer swimmer,
      List<String> files) async {
    String path = "/researcher/files/zip";
    FilesDownloadRequest request = new FilesDownloadRequest(swimmer, files);
    Map<String, dynamic> map = request.toJson();
    print(map);
    return await this.connectionHandler.downloadFile(path, map);
  }

  Future<InvitationResponse> sendInvitationEmail(Swimmer swimmer, String email) async {
    String path = '/coach/invite';
    Map<String, dynamic> map = swimmer.toJson();
    map['to'] = email;
    try {
      ServerResponse response = await connectionHandler.postMessage(
          path, map);
      if (response != null && response.isSuccess()) {
        return InvitationResponse.fromJson(response.value as Map);
      }
    }
    catch(e, stacktrace) {
      print('error in $path with ${e.toString()} $stacktrace');
    }
    return null;
  }

  /// get the days a swimmer swim
  Future<List<DateDayDTO>> getSwimmerHistoryDays(Swimmer swimmer) async {
    try {
      String path = "/swimmer/history";
      ServerResponse response = await this.connectionHandler
          .postMessage(path, swimmer.toJson());
      //TODO check if response is valid
      List<dynamic> daysMap = response.value as List<dynamic>;
      List<DateDayDTO> days = daysMap.map(
              (element) {
                return DateDayDTO.factory(element);
              }).toList();
      return days;
    }
    catch(e, stacktrace) {
      print('error in get swimmer history days ${e.toString()} $stacktrace');
    }
    return null;
  }

  /// get the days a swimmer swim
  Future<List<SwimmerHistoryFeedback>> getSwimmerHistoryPoolsByDay(Swimmer swimmer, DateDayDTO day) async {
    try {
      String path = "/swimmer/history/day";
      Map<String, dynamic> request = Map();
      request['user'] = swimmer.toJson();
      request['date'] = day.toJson();
      ServerResponse response = await connectionHandler.postMessage(path, request);
      //TODO check if response is valid
      List<dynamic> feedbacks = response.value as List<dynamic>;
      return feedbacks.map((element) => SwimmerHistoryFeedback.fromJson(element)).toList();
    }
    catch(e, stacktrace) {
      print('error in get swimmer history pools by day ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<bool> deleteFeedback(Swimmer swimmer, DateDayDTO date, String link) async {
    try {
      String path = "/swimmer/history/day/delete";
      Map<String, dynamic> parameters = new Map();
      parameters['user'] = swimmer.toJson();
      parameters['date'] = date.toJson();
      parameters['link'] = link;
      print(parameters);
      ServerResponse serverResponse = await this.connectionHandler.postMessage(
          path, parameters);
      return serverResponse.value as bool;
    }
    catch(e, stacktrace) {
      print('error in delete feedback ${e.toString()} $stacktrace');
    }
    return false;
  }

  Future<List<Swimmer>> getUsersThatNotAdmins(Swimmer admin) async {
    try {
      String path = "/admin/search/users/not/admins";
      Map<String, dynamic> map = admin.toJson();
      ServerResponse serverResponse = await this.connectionHandler.postMessage(
          path, map);
      List<dynamic> list = serverResponse.value;
      print(list);
      return list.map((e) {
        Swimmer swimmer = Swimmer.fromJson(e);
        swimmer.name = utf8.decode(swimmer.name.runes.toList());
        return swimmer;
      }).toList();
    }
    catch(e, stacktrace) {
      print('error in get users that not admins ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<bool> addAdmin(Swimmer admin, Swimmer user) async {
    try {
      String path = "/admin/add/admin";
      Map<String, dynamic> map = {};
      map['admin'] = admin.toJson();
      map['user'] = user.toJson();
      ServerResponse serverResponse = await this.connectionHandler.postMessage(
          path, map);
      return serverResponse.value as bool;
    }
    catch(e, stacktrace) {
      print('error in add admin ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<List<Swimmer>> getUsersThatNotResearchers(Swimmer admin) async {
    try {
      String path = "/admin/search/users/not/researchers";
      Map<String, dynamic> map = admin.toJson();
      ServerResponse serverResponse = await this.connectionHandler.postMessage(
          path, map);
      List<dynamic> list = serverResponse.value;
      print(list);
      return list.map((e) {
        Swimmer swimmer = Swimmer.fromJson(e);
        swimmer.name = utf8.decode(swimmer.name.runes.toList());
        return swimmer;
      }).toList();
    }
    catch(e, stacktrace) {
      print('error in get users that not researchers ${e.toString()} $stacktrace');
    }
    return null;
  }


  Future<bool> addResearcher(Swimmer admin, Swimmer user) async{
    try {
      String path = "/admin/add/researcher";
      Map<String, dynamic> map = {};
      map['admin'] = admin.toJson();
      map['user'] = user.toJson();
      ServerResponse serverResponse = await this.connectionHandler.postMessage(
          path, map);
      return serverResponse.value as bool;
    }
    catch(e, stacktrace) {
      print('error in add researcher ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<Summary> getSummary(Swimmer swimmer) async {
    try {
      String path = "/admin/summary";
      Map<String, dynamic> map = swimmer.toJson();
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        return Summary.fromJson(serverResponse.value as Map);
      }
    }
    catch(e, stacktrace) {
      print('error in get summary ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<AddingTeamResponse> openSwimmingTeam(Swimmer swimmer, String teamName) async {
    try {
      String path = "/swimmer/team/open";
      Map<String, dynamic> map = {};
      map['userDTO'] = swimmer.toJson();
      map['teamName'] = teamName;
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null) {
        return AddingTeamResponse.fromJson(serverResponse.value as Map);
      }
    }
    catch(e, stacktrace) {
      print('error in open swimming team ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<List<Invitation>> getInvitations(Swimmer swimmer) async {
    try {
      String path = "/swimmer/invitations";
      Map<String, dynamic> map = swimmer.toJson();
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        List<dynamic> list = serverResponse.value;
        return list.map((e) => Invitation.fromJson(e)).toList();
      }
    }
    catch(e, stacktrace) {
      print('error in get invitations ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<bool> approveInvitation(Swimmer swimmer, String invitationId) async {
    try {
      String path = "/swimmer/invitation/approve";
      Map<String, dynamic> map = Map();
      map['userDTO'] = swimmer.toJson();
      map['invitationId'] = invitationId;
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        return serverResponse.value as bool;
      }
    }
    catch(e, stacktrace) {
      print('error in approve invitation ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<bool> denyInvitation(Swimmer swimmer, String invitationId) async {
    try {
      String path = "/swimmer/invitation/deny";
      Map<String, dynamic> map = Map();
      map['userDTO'] = swimmer.toJson();
      map['invitationId'] = invitationId;
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        return serverResponse.value as bool;
      }
    }
    catch(e, stacktrace) {
      print('error in deny invitation ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<List<Invitation>> getInvitationsHistory(Swimmer swimmer) async {
    try {
      String path = "/swimmer/invitations/history";
      Map<String, dynamic> map = swimmer.toJson();
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        List<dynamic> list = serverResponse.value;
        return list.map((e) => Invitation.fromJson(e)).toList();
      }
    }
    catch(e, stacktrace) {
      print('error in get invitations history ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<bool> leaveTeam(Swimmer swimmer, String teamName) async {
    try {
      String path = '/swimmer/team/leave';
      Map<String, dynamic> map = Map();
      map['userDTO'] = swimmer.toJson();
      map['teamId'] = teamName;
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        return serverResponse.value as bool;
      }
    }
    catch(e, stacktrace) {
      print('error in leave team ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<MyTeam> getMyTeam(Swimmer swimmer) async {
    try {
      String path = '/swimmer/team';
      Map<String, dynamic> map = swimmer.toJson();
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        return MyTeam.fromJson(serverResponse.value) ;
      }
    }
    catch(e, stacktrace) {
      print('error in get my team ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<Team> getCoachTeam(Swimmer swimmer) async {
    try {
      String path = '/coach//team';
      Map<String, dynamic> map = swimmer.toJson();
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        return Team.fromJson(serverResponse.value) ;
      }
    }
    catch(e, stacktrace) {
      print('error in coach get team ${e.toString()} ${stacktrace}');
    }
    return null;
  }

  Future<List<FeedbackInfo>> coachGetSwimmerFeedbacks(Swimmer coach, String swimmersEmail) async {
    try {
      String path = '/coach/swimmer/feedbacks';
      Map<String, dynamic> map = Map();
      map['coachDTO'] = coach.toJson();
      map['swimmersEmail'] = swimmersEmail;
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        List<dynamic> list = serverResponse.value as List;
        return list.map((e)=>FeedbackInfo.fromJson(e)).toList();
      }
    }
    catch(e, stacktrace) {
      print('error in get swimmer feedbacks ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<FeedbackData> coachGetFeedbackData(Swimmer coach, String swimmersEmail, String feedbackKey) async {
    try {
      String path = '/coach/swimmer/feedback';
      Map<String, dynamic> map = Map();
      map['coachDTO'] = coach.toJson();
      map['swimmerEmail'] = swimmersEmail;
      map['key'] = feedbackKey;
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        print(serverResponse.value);
        return FeedbackData.fromJson(serverResponse.value as Map);
      }
    }
    catch(e, stacktrace) {
      print('error in coach get feedback data ${e.toString()} $stacktrace');
    }
    return null;
  }

  Future<bool> coachAddFeedbackComment(Swimmer coach, String swimmersEmail, String feedbackKey, String text) async {
    try {
      String path = "/coach/swimmer/feedback/comment/add";
      Map<String, dynamic> map = Map();
      map['coachDTO'] = coach.toJson();
      map['swimmerEmail'] = swimmersEmail;
      map['key'] = feedbackKey;
      map['commentText'] = text;
      ServerResponse serverResponse = await this.connectionHandler.postMessage(path, map);
      if(serverResponse!=null && serverResponse.isSuccess()) {
        return serverResponse.value;
      }
    }
    catch(e, stacktrace) {
      print('error in coach get feedback data ${e.toString()} $stacktrace');
    }
    return null;
  }

}