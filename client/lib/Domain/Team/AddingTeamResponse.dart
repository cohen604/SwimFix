class AddingTeamResponse {

  String teamName;
  bool isAdded;
  bool isAlreadyExists;
  bool isAlreadyCoach;

  AddingTeamResponse.fromJson(Map<String, dynamic> json)
      : teamName = json['teamName'],
        isAdded = json['isAdded'],
        isAlreadyExists = json['isAlreadyExists'],
        isAlreadyCoach = json['isAlreadyCoach'];


}