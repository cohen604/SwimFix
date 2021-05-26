class Summary {

  final int users;
  final int loggedUsers;
  final int swimmers;
  final int loggedSwimmers;
  final int coaches;
  final int loggedCoaches;
  final int researchers;
  final int loggedResearchers;
  final int admins;
  final int loggedAdmins;
  final int feedbacks;

  Summary(
      this.users,
      this.loggedUsers,
      this.swimmers,
      this.loggedSwimmers,
      this.coaches,
      this.loggedCoaches,
      this.researchers,
      this.loggedResearchers,
      this.admins,
      this.loggedAdmins,
      this.feedbacks);

  Summary.fromJson(Map<String,dynamic> map):
        users = map['users'],
        loggedUsers = map['loggedUsers'],
        swimmers = map['swimmers'],
        loggedSwimmers = map['loggedSwimmers'],
        coaches = map['coaches'],
        loggedCoaches = map['loggedCoaches'],
        researchers = map['researchers'],
        loggedResearchers = map['loggedResearchers'],
        admins = map['admins'],
        loggedAdmins = map['loggedAdmins'],
        feedbacks = map['feedbacks'];

  int offlineUsers() {
    if(users == null || loggedUsers == null) {
      return null;
    }
    return users - loggedUsers;
  }

  int offlineSwimmers() {
    if(swimmers == null || loggedSwimmers == null) {
      return null;
    }
    return swimmers - loggedSwimmers;
  }

  int offlineCoaches() {
    if(coaches == null || loggedCoaches == null) {
      return null;
    }
    return coaches - loggedCoaches;
  }

  int offlineResearchers() {
    if(researchers == null || loggedResearchers == null) {
      return null;
    }
    return researchers - loggedResearchers;
  }

  int offlineAdmins() {
    if(admins == null || loggedAdmins == null) {
      return null;
    }
    return admins - loggedAdmins;
  }

  double feedbacksPerSwimmer() {
    if(feedbacks == null || swimmers == null) {
      return null;
    }
    return feedbacks / swimmers;
  }


}