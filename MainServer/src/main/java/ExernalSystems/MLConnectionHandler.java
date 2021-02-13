package ExernalSystems;

import Domain.Streaming.IVideo;
import Domain.Streaming.TaggedVideo;


public interface MLConnectionHandler {
    TaggedVideo getSkeletons(IVideo video);

}
