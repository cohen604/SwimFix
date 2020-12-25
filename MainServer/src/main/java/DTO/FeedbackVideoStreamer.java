package DTO;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * This class is for returning the a streaming path to the customer for view the feedbackVideo
 */
public class FeedbackVideoStreamer {

    private String path;
    //TODO need to add here the List of comments because we cant send them in with the streaming video
        private List<String> detectors;

    public FeedbackVideoStreamer(File file, List<String> detectors) {
        this.path = file.getPath();
        this.detectors = detectors;
    }

    public String getPath() {
        return this.path;
    }
}
