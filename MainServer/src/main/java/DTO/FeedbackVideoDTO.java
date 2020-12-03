package DTO;

import Domain.Streaming.FeedbackVideo;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

/**
 * This class is for returning the feedbackvideo to the customer for saving it.
 */
public class FeedbackVideoDTO {

    private String path;
    private String type;
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
