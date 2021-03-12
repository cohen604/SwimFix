package Domain.Streaming;

import Domain.SwimmingData.SwimmingError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class FactoryFeedbackVideo implements IFactoryFeedbackVideo{


    @Override
    public IFeedbackVideo create(IVideo video,
                                 TaggedVideo taggedVideo, Map<Integer, List<SwimmingError>> errorMap,
                                 String path) {
        return new FeedbackVideo(video, taggedVideo, errorMap, path);
    }
}
