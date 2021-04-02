package mainServer.Providers.Interfaces;

import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;

import java.util.List;
import java.util.Map;

public interface IDetectProvider {

    Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons);

    Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons, ISwimmingPeriodTime periodTime);
}
