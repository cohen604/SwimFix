class MyTeam {

  bool hasTeam;
  String name;

  MyTeam(this.hasTeam, this.name);

  MyTeam.fromJson(Map<String, dynamic> map)
    : hasTeam = map['hasTeam'],
      name = map['teamId'];
}