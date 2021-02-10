package Domain.Streaming;

import org.opencv.core.Mat;

import java.util.List;

public interface IVideo {

    boolean isVideoExists();
    List<byte[]> getVideo();
    int getHeight();
    int getWidth();
    String getPath();
    String getVideoType();
    List<Mat> getVideoFrames();

}
