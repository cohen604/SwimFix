class SwimmerTeam {

  String email;
  int feedbacksCounter;

  SwimmerTeam(this.email, this.feedbacksCounter);

  SwimmerTeam.fromJson(Map<String, dynamic> map)
    : email = map['email'],
      feedbacksCounter = map['feedbacksCounter'];
}