package DTO.FeedbackDTOs;

import java.time.LocalDateTime;

public class TextualCommentDTO {

    private DateDTO dateDTO;
    private String coachId;
    private String comment;

    public TextualCommentDTO(LocalDateTime dateTime, String coachId, String comment) {
        this.dateDTO = new DateDTO(
                dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getHour(),
                dateTime.getMinute(),
                dateTime.getSecond()
        );
        this.coachId = coachId;
        this.comment = comment;
    }

    public DateDTO getDateDTO() {
        return dateDTO;
    }

    public void setDateDTO(DateDTO dateDTO) {
        this.dateDTO = dateDTO;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
