package mainServer.Providers.Interfaces;

import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;

public interface IPeriodTimeProvider {

    ISwimmingPeriodTime analyzeTimes(List<ISwimmingSkeleton> skeletons);

    List<ISwimmingSkeleton> correctSkeletons(List<ISwimmingSkeleton> skeletons,
                                             ISwimmingPeriodTime periodTime);

}
