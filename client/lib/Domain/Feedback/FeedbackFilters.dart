
class FeedbackFilters {
  String path;
  List<String> filters;


  FeedbackFilters(this.path, this.filters);

  FeedbackFilters.fromJson(Map<String, dynamic> json)
      : path = json['path'],
        filters = json['filters'];

  Map<String, dynamic> toMap() =>
      {
        'path': path,
        'filters': filters,
      };

}