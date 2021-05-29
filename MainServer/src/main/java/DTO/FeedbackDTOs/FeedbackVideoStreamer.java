package DTO.FeedbackDTOs;


/**
 * This class is for returning the a streaming path to the customer for view the feedbackVideo
 */
public class FeedbackVideoStreamer {

    private String path;

    public FeedbackVideoStreamer(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

}
