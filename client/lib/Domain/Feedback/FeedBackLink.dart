class FeedBackLink {

  final String path;
  FeedBackLink(this.path);

  FeedBackLink.fromJson(Map<String, dynamic> json)
      : path = json['path'];

  String getPath() {
    return "/"+this.path.replaceAll("\\", "/");
  }

  static FeedBackLink factory(Map map) {
    return new FeedBackLink(map['path']);
  }

}