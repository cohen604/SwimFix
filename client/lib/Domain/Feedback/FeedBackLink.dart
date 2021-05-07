class FeedBackLink {

  String path;
  FeedBackLink(this.path);

  FeedBackLink.fromJson(Map<String, dynamic> json)
      : path = json['path'];

  String getPath() {
    return "/"+this.path.replaceAll("\\", "/");
  }

  Map<String, dynamic> toJson() =>
      {
        'path': path,
      };

  static FeedBackLink factory(Map map) {
    return new FeedBackLink(map['path']);
  }

}