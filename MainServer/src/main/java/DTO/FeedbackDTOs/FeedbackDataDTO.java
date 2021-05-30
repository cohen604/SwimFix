package DTO.FeedbackDTOs;

import java.time.LocalDateTime;
import java.util.List;

public class FeedbackDataDTO {

    private String swimmerEmail;
    private String link;
    private String key;
    private DateDTO date;
    private List<TextualCommentDTO> comments;

    public FeedbackDataDTO(String swimmerEmail,
                           String link,
                           String key,
                           LocalDateTime date,
                           List<TextualCommentDTO> comments) {
        this.swimmerEmail = swimmerEmail;
        this.link = link;
        this.key = key;
        this.date = new DateDTO(
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getHour(),
                date.getMinute(),
                date.getSecond()
        );
        this.comments = comments;
    }

    public String getSwimmerEmail() {
        return swimmerEmail;
    }

    public void setSwimmerEmail(String swimmerEmail) {
        this.swimmerEmail = swimmerEmail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DateDTO getDate() {
        return date;
    }

    public void setDate(DateDTO date) {
        this.date = date;
    }

    public List<TextualCommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<TextualCommentDTO> comments) {
        this.comments = comments;
    }
}
