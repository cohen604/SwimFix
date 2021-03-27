package UnitTests.ProvidersTests;

import DTO.FileDTO;
import Domain.StatisticsData.IStatistic;
import Domain.StatisticsData.StatisticsHolder;
import DomainLogic.FileLoaders.ISkeletonsLoader;
import DomainLogic.FileLoaders.SkeletonsLoader;
import DomainLogic.PdfDrawing.IGraphDrawer;
import DomainLogic.PdfDrawing.PdfDrawer;
import junit.framework.TestCase;
import mainServer.Providers.ReportProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class StatisticsProviderTest extends TestCase {

    private ReportProvider _statisticsProvider;
    private List<String> deleteList;
    @Before
    public void setUp() {
        IGraphDrawer graphDrawer = new PdfDrawer();
        _statisticsProvider = new ReportProvider(graphDrawer);
        deleteList = new LinkedList<>();
    }

    @After
    public void tearDown() {
        for(String path: deleteList) {
            try {
                Files.delete(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void testGetStatistics() {
        try {
            //Arrange
            String pdfFolder = "./src/test/java/TestingVideos";
            String rawPath = "./src/test/java/TestingVideos/test@gmail.com/mlSkeletons/2021-03-12-17-36-11.csv";
            String skeletonsPath = "./src/test/java/TestingVideos/test@gmail.com/feedbacksSkeletons/2021-03-12-17-36-11.csv";
            byte[] bytes = Files.readAllBytes(Paths.get(rawPath));
            FileDTO fileDTO = new FileDTO(skeletonsPath, bytes);
            IStatistic statistic = new StatisticsHolder(new LinkedList<>(), new LinkedList<>()); //TODO change this
            //Act
            //String filePath = _statisticsProvider.generateReport(, skeletonsPath, pdfFolder, statistic);
            //deleteList.add(filePath);
            //Assert
            //Assert.assertTrue(Files.exists(Paths.get(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}