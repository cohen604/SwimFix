package UnitTests.ProvidersTests;

import DTO.FileDTO;
import junit.framework.TestCase;
import mainServer.Providers.StatisticsProvider;
import org.junit.Before;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StatisticsProviderTest extends TestCase {

    private StatisticsProvider _statisticsProvider;

    @Before
    public void setUp() {
        _statisticsProvider = new StatisticsProvider();
    }

    public void testGetStatistics() {
        try {
            //Arrange
            String pdfFolder = "./src/test/java/TestingVideos";
            String rawPath = "./src/test/java/TestingVideos/test@gmail.com/mlSkeletons/2021-03-12-17-36-11.csv";
            String skeletonsPath = "./src/test/java/TestingVideos/test@gmail.com/feedbacksSkeletons/2021-03-12-17-36-11.csv";
            byte[] bytes = Files.readAllBytes(Paths.get(rawPath));
            FileDTO fileDTO = new FileDTO(skeletonsPath, bytes);
            //Act
            _statisticsProvider.getStatistics(fileDTO, skeletonsPath, pdfFolder );
            //Assert
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
