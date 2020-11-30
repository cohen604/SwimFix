package Domain.Streaming;

import DTO.ConvertedVideoDTO;
import org.opencv.core.Mat;

import java.util.List;

public class Video {

    protected List<Mat> video;
    int height;
    int width;

    public Video(ConvertedVideoDTO convertedVideoDTO) {
        VideoHandler videoHandler = new VideoHandler();
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
        this.video = other.video;
    }

    public List<byte[]> getVideo() {
        VideoHandler videoHandler = new VideoHandler();
        return videoHandler.getFramesBytes(this.video);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
