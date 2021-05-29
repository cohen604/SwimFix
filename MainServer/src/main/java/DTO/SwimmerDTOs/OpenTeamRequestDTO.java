package DTO.SwimmerDTOs;

import DTO.UserDTOs.UserDTO;

public class OpenTeamRequestDTO {

    private UserDTO userDTO;
    private String teamName;

    public OpenTeamRequestDTO(UserDTO userDTO, String teamName) {
        this.userDTO = userDTO;
        this.teamName = teamName;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
