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
        //TODO check if the video is empty?
        this.height = this.video.get(0).height();
        this.width = this.video.get(0).width();
    }

    public String generateFileName() {
        int number =  id.getAndIncrement();
        return "clientVideos/videoTmp"+number;
    }

    public Video(ConvertedVideoDTO convertedVideoDTO, String path) {
        this.videoType = convertedVideoDTO.getVideoType();
        this.path = path;
        this.videoHandler = new VideoHandler();
        this.video =  videoHandler.getFrames(convertedVideoDTO.getBytes(), this.path);
        //TODO check if the video is empty?
        this.height = this.video.get(0).height();
        this.width = this.video.get(0).width();
    }

    public Video(String path, String videoType) {
        this.path = path;
        this.videoType = videoType;
        this.videoHandler = new VideoHandler();
        this.video = this.videoHandler.getFrames(path);
        //TODO check if the video is empty?
        this.height = this.video.get(0).height();
        this.width = this.video.get(0).width();
    }

    /***
     * Copy Constructor
     * @param other
     */
    public Video(Video other) {
        this.videoType = other.videoType;
        this.video = other.video;
        this.height = other.height;
        this.width = other.width;
        this.videoHandler = other.videoHandler;
    }

    @Override
    public String toString() {
        return "Video [ path: " + this.path +
                ", type: " + this.videoType +
                "]";
    }

    /**
     * The function return a list of bytes of the video
     * @return list of bytes
     * @postcondition None
     * @precondition list size == number of frames in the video
     */
    public List<byte[]> getVideo() {
        if(this.video == null || this.video.isEmpty()) {
            return null;
        }
        return videoHandler.getFramesBytes(this.video);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getPath() {
        return path;
    }

    public String getVideoType() {
        return videoType;
    }
}
