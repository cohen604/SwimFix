package ExernalSystems;

import Domain.Streaming.IVideo;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;


public interface MLConnectionHandler {

    List<ISwimmingSkeleton> getSkeletons(IVideo video, int size, int height, int width) throws Exception;

}
