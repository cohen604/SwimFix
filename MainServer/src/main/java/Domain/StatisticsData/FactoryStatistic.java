package Domain.StatisticsData;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;

public class FactoryStatistic implements IFactoryStatistic {

    @Override
    public IStatistic create(List<ISwimmingSkeleton> raw,
                             List<ISwimmingSkeleton> model,
                             List<ISwimmingSkeleton> modelAndInterpolation) {
        return new StatisticsHolder(raw, model, modelAndInterpolation);
    }
}
