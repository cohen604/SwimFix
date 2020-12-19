class Swimmer {

  String uid;
  String email;
  String name;

  Swimmer(this.uid, this.email, this.name);

  Swimmer.fromJson(Map<String, dynamic> json)
      : uid = json['uid'],
        email = json['email'],
        name = json['name'];

  Map<String, dynamic> toJson() =>
    {
      'uid': uid,
      'email': email,
      'name': name,
    };

  static Swimmer factory(Map map) {
    return new Swimmer(map['uid'], map['email'], map['name']);
  }

}