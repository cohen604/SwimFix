package Domain.StatisticsData;

import Domain.SwimmingData.ISwimmingSkeleton;

import java.util.List;

public class FactoryStatistic implements IFactoryStatistic {

    @Override
    public IStatistic create(List<ISwimmingSkeleton> raw, List<ISwimmingSkeleton> current) {
        return new StatisticsHolder(raw, current);
    }
}
