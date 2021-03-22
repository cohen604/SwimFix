package mainServer.Providers.Interfaces;
import Domain.StatisticsData.IStatistic;
import Domain.SwimmingData.ISwimmingSkeleton;

import java.util.List;

public interface IReportProvider {

    String generateReport(List<ISwimmingSkeleton> raw,
                          List<ISwimmingSkeleton> current,
                          String pdfFolderPath,
                          IStatistic statistic);
}
