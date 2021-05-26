package Domain.Streaming;
import DTO.FeedbackDTOs.ConvertedVideoDTO;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Video implements IVideo {
    
    private String path; // The path the video saved into
    private String videoType; // The video type must be in the format ".type"

    public Video(String videoPath, String videoType) {
        this.path = videoPath;
        this.videoType = videoType;
    }

    /**
     * constructor
     * @precondition convertedVideoDTO not null
     * @param convertedVideoDTO - the data
     * @param path - the path of the video
     */
    public Video(ConvertedVideoDTO convertedVideoDTO,
                 String path) {
        this.videoType = convertedVideoDTO.getVideoType();
        this.path = path;
    }

    /**
     * constructor
     * @param path the path of the video
     * @precondition path must have char .
     */
    public Video(String path) {
        this.path = path;
        int dotIndex = path.lastIndexOf(".");
        this.videoType = path.substring(dotIndex);
    }

    /***
     * Copy Constructor
     * @precondition other not null
     * @param other
     */
    public Video(Video other) {
        this.path = other.path;
        this.videoType = other.videoType;
    }

    public Video(IVideo iVideo) {
        this.path = iVideo.getPath();
        this.videoType = iVideo.getVideoType();
    }

    /**
     * The function return if the video is Exists
     * @return true if the video is exits
     */
    public boolean isVideoExists() {
        if(this.path == null || this.path.isEmpty())
            return false;
        return Files.exists(Paths.get(this.path));
    }

    /**
     * The function return the path of the video if he exits
     * @return the path
     */
    public String getPath() {
        if(isVideoExists()) {
            return this.path;
        }
        return null;
    }

    /**
     * The function return the type of the video if he exits
     * @return the type
     */
    public String getVideoType() {
        return this.videoType;
    }
}
