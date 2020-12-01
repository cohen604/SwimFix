package Domain.Streaming;

import DTO.ConvertedVideoDTO;
import org.opencv.core.Mat;

import java.util.List;

public class Video {

    protected String videoType; //this is the video type must be in the format ".type"
    protected List<Mat> video;
    protected int height;
    protected int width;
    protected VideoHandler videoHandler;

    public Video(ConvertedVideoDTO convertedVideoDTO) {
        this.videoType = convertedVideoDTO.getVideoType();
        this.videoHandler = new VideoHandler(this.videoType);
        this.video =  videoHandler.getFrames(convertedVideoDTO.getBytes());
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

    public List<byte[]> getVideo() {
        return videoHandler.getFramesBytes(this.video);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
