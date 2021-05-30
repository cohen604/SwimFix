package DTO.SwimmerDTOs;

import DTO.UserDTOs.UserDTO;

public class HistoryDayDTO {

    private UserDTO user;
    private DateDayDTO date;

    public HistoryDayDTO(UserDTO user, DateDayDTO date) {
        this.user = user;
        this.date = date;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public DateDayDTO getDate() {
        return date;
    }

    public void setDate(DateDayDTO date) {
        this.date = date;
    }
}
