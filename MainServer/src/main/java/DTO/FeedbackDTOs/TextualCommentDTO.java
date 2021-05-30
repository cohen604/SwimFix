package DTO.FeedbackDTOs;

public class TextualCommentDTO {

    private DateDTO dateDTO;
    private String coachId;
    private String comment;

    public TextualCommentDTO(DateDTO dateDTO, String coachId, String comment) {
        this.dateDTO = dateDTO;
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
