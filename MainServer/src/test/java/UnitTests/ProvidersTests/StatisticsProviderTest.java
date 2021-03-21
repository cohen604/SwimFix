package UnitTests.ProvidersTests;

import DTO.FileDTO;
import junit.framework.TestCase;
import mainServer.Providers.StatisticsProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class StatisticsProviderTest extends TestCase {

    private StatisticsProvider _statisticsProvider;
    private List<String> deleteList;

    @Before
    public void setUp() {
        _statisticsProvider = new StatisticsProvider();
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
            //Act
            String filePath = _statisticsProvider.getStatistics(fileDTO, skeletonsPath, pdfFolder );
            deleteList.add(filePath);
            //Assert
            Assert.assertTrue(Files.exists(Paths.get(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
