class FeedbackLink {

  String path;

  FeedbackLink(this.path);

  FeedbackLink.fromJson(Map<String, dynamic> json)
      : path = json['path'];

  String getPath() {
    return "/"+this.path.replaceAll("\\", "/");
  }

  static FeedbackLink factory(Map map) {
    return new FeedbackLink(map['path']);
  }

}