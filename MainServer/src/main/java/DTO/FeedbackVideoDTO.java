package DTO;

import java.util.List;

/**
 * This class is for returning the feedbackvideo to the customer for saving it.
 */
public class FeedbackVideoDTO {

    private String type;
    private byte[] bytes;
    private List<String> comments;

    public FeedbackVideoDTO(String type, byte[] bytes, List<String> comments) {
        this.bytes = bytes;
        this.comments = comments;
        this.type = type;
    }

}
