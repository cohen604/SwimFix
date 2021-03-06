package Domain.Streaming;
import DTO.ConvertedVideoDTO;
import org.opencv.core.Mat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Video implements IVideo {

    private static AtomicInteger id = new AtomicInteger(0);

    private String path; // The path the video saved into
    private String videoType; // The video type must be in the format ".type"
    protected List<Mat> video; // The original frames of the video
    private int height;
    private int width;
    IVideoHandler videoHandler; // The video handler for doing

    //TODO delete this
    public Video(IVideoHandler videoHandler, ConvertedVideoDTO convertedVideoDTO) {
        this.videoType = convertedVideoDTO.getVideoType();
        this.path = generateFileName() + this.videoType;
        this.videoHandler = videoHandler;
        this.video =  videoHandler.getFrames(convertedVideoDTO.getBytes(), this.path);
        if(isVideoExists()) {
            this.height = this.video.get(0).height();
            this.width = this.video.get(0).width();
        }
    }

    //TODO delete this
    protected String generateFileName() {
        int number =  id.getAndIncrement();
        return "clientVideos\\videoTmp"+number;
    }

    /**
     * constructor
     * @precondition convertedVideoDTO not null
     * @param convertedVideoDTO - the data
     * @param path - the path of the video
     */
    public Video(IVideoHandler videoHandler,
                 ConvertedVideoDTO convertedVideoDTO,
                 String path,
                 LocalDateTime localDateTime) {
        this.videoType = convertedVideoDTO.getVideoType();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        //this.path = path + "\\" + LocalDateTime.now().format(formatter) + this.videoType;
        this.path = path + "\\" + localDateTime.format(formatter) + this.videoType;
        this.videoHandler = videoHandler;
        this.video =  videoHandler.getFrames(convertedVideoDTO.getBytes(), this.path);
        if(isVideoExists()) {
            this.height = this.video.get(0).height();
            this.width = this.video.get(0).width();
        }
    }

    public Video(IVideoHandler videoHandler, String path, String videoType) {
        this.path = path;
        this.videoType = videoType;
        this.videoHandler = videoHandler;
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

    public Video(IVideo iVideo) {
        this.path = iVideo.getPath();
        this.videoType = iVideo.getVideoType();
        this.video = iVideo.getVideoFrames();
        this.height = iVideo.getHeight();
        this.width = iVideo.getWidth();
        this.videoHandler = iVideo.getIVideoHandler();
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

    public List<Mat> getVideoFrames() {
        if (isVideoExists()) {
            return video;
        }
        return  null;
    }

    @Override
    public IVideoHandler getIVideoHandler() {
        return this.videoHandler;
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
