package Domain.Streaming;
import DTO.ConvertedVideoDTO;
import org.opencv.core.Mat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Video {

    private static AtomicInteger id = new AtomicInteger(0);

    private String path; // The path the video saved into
    private String videoType; // The video type must be in the format ".type"
    protected List<Mat> video; // The original frames of the video
    private int height;
    private int width;
    VideoHandler videoHandler; // The video handler for doing

    public Video(ConvertedVideoDTO convertedVideoDTO) {
        this.videoType = convertedVideoDTO.getVideoType();
        //TODO generate here a unique string path that recognize the user so we can load later
        this.path = generateFileName() + this.videoType;
        this.videoHandler = new VideoHandler();
        this.video =  videoHandler.getFrames(convertedVideoDTO.getBytes(), this.path);
        if(isVideoExists()) {
            this.height = this.video.get(0).height();
            this.width = this.video.get(0).width();
        }
    }

    protected String generateFileName() {
        int number =  id.getAndIncrement();
        return "clientVideos/videoTmp"+number;
    }

    /**
     * constructor
     * @precondition convertedVideoDTO not null
     * @param convertedVideoDTO - the data
     * @param path - the path of the video
     */
    public Video(ConvertedVideoDTO convertedVideoDTO, String path) {
        this.videoType = convertedVideoDTO.getVideoType();
        this.path = path;
        this.videoHandler = new VideoHandler();
        this.video =  videoHandler.getFrames(convertedVideoDTO.getBytes(), this.path);
        if(isVideoExists()) {
            this.height = this.video.get(0).height();
            this.width = this.video.get(0).width();
        }
    }

    public Video(String path, String videoType) {
        this.path = path;
        this.videoType = videoType;
        this.videoHandler = new VideoHandler();
        this.video = this.videoHandler.getFrames(path);
        if(isVideoExists()) {
            this.height = this.video.get(0).height();
            this.width = this.video.get(0).width();
        }
    }

    /***
     * Copy Constructor
     * @precondition other not null
     * @param other
     */
    public Video(Video other) {
        this.path = other.path;
        this.videoType = other.videoType;
        this.video = other.video;
        this.height = other.height;
        this.width = other.width;
        this.videoHandler = other.videoHandler;

    }

    /**
     * The function return if the video is Exists
     * @return true if the video is exits
     */
    public boolean isVideoExists() {
        if(this.path == null || this.path.isEmpty())
            return false;
        if(this.video==null) {
            return false;
        }
        return true;
    }

    /**
     * The function return a list of bytes of the video if he exits
     * @return list of bytes
     * @postcondition None
     * @precondition list size == number of frames in the video
     */
    public List<byte[]> getVideo() {
        if(isVideoExists()) {
            return videoHandler.getFramesBytes(this.video);
        }
        return null;
    }

    /**
     * The function return the height of the video if he exits
     * @return the height
     */
    public int getHeight() {
        if(isVideoExists()) {
            return height;
        }
        return -1;
    }

    /**
     * The function return the width of the video if he exits
     * @return the width
     */
    public int getWidth() {
        if(isVideoExists()) {
            return width;
        }
        return -1;
    }

    /**
     * The function return the path of the video if he exits
     * @return the path
     */
    public String getPath() {
        if(isVideoExists()) {
            return path;
        }
        return null;
    }

    /**
     * The function return the type of the video if he exits
     * @return the type
     */
    public String getVideoType() {
        if(isVideoExists()) {
            return videoType;
        }
        return null;
    }
}
