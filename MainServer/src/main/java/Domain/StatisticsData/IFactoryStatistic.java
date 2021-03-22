package Domain.StatisticsData;

import Domain.SwimmingData.ISwimmingSkeleton;

import java.util.List;

public interface IFactoryStatistic {

    IStatistic create(List<ISwimmingSkeleton> raw, List<ISwimmingSkeleton> current);
}
