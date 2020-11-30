package ExernalSystems;

import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;

public class MLConnectionHandlerProxy implements MLConnectionHandler{
    private MLConnectionHandler mlConnectionHandler = null;

    public MLConnectionHandlerProxy() {
        mlConnectionHandler = new MLConnectionHandlerReal("84.109.116.61", "5000");
    }

    @Override
    public TaggedVideo tagVideo(Video video) {
        if (mlConnectionHandler != null)
            return mlConnectionHandler.tagVideo(video);
        return null;
    }
}
