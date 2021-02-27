package Domain.Streaming;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import org.opencv.core.Mat;

import java.io.File;
import java.util.List;
import java.util.Map;

/***
 * The interface represent a contract of what functionality videoHandler need to have
 */
public interface IVideoHandler {

    /**
     * The function save video from byte array to a given path
     * @param video - the byte array
     * @param path - the path to save to
     * @return true if the video where saved
     */
    boolean saveFramesBytes(byte[] video, String path);

    /**
     * The function delete a video from a given path
     * @param path - the path to delete from
     * @return true if the video deleted
     */
    boolean deleteVideo(String path);

    /**
     * The function return the byte array of a video from a given path
     * @param path - the string of file
     * @return the byte array
     */
    byte[] readVideo(String path);

    /**
     * The function return the list of frames from a given video path
     * @param desPath - the video path
     * @return the list of frames
     */
    List<Mat> getFrames(String desPath);

    /**
     * The function return the list of frames from a given video path
     * @param video - the video bytes
     * @param desPath - the destination path
     * @return the list of frames
     */
    List<Mat> getFrames(byte[] video, String desPath);

    /**
     * The function convert list of frames to list of bytes
     * @param frames - the frames
     * @return the list of frames bytes
     */
    List<byte[]> getFramesBytes(List<Mat> frames);

    /***
     * The function save the list of frames to a given path
     * @param path - the path to save too
     * @param frames - the frames to save
     * @return the file created
     */
    File saveFrames(String path, List<Mat> frames);

    /**
     * The function creates a feedback video for a video from given data
     * @param desPath - the path to save too
     * @param frames - the frames of the feedback
     * @param dots - the skeletons
     * @param errors - the errors
     * @param visualComments - the comments for the video
     * @return the file of the feedback
     */
    File getFeedBackVideoFile(String desPath, List<Mat> frames, List<ISwimmingSkeleton> dots,
                              Map<Integer, List<SwimmingError>> errors,
                              List<Object> visualComments);

}
