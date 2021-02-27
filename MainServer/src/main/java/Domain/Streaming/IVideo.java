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

    /***
     * The function return the video bytes
     * @return list of bytes of the video
     */
    List<byte[]> getVideo();

    /**
     * The function return the height of the frame in the video
     * @return the height of the frame in the video
     */
    int getHeight();

    /**
     * The function return the width of the frame in the video
     * @return the width of the frame in the video
     */
    int getWidth();

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

    /**
     * The function return the frames of the video in mat format
     * @return the video frames as Mat list
     */
    List<Mat> getVideoFrames();

    /**
     * The function return the IVideoHandler of the video
     * @return the IVideoHandler
     */
    IVideoHandler getIVideoHandler();

}
