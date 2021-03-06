package Domain.Streaming;

import Domain.SwimmingData.SwimmingError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class FactoryFeedbackVideo implements IFactoryFeedbackVideo{

    //TODO delete this
    @Override
    public IFeedbackVideo create(IVideo video, TaggedVideo taggedVideo, Map<Integer, List<SwimmingError>> errorMap) {
        return new FeedbackVideo(video, taggedVideo, errorMap);
    }

    @Override
    public IFeedbackVideo create(IVideo video,
                                 TaggedVideo taggedVideo, Map<Integer,
                                 List<SwimmingError>> errorMap,
                                 String folderName,
                                 LocalDateTime time) {
        return new FeedbackVideo(video, taggedVideo, errorMap, folderName, time);
    }
}
