package DTO.CoachDTOs;

import DTO.FeedbackDTOs.DateDTO;

import java.time.LocalDateTime;

public class CoachSwimmerFeedbackDTO {

    private String swimmersEmail;
    private DateDTO dateDTO;
    private String link;
    private String key;
    private int numberOfErrors;
    private int numberOfComments;

    public CoachSwimmerFeedbackDTO(String swimmersEmail,
                                   LocalDateTime localDateTime,
                                   String link,
                                   String key,
                                   int numberOfErrors,
                                   int numberOfComments) {
        this.swimmersEmail = swimmersEmail;
        this.dateDTO = new DateDTO(
                localDateTime.getYear(),
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                localDateTime.getHour(),
                localDateTime.getMinute(),
                localDateTime.getSecond());
        this.link = link;
        this.key = key;
        this.numberOfErrors = numberOfErrors;
        this.numberOfComments = numberOfComments;
    }

    public String getSwimmersEmail() {
        return swimmersEmail;
    }

    public void setSwimmersEmail(String swimmersEmail) {
        this.swimmersEmail = swimmersEmail;
    }

    public DateDTO getDateDTO() {
        return dateDTO;
    }

    public void setDateDTO(DateDTO dateDTO) {
        this.dateDTO = dateDTO;
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

    public int getNumberOfErrors() {
        return numberOfErrors;
    }

    public void setNumberOfErrors(int numberOfErrors) {
        this.numberOfErrors = numberOfErrors;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }
}
