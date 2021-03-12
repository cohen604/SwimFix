package Domain.Streaming;

import DTO.FeedbackVideoStreamer;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;

import java.util.List;
import java.util.Map;

/**
 * The interface represent a contract how feedback video need to behave in our system
 */
public interface IFeedbackVideo {

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

    /**
     * TODO comment
     * @return
     */
    List<ISwimmingSkeleton> getSwimmingSkeletons();

    String getMLSkeletonsPath();

    String getSkeletonsPath();

    Map<Integer, List<SwimmingError>> getSwimmingErrors();

}
