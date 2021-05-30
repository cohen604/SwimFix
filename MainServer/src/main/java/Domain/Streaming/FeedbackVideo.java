package Domain.Streaming;
import DTO.FeedbackDTOs.FeedbackVideoStreamer;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Errors.Interfaces.SwimmingError;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

public class FeedbackVideo extends Video implements IFeedbackVideo {

    private Map<Integer, List<SwimmingError>> errorMap;
    private TaggedVideo taggedVideo; // swimming skeletons
    private String path; // The feedback video path to insert into
    private boolean feedbackUpdated; // this flag will be used for knowing when the feedback video is updated and need to generate new feedback file
    private ISwimmingPeriodTime periodTime;
    private List<TextualComment> comments;
    private VisualComment visualComment;

    private final Object lockComments;

    public FeedbackVideo(IVideo video, TaggedVideo taggedVideo, Map<Integer, List<SwimmingError>> errorMap,
                         String path, ISwimmingPeriodTime periodTime, List<TextualComment> comments) {
        super(video);
        this.taggedVideo = taggedVideo;
        this.errorMap = errorMap;
        this.path = path;
        this.feedbackUpdated = false;
        this.periodTime = periodTime;
        this.comments = comments;
        this.lockComments = new Object();
        //TODO
        this.visualComment = null;
    }

    /**
     * The function generate a feedback video for streaming
     * @return feedback streamer if the video exists
     */
    @Override
    public FeedbackVideoStreamer generateFeedbackStreamer() {
        File file = new File(this.path);
        if(!file.exists()) {
            return null;
        }
        return new FeedbackVideoStreamer(this.path);
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
    public LocalDateTime getDate() {
        String dateString = getDateString(this.path);
        String[] values = dateString.split("-");
        int year = Integer.valueOf(values[0]);
        int month = Integer.valueOf(values[1]);
        int day = Integer.valueOf(values[2]);
        int hour = Integer.valueOf(values[3]);
        int mintes = Integer.valueOf(values[4]);
        int seconds = Integer.valueOf(values[5]);
        return LocalDateTime.of(year, month, day, hour, mintes, seconds);
    }

    @Override
    public int getNumberOfErrors() {
        int sum = 0;
        for(List<SwimmingError> errors : errorMap.values()) {
            sum += errors.size();
        }
        return sum;
    }

    @Override
    public int getNumberOfComments() {
        return this.comments.size();
    }

    @Override
    public Collection<? extends ITextualComment> getComments() {
        return this.comments;
    }

    @Override
    public ITextualComment addComment(String coachEmail, String commentText) {
        synchronized (lockComments) {
            if(commentText!=null && !commentText.isEmpty()) {
                TextualComment textualComment = new TextualComment(coachEmail, commentText);
                this.comments.add(textualComment);
                return textualComment;
            }
        }
        return null;
    }

    @Override
    public void removeComment(ITextualComment comment) {
        synchronized (lockComments) {
            boolean deleted = false;
            for (int i=0; i<comments.size() && !deleted; i++) {
                TextualComment textualComment = this.comments.get(i);
                if(textualComment.getDate().equals(comment.getDate())
                        && textualComment.getCoachId().equals(comment.getCoachId())) {
                    comments.remove(i);
                    deleted = true;
                }
            }
        }
    }

    private String getDateString(String path) {
        int indexOfSlash = path.lastIndexOf("\\") + 1;
        String onlyDate = path.substring(indexOfSlash);
        int indexOfDot = onlyDate.lastIndexOf(".");
        return onlyDate.substring(0, indexOfDot);
    }

    @Override
    public boolean isFeedbackUpdated() {
        return feedbackUpdated;
    }

    public List<TextualComment> getTextualComments() {
        return this.comments;
    }
}