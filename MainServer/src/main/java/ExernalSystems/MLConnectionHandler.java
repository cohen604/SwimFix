package ExernalSystems;

import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import org.opencv.core.Mat;

import java.util.List;

public interface MLConnectionHandler {
    public TaggedVideo getSkeletons(Video video);

}
