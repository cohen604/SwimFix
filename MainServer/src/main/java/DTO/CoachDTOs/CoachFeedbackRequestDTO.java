package DTO.CoachDTOs;

import DTO.UserDTOs.UserDTO;

public class CoachFeedbackRequestDTO {

    private UserDTO coachDTO;
    private String swimmerEmail;
    private String key;

    public CoachFeedbackRequestDTO(UserDTO coachDTO, String swimmerEmail, String key) {
        this.coachDTO = coachDTO;
        this.swimmerEmail = swimmerEmail;
        this.key = key;
    }

    public UserDTO getCoachDTO() {
        return coachDTO;
    }

    public void setCoachDTO(UserDTO coachDTO) {
        this.coachDTO = coachDTO;
    }

    public String getSwimmerEmail() {
        return swimmerEmail;
    }

    public void setSwimmerEmail(String swimmerEmail) {
        this.swimmerEmail = swimmerEmail;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
