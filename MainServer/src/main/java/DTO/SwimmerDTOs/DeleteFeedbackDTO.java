package DTO.SwimmerDTOs;

import DTO.UserDTOs.UserDTO;

public class DeleteFeedbackDTO {

    private UserDTO user;
    private DateDTO date;
    private String link;

    public DeleteFeedbackDTO(UserDTO user, DateDTO date, String link) {
        this.user = user;
        this.date = date;
        this.link = link;
    }

    public UserDTO getUser() {
        return user;
    }

    public DateDTO getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }
}
