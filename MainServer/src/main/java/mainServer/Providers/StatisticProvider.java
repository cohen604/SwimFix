package mainServer.Providers;

import Domain.StatisticsData.IFactoryStatistic;
import Domain.StatisticsData.IStatistic;
import Domain.SwimmingData.ISwimmingSkeleton;
import mainServer.Providers.Interfaces.IStatisticProvider;

import java.util.List;

public class StatisticProvider implements IStatisticProvider {

    public IFactoryStatistic _factoryStatistic;

    public StatisticProvider(IFactoryStatistic factoryStatistic) {
        _factoryStatistic = factoryStatistic;
    }

    @Override
    public IStatistic analyze(List<ISwimmingSkeleton> raw,
                              List<ISwimmingSkeleton> model,
                              List<ISwimmingSkeleton> modelAndInterpolation) {
        return _factoryStatistic.create(raw, model, modelAndInterpolation);
    }

}
