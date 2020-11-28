package Domain.Streaming;

import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;

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

    public FeedbackVideoDTO generateDTO() {
        //TODO create new video from stream of bytes and objects
        return new FeedbackVideoDTO(this.video);
    }
}
