package Domain.Streaming;

import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;

import java.util.List;

public interface IFeedbackVideo {

    FeedbackVideoDTO generateFeedbackDTO();
    FeedbackVideoStreamer generateFeedbackStreamer(List<String> detectors);
    void updateVideo();
    boolean isFeedbackUpdated();
    String getPath();
    IVideo getIVideo();

}
