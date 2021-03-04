package Domain.Streaming;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    // The file generated in the video handler for the for the feedback
    private File feedbackFile;
    // this flag will be used for knowing when the feedback video is updated and need to generate new feedback file
    private boolean feedbackUpdated;

    public FeedbackVideo(IVideo video, TaggedVideo taggedVideo, Map<Integer, List<SwimmingError>> errorMap) {
        super(video);
        this.taggedVideo = taggedVideo;
        this.errorMap = errorMap;
        //TODO generate here a unique string path that recognize the user so we can load later
        this.path = generateFileName()+"_feedback.mp4";
        this.feedbackFile = null;
        this.feedbackUpdated = false;
        //TODO
        this.visualComment = null;
        this.textualComment = null;
    }

    public FeedbackVideo(IVideo video, TaggedVideo taggedVideo, Map<Integer, List<SwimmingError>> errorMap,
                         String folderPath) {
        super(video);
        this.taggedVideo = taggedVideo;
        this.errorMap = errorMap;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        this.path = folderPath + "\\" + LocalDateTime.now().format(formatter) + ".mp4";
        this.feedbackFile = null;
        this.feedbackUpdated = false;
        //TODO
        this.visualComment = null;
        this.textualComment = null;
    }


    /**
     * The function update the feedback file if video exists
     * @precondition feedback file is Null or feedback is updated, have the original video frames
     * @postcondition generated new feedback file
     */
    private void updateFeedbackFile() {
        if(isVideoExists()) {
            List<ISwimmingSkeleton> swimmingSkeletons = null;
            if (this.taggedVideo != null) {
                swimmingSkeletons = this.taggedVideo.getTags();
            }
            List<Object> visualComments = null; //TODO
            if (this.feedbackFile == null || this.feedbackUpdated) {
                File file = this.videoHandler.getFeedBackVideoFile(this.path, this.video, swimmingSkeletons,
                        errorMap, visualComments);
                if (file != null) {
                    this.feedbackFile = file;
                    this.feedbackUpdated = false;
                }
            }
        }
    }

    /**
     * The function return a feedback video
     * @return feedback video if the video exists
     */
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
     * @param detectors - list of detectors
     * @return feedback streamer if the video exists
     */
    public FeedbackVideoStreamer generateFeedbackStreamer(List<String> detectors) {
        if(detectors==null) {
            return null;
        }
        updateFeedbackFile();
        if(this.feedbackFile == null) {
            return null;
        }
        return new FeedbackVideoStreamer(this.feedbackFile, detectors);
    }

    /**
     * The function turn up the feedback to need to be updated
     */
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

    public boolean isFeedbackUpdated() {
        return feedbackUpdated;
    }
}