package Domain.Streaming;

import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.Errors.Interfaces.SwimmingError;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FactoryFeedbackVideo implements IFactoryFeedbackVideo{

    @Override
    public IFeedbackVideo create(IVideo video,
                                 TaggedVideo taggedVideo,
                                 Map<Integer, List<SwimmingError>> errorMap,
                                 String path,
                                 ISwimmingPeriodTime periodTime) {
        return new FeedbackVideo(video, taggedVideo, errorMap, path, periodTime, new LinkedList<>());
    }
}
