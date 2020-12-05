package Domain.Streaming;

import Storage.Daos;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;
import DTO.ConvertedVideoDTO;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Document(collation = "Video")
public class Video {

    @Id
    private String path; // The path the video saved into

    private String videoType; // The video type must be in the format ".type"
    // The original frames of the video
    protected List<Mat> video;
    private int height;
    private int width;
    // The video handler for doing
    VideoHandler videoHandler;

    public Video(ConvertedVideoDTO convertedVideoDTO) {
        this.videoType = convertedVideoDTO.getVideoType();
        //TODO generate here a unique string path that recognize the user so we can load later
        this.path = "clientVideos/videoTmp"+this.videoType;
        this.videoHandler = new VideoHandler();
        this.video =  videoHandler.getFrames(convertedVideoDTO.getBytes(), this.path);
        //TODO check if the video is empty?
        this.height = this.video.get(0).height();
        this.width = this.video.get(0).width();
        // save the information in the video repo
        Daos.getInstance().getVideoDao().insert(this);
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
