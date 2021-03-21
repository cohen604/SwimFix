package Domain.Streaming;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FeedbackVideo extends Video implements IFeedbackVideo {

    private VisualComment visualComment;
    private TextualComment textualComment;
    private Map<Integer, List<SwimmingError>> errorMap;
    private TaggedVideo taggedVideo;
    // The feedback video path to insert into
    private String path;
    // this flag will be used for knowing when the feedback video is updated and need to generate new feedback file
    private boolean feedbackUpdated;

    public FeedbackVideo(IVideo video, TaggedVideo taggedVideo, Map<Integer, List<SwimmingError>> errorMap,
                         String path) {
        super(video);
        this.taggedVideo = taggedVideo;
        this.errorMap = errorMap;
        this.path = path;
        this.feedbackUpdated = false;
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
    public boolean isFeedbackUpdated() {
        return feedbackUpdated;
    }
}