package DomainLogic.SwimmingErrorDetectors;

import Domain.Errors.Interfaces.SwimmingError;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;
import java.util.Map;

public interface ISwimmingTimeErrorDetector {

    Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons, ISwimmingPeriodTime periodTime);
}
