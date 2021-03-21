package Domain.Streaming;

import org.opencv.core.Mat;
import org.opencv.video.Video;

import java.util.List;

/***
 * The interface represent a contract what is video in the system
 */
public interface IVideo {

    /**
     * The function checks if the video exists
     * @return true if the video exists
     */
    boolean isVideoExists();

    /**
     * The function return the path of the video
     * @return the path of the video
     */
    String getPath();

    /**
     * The function return the type of the video
     * @return the type of the video
     */
    String getVideoType();

}
