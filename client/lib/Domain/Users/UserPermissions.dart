class UserPermissions {

  bool isSwimmer;
  bool isCoach;
  bool isAdmin;
  bool isResearcher;

  UserPermissions(this.isSwimmer, this.isCoach, this.isAdmin, this.isResearcher);

  UserPermissions.fromJson(Map<String, dynamic> json)
      : isSwimmer = json['isSwimmer'],
        isCoach = json['isCoach'],
        isAdmin = json['isAdmin'],
        isResearcher = json['isResearcher'];

  static UserPermissions factory(Map map) {
    return new UserPermissions(
      map['isSwimmer'],
      map['isCoach'],
      map['isAdmin'],
      map['isResearcher']
    );
  }
}