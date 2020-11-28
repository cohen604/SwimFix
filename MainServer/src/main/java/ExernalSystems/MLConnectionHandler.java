package ExernalSystems;

import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;

public interface MLConnectionHandler {
    public TaggedVideo tagVideo(Video video);

}
