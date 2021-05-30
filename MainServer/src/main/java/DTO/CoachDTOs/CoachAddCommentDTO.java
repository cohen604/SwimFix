package DTO.CoachDTOs;

import DTO.UserDTOs.UserDTO;

public class CoachAddCommentDTO {

    private UserDTO coachDTO;
    private String swimmerEmail;
    private String key;
    private String commentText;

    public CoachAddCommentDTO(UserDTO coachDTO, String swimmerEmail, String key, String commentText) {
        this.coachDTO = coachDTO;
        this.swimmerEmail = swimmerEmail;
        this.key = key;
        this.commentText = commentText;
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
