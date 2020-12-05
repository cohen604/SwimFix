package Domain.Streaming;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class FeedbackVideo extends Video {

    private VisualComment visualComment;
    private TextualComment textualComment;
    private List<SwimmingError> errorList;
    private TaggedVideo taggedVideo;
    // The feedback video path to save into
    private String path;
    // The file generated in the video handler for the for the feedback
    private File feedbackFile;
    // this flag will be used for knowing when the feedback video is updated and need to generate new feedback file
    private boolean feedbackUpdated;

    public FeedbackVideo(Video video, TaggedVideo taggedVideo, List<SwimmingError> errorList) {
        super(video);
        this.taggedVideo = taggedVideo;
        this.errorList = errorList;
        //TODO generate here a unique string path that recognize the user so we can load later
        this.path = "clientVideos/feedbackvideoTmp.mp4";
        this.feedbackFile = null;
        this.feedbackUpdated = false;
    }

    /**
     * The function update the feedback file
     * @precondition feedback file is Null or feedback is updated, have the original video frames
     * @postcondition generated new feedback file
     */
    private void updateFeedbackFile() {
        List<SwimmingTag> swimmingTags = null;
        List<Object> visualComments = null; //TODO
        if(this.feedbackFile == null || this.feedbackUpdated) {
            File file = this.videoHandler.getFeedBackVideoFile(this.path, this.video, swimmingTags,
                    errorList, visualComments);
            if(file != null) {
                this.feedbackFile = file;
                this.feedbackUpdated = false;
            }
        }
    }

    public FeedbackVideoDTO generateFeedbackDTO() {
        updateFeedbackFile();
        if(this.feedbackFile == null) {
            return null;
        }
        byte[] feedbackBytes = this.videoHandler.readVideo(this.feedbackFile.getPath());
        if(feedbackBytes == null) {
            return null;
        }
        List<String> textualComments = new LinkedList<>(); //TODO
        String path = this.feedbackFile.getPath();
        String type = path.substring(path.lastIndexOf("."));
        return new FeedbackVideoDTO( path, type, feedbackBytes, textualComments);
    }


    /**
     * The function generate a feedback video for streaming
     * @return feedback streamer
     */
    public FeedbackVideoStreamer generateFeedbackStreamer() {
        updateFeedbackFile();
        if(this.feedbackFile == null) {
            return null;
        }
        return new FeedbackVideoStreamer(this.feedbackFile);
    }

}