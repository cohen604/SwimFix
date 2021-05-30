package Domain.Streaming;

import java.time.LocalDateTime;

public interface ITextualComment {

    LocalDateTime getDate();

    String getCoachId();

    String getText();

}
