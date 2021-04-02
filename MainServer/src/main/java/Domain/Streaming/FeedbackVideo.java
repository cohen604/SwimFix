package Domain.Streaming;
import DTO.FeedbackVideoStreamer;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Errors.Interfaces.SwimmingError;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackVideo extends Video implements IFeedbackVideo {

    private Map<Integer, List<SwimmingError>> errorMap;
    private TaggedVideo taggedVideo; // swimming skeletons
    private String path; // The feedback video path to insert into
    private boolean feedbackUpdated; // this flag will be used for knowing when the feedback video is updated and need to generate new feedback file
    private VisualComment visualComment;
    private TextualComment textualComment;
    private ISwimmingPeriodTime periodTime;

    public FeedbackVideo(IVideo video, TaggedVideo taggedVideo, Map<Integer, List<SwimmingError>> errorMap,
                         String path, ISwimmingPeriodTime periodTime) {
        super(video);
        this.taggedVideo = taggedVideo;
        this.errorMap = errorMap;
        this.path = path;
        this.feedbackUpdated = false;
        this.periodTime = periodTime;
        //TODO
        this.visualComment = null;
        this.textualComment = null;
    }

    public FeedbackVideo(IVideo video, TaggedVideo taggedVideo, String path) {
        super(video);
        this.taggedVideo = taggedVideo;
        this.errorMap = new HashMap<>();
        this.path = path;
        //TODO
        this.visualComment = null;
        this.textualComment = null;
    }

    /**
     * The function generate a feedback video for streaming
     * @param detectors - list of detectors
     * @return feedback streamer if the video exists
     */
    @Override
    public FeedbackVideoStreamer generateFeedbackStreamer(List<String> detectors) {
        if(detectors==null) {
            return null;
        }
        File file = new File(this.path);
        if(!file.exists()) {
            return null;
        }
        return new FeedbackVideoStreamer(file, detectors);
    }

    /**
     * The function turn up the feedback to need to be updated
     */
    @Override
    public void updateVideo() {
        this.feedbackUpdated = true;
    }

    /**
     * Getters
     */

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public IVideo getIVideo() {
        return this;
    }

    @Override
    public List<ISwimmingSkeleton> getSwimmingSkeletons() {
        return this.taggedVideo.getTags();
    }

    @Override
    public String getMLSkeletonsPath() {
        return this.taggedVideo.getMlSkeletonsPath();
    }

    @Override
    public String getSkeletonsPath() {
        return this.taggedVideo.getskeletonsPath();
    }

    @Override
    public Map<Integer, List<SwimmingError>> getSwimmingErrors() {
        return this.errorMap;
    }

    @Override
    public ISwimmingPeriodTime getSwimmingPeriodTime() {
        return this.periodTime;
    }


    @Override
    public boolean isFeedbackUpdated() {
        return feedbackUpdated;
    }
}