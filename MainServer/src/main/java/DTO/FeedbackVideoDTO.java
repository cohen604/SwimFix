package DTO;

import java.util.List;

public class FeedbackVideoDTO {

    private byte[] bytes;
    private List<String> comments;

    public FeedbackVideoDTO(byte[] bytes, List<String> comments) {
        this.bytes = bytes;
        this.comments = comments;
    }

}
