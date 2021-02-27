package Domain.Streaming;

import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;

import java.util.List;

/**
 * The interface represent a contract how feedback video need to behave in our system
 */
public interface IFeedbackVideo {

    /**
     * The function generated feedbackTDO
     * @return the feedbackTDO generated
     */
    FeedbackVideoDTO generateFeedbackDTO();

    /**
     * The function generate a feedback view link with the given detectors filters
     * @param detectors - the names of the detectors in the system
     * @return the link to view the feedback
     */
    FeedbackVideoStreamer generateFeedbackStreamer(List<String> detectors);

    /**
     * The function update the video
     */
    void updateVideo();

    /**
     * The function return if the feedback video updated
     * @return true if the feedback updated
     */
    boolean isFeedbackUpdated();

    /**
     * The function return the path of the feedback
     * @return the path of the video
     */
    String getPath();

    /**
     * The function return the original video of the feedback
     * @return the IVideo of feedback
     */
    IVideo getIVideo();

}
