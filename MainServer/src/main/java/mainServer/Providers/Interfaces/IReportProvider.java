package mainServer.Providers.Interfaces;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.StatisticsData.IStatistic;
import Domain.SwimmingData.ISwimmingSkeleton;

import java.util.List;

public interface IReportProvider {

    String generateReport(List<ISwimmingSkeleton> raw,
                          List<ISwimmingSkeleton> model,
                          List<ISwimmingSkeleton> modelAndInterpolation,
                          String pdfFolderPath,
                          IStatistic statistic,
                          ISwimmingPeriodTime periodTime);
}
