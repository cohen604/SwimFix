import 'package:client_application/Domain/Users/AppUser.dart';
import 'package:client_application/Domain/Video/FeedBackLink.dart';
import 'package:flutter/material.dart';

class FeedbackScreenArguments {

  AppUser appUser;
  FeedbackLink link;
  TimeOfDay poolTime;
  int poolNumber;
  int startTime;
  int endTime;

  FeedbackScreenArguments(this.appUser, this.link, this.poolTime,
      this.poolNumber, this.startTime, this.endTime);
}