package DTO.SwimmerDTOs;

import DTO.UserDTOs.UserDTO;

public class SwimmerInvitationUpdateDTO {

    private UserDTO userDTO;
    private String invitationId;

    public SwimmerInvitationUpdateDTO(UserDTO userDTO, String invitationId) {
        this.userDTO = userDTO;
        this.invitationId = invitationId;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setInvitationId(String invitationId) {
        this.invitationId = invitationId;
    }


}
