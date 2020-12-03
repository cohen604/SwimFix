package DTO;

import java.io.File;

/**
 * This class is for returning the a streaming path to the customer for view the feedbackVideo
 */
public class FeedbackVideoStreamer {

    private String path;
    //TODO need to add here the List of comments because we cant send them in with the streaming video

    public FeedbackVideoStreamer(File file) {
        this.path = file.getPath();
    }

    public String getPath() {
        return this.path;
    }
}
