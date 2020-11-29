package DTO;

import java.util.List;

public class FeedbackVideoDTO {

    private String type;
    private byte[] bytes;
    private List<String> comments;

    public FeedbackVideoDTO(byte[] bytes, List<String> comments, String type) {
        this.bytes = bytes;
        this.comments = comments;
        this.type = type;
    }

}
