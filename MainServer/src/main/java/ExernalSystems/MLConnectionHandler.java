package ExernalSystems;

import Domain.Streaming.IVideo;
import Domain.Streaming.TaggedVideo;
import Domain.SwimmingData.ISwimmingSkeleton;

import java.util.List;


public interface MLConnectionHandler {

    List<ISwimmingSkeleton> getSkeletons(IVideo video, int size, int height, int width);

}
