package DTO.CoachDTOs;

import DTO.FeedbackDTOs.DateDTO;
import DTO.FeedbackDTOs.FeedbackGraphsDTO;
import DTO.FeedbackDTOs.TextualCommentDTO;

import java.time.LocalDateTime;
import java.util.List;

public class CoachFeedbackDataDTO {

    private String swimmerEmail;
    private String link;
    private String key;
    private DateDTO date;
    private List<TextualCommentDTO> comments;
    private FeedbackGraphsDTO graphs;

    public CoachFeedbackDataDTO(String swimmerEmail,
                                String link,
                                String key,
                                LocalDateTime date,
                                List<TextualCommentDTO> comments,
                                FeedbackGraphsDTO graphs) {
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
        this.graphs = graphs;
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

    public FeedbackGraphsDTO getGraphs() {
        return graphs;
    }

    public void setGraphs(FeedbackGraphsDTO graphs) {
        this.graphs = graphs;
    }
}
