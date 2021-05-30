package Domain.Streaming;

import java.time.LocalDateTime;

public class TextualComment implements ITextualComment {

    private LocalDateTime dateTime;
    private String coachId;
    private String text;

    public TextualComment(String coachId, String text) {
        this.dateTime = LocalDateTime.now();
        this.coachId = coachId;
        this.text = text;
    }

    public TextualComment(LocalDateTime dateTime, String coachId, String text) {
        this.dateTime = dateTime;
        this.coachId = coachId;
        this.text = text;
    }

    @Override
    public LocalDateTime getDate() {
        return this.dateTime;
    }

    @Override
    public String getCoachId() {
        return this.coachId;
    }

    @Override
    public String getText() {
        return this.text;
    }


}
