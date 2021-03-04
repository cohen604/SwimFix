package Domain.Streaming;

import Domain.SwimmingData.SwimmingError;

import java.util.List;
import java.util.Map;

public interface IFactoryFeedbackVideo {

    IFeedbackVideo create(IVideo video, TaggedVideo taggedVideo,
                          Map<Integer, List<SwimmingError>> errorMap);


    IFeedbackVideo create(IVideo video, TaggedVideo taggedVideo,
                          Map<Integer, List<SwimmingError>> errorMap, String folderPath);

}
