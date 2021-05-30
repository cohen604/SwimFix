package DTO.SwimmerDTOs;

import DTO.UserDTOs.UserDTO;

public class DeleteFeedbackDTO {

    private UserDTO user;
    private DateDayDTO date;
    private String link;

    public DeleteFeedbackDTO(UserDTO user, DateDayDTO date, String link) {
        this.user = user;
        this.date = date;
        this.link = link;
    }

    public UserDTO getUser() {
        return user;
    }

    public DateDayDTO getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }
}
