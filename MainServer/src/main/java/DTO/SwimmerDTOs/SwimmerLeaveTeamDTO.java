package DTO.SwimmerDTOs;

import DTO.UserDTOs.UserDTO;

public class SwimmerLeaveTeamDTO {

    private UserDTO userDTO;
    private String teamId;

    public SwimmerLeaveTeamDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
