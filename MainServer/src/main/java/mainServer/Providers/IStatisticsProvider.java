package mainServer.Providers;

import DTO.FileDTO;

public interface IStatisticsProvider {

    String getStatistics(FileDTO rawSkeletonFileDTO,
                         String skeletonsPath,
                         String pdfFolderPath);
}
