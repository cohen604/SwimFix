package Domain.Streaming;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FeedbackVideo extends Video {

    VisualComment visualComment;
    TextualComment textualComment;
    List<SwimmingError> errorList;
    TaggedVideo taggedVideo;

    public FeedbackVideo(Video video, TaggedVideo taggedVideo) {
        super(video);
        this.taggedVideo = taggedVideo;
    }

    public FeedbackVideo(Video video, TaggedVideo taggedVideo, List<SwimmingError> errorList) {
        super(video);
        this.taggedVideo = taggedVideo;
        this.errorList = errorList;
    }

    public FeedbackVideoDTO generateFeedbackDTO() {
        List<SwimmingTag> swimmingTags = null;
        List<Object> visualComments = null; //TODO
        byte[] outputVideo = this.videoHandler.getFeedBackVideo(this.video, swimmingTags, errorList, visualComments);
        List<String> textualComments = new LinkedList<>(); //TODO
        String path = this.videoHandler.getDesPath();
        String type = this.videoHandler.getOutputType();
        return new FeedbackVideoDTO( path, type, outputVideo, textualComments);
    }

    public FeedbackVideoStreamer generateFeedbackStreamer() {
        List<SwimmingTag> swimmingTags = null;
        List<Object> visualComments = null; //TODO
        File file = this.videoHandler.getFeedBackVideoFile(this.video, swimmingTags, errorList, visualComments);
        if(file == null) {
            return null;
        }
        return new FeedbackVideoStreamer(file);
    }


}