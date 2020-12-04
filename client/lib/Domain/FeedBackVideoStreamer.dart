class FeedbackVideoStreamer {
  final String path;

  FeedbackVideoStreamer(this.path);

  FeedbackVideoStreamer.fromJson(Map<String, dynamic> json)
      : path = json['path'];

  String getPath() {
    return "/"+this.path.replaceAll("\\", "/");
  }

  static FeedbackVideoStreamer factory(Map map) {
    return new FeedbackVideoStreamer(map['path']);
  }

}