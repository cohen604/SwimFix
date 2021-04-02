package mainServer.Providers.Interfaces;

import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Errors.Interfaces.SwimmingError;

import java.util.List;
import java.util.Map;

public interface IDetectProvider {

    Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons);

    Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons, ISwimmingPeriodTime periodTime);
}
