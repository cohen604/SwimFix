class FeedbackVideoStreamer {

  final String path;
  List<String> detectors;

  FeedbackVideoStreamer(this.path, this.detectors);

  FeedbackVideoStreamer.fromJson(Map<String, dynamic> json)
      : path = json['path'],
        detectors = json['detectors'];

  String getPath() {
    return "/"+this.path.replaceAll("\\", "/");
  }

  static FeedbackVideoStreamer factory(Map map) {
    List<String> list = List<String>.from(map['detectors']);
    return new FeedbackVideoStreamer(map['path'], list);
  }

}