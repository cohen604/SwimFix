package DTO;

import java.util.List;

/**
 * This class is for returning the feedbackvideo to the customer for saving it.
 */
public class FeedbackVideoDTO {

    private String path;
    private String type; // TODO - why we need this?
    private byte[] bytes;
    private List<String> comments;

    public FeedbackVideoDTO(String path, byte[] bytes) {
        this.path = path;
        this.bytes = bytes;
    }

    public FeedbackVideoDTO(String path, String type, byte[] bytes, List<String> comments) {
        this.path = path;
        this.type = type;
        this.bytes = bytes;
        this.comments = comments;
    }

    public String getPath() {
        return path;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
