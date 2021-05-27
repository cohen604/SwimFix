package DTO.CoachDTOs;

public class InvitationResponseDTO {

    private boolean sendEmailToUser;
    private boolean sendInvitationToUser;

    public InvitationResponseDTO() {
        this.sendEmailToUser = false;
        this.sendInvitationToUser = false;
    }

    public InvitationResponseDTO(boolean sendEmailToUser, boolean sendInvitationToUser) {
        this.sendEmailToUser = sendEmailToUser;
        this.sendInvitationToUser = sendInvitationToUser;
    }

    public boolean isSendEmailToUser() {
        return sendEmailToUser;
    }

    public boolean isSendInvitationToUser() {
        return sendInvitationToUser;
    }

    public void setSendEmailToUser(boolean sendEmailToUser) {
        this.sendEmailToUser = sendEmailToUser;
    }

    public void setSendInvitationToUser(boolean sendInvitationToUser) {
        this.sendInvitationToUser = sendInvitationToUser;
    }
}
