package Domain.StatisticsData;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;

public interface IFactoryStatistic {

    IStatistic create(List<ISwimmingSkeleton> raw,
                      List<ISwimmingSkeleton> model,
                      List<ISwimmingSkeleton> modelAndInterpolation);
}
