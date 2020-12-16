class Swimmer {

  String uid;
  String email;

  Swimmer(this.uid, this.email);

  static Swimmer factory(Map map) {
    return new Swimmer(map['uid'], map['email']);
  }

  Swimmer.fromJson(Map<String, dynamic> json)
      : uid = json['uid'],
        email = json['email'];

  Map<String, dynamic> toJson() =>
    {
      'uid': uid,
      'email': email,
    };

}