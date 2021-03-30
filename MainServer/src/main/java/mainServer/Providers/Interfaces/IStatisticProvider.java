package mainServer.Providers.Interfaces;

import Domain.StatisticsData.IStatistic;
import Domain.SwimmingData.ISwimmingSkeleton;

import java.util.List;

public interface IStatisticProvider {

    IStatistic analyze(List<ISwimmingSkeleton> raw,
                       List<ISwimmingSkeleton> model,
                       List<ISwimmingSkeleton> modelAndInterpolation);
}
