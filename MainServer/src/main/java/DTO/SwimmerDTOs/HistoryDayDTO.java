package DTO.SwimmerDTOs;

import DTO.UserDTOs.UserDTO;

public class HistoryDayDTO {

    private UserDTO user;
    private DateDTO date;

    public HistoryDayDTO(UserDTO user, DateDTO date) {
        this.user = user;
        this.date = date;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public DateDTO getDate() {
        return date;
    }

    public void setDate(DateDTO date) {
        this.date = date;
    }
}
