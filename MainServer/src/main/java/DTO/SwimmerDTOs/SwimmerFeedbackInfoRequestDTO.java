package DTO.SwimmerDTOs;

import DTO.UserDTOs.UserDTO;

public class SwimmerFeedbackInfoRequestDTO {

    private UserDTO swimmerDTO;
    private String path;

    public SwimmerFeedbackInfoRequestDTO(UserDTO swimmerDTO, String path) {
        this.swimmerDTO = swimmerDTO;
        this.path = path;
    }

    public UserDTO getSwimmerDTO() {
        return swimmerDTO;
    }

    public void setSwimmerDTO(UserDTO swimmerDTO) {
        this.swimmerDTO = swimmerDTO;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
