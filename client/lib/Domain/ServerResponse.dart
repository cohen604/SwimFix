class ServerResponse {
  String response;
  Object value;

  ServerResponse(this.response, this.value);

  ServerResponse.fromJson(Map<String, dynamic> json)
      : response = json['response'],
        value = json['value'];

  bool isSuccess() => this.response == "SUCCESS";
}