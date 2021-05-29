package Domain.Streaming;

import DTO.FeedbackDTOs.FeedbackVideoStreamer;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Errors.Interfaces.SwimmingError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The interface represent a contract how feedback video need to behave in our system
 */
public interface IFeedbackVideo {

    /**
     * The function generate a feedback view link with the given detectors filters
     * @return the link to view the feedback
     */
    FeedbackVideoStreamer generateFeedbackStreamer();

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
     * The function get the swimming skeletons
     * @return swimming skeletons
     */
    List<ISwimmingSkeleton> getSwimmingSkeletons();

    /**
     * The function return the path of the ml skeletons csv
     * @return path of the ml skeletons csv
     */
    String getMLSkeletonsPath();

    /**
     * The function return the path of the skeletons csv
     * @return path of the ml skeletons csv
     */
    String getSkeletonsPath();

    /**
     * The function return the swimming error map
     * @return the swimming error map
     */
    Map<Integer, List<SwimmingError>> getSwimmingErrors();

    /**
     * The function return the swimming period times
     * @return the swimming period time
     */
    ISwimmingPeriodTime getSwimmingPeriodTime();

    /**
     * The function return the date of the ISwimmingFeedback
     * @return local date time of the feedback video
     */
    LocalDateTime getDate();

    int getNumberOfErrors();

    int getNumberOfComments();
}
