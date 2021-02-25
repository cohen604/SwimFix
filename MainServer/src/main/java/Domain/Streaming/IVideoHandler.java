package Domain.Streaming;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import org.opencv.core.Mat;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IVideoHandler {

    boolean saveFramesBytes(byte[] video, String path);

    boolean deleteVideo(String path);

    byte[] readVideo(String path);

    List<Mat> getFrames(String desPath);

    List<Mat> getFrames(byte[] video, String desPath);

    List<byte[]> getFramesBytes(List<Mat> frames);

    File saveFrames(String path, List<Mat> frames);

    File getFeedBackVideoFile(String desPath, List<Mat> frames, List<ISwimmingSkeleton> dots,
                              Map<Integer, List<SwimmingError>> errors,
                              List<Object> visualComments);

}
