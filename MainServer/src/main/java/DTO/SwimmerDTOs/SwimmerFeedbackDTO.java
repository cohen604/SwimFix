package DTO.SwimmerDTOs;

import DTO.FeedbackDTOs.DateDTO;

import java.time.LocalDateTime;

public class SwimmerFeedbackDTO {

    private String path;
    private DateDTO date;

    public SwimmerFeedbackDTO(String path, LocalDateTime date) {
        this.path = path;
        this.date = new DateDTO(
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getHour(),
                date.getMinute(),
                date.getSecond()
        );
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public DateDTO getDate() {
        return date;
    }

    public void setDate(DateDTO date) {
        this.date = date;
    }
}
