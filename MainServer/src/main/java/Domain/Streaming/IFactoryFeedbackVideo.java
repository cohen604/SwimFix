package Domain.Streaming;

import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.Errors.Interfaces.SwimmingError;

import java.util.List;
import java.util.Map;

public interface IFactoryFeedbackVideo {

    IFeedbackVideo create(IVideo video,
                          TaggedVideo taggedVideo,
                          Map<Integer, List<SwimmingError>> errorMap,
                          String path,
                          ISwimmingPeriodTime periodTime);

}
