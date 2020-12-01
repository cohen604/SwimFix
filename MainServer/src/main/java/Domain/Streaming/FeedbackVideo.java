package Domain.Streaming;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;

import java.io.FileOutputStream;
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
        byte[] outputVideo = this.videoHandler.generatedFeedBack(this.video, swimmingTags, errorList, visualComments);
        List<String> textualComments = new LinkedList<>(); //TODO
        return new FeedbackVideoDTO(this.videoType, outputVideo, textualComments);
    }

    public FeedbackVideoStreamer generateFeedbackStreamer() {
        return new FeedbackVideoStreamer(this.videoHandler.getDesPath());
    }


}