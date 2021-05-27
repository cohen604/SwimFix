class InvitationResponse {

  bool sendEmailToUser;
  bool sendInvitationToUser;

  InvitationResponse(this.sendEmailToUser, this.sendInvitationToUser);

  InvitationResponse.fromJson(Map<String, dynamic> map)
    : sendEmailToUser = map['sendEmailToUser'],
      sendInvitationToUser = map['sendEmailToUser'];

}