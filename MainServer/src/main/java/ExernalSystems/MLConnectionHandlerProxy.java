package ExernalSystems;

import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;

import java.util.List;

public class MLConnectionHandlerProxy implements MLConnectionHandler{
    private MLConnectionHandler mlConnectionHandler = null;

    public MLConnectionHandlerProxy() {
        mlConnectionHandler = new MLConnectionHandlerReal("84.109.116.61", "5000");
    }

    @Override
    public TaggedVideo getSkeletons(Video video) {
        if (mlConnectionHandler != null) {
            return mlConnectionHandler.getSkeletons(video);
        }
        return null;
    }
}
