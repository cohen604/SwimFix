package UnitTests.Storage;

import DTO.ConvertedVideoDTO;
import Domain.Streaming.*;
import Domain.SwimmingData.Drawing.Draw;
import Storage.Video.VideoDao;
import junit.framework.TestCase;
import mainServer.SwimmingErrorDetectors.FactoryDraw;
import mainServer.SwimmingErrorDetectors.IFactoryDraw;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class VideoServiceTest extends TestCase {

    final private String VIDEO_FOLDER = "./src/test/java/TestingVideos";
    private VideoDao videoService;

    private IVideo video;

    @Before
    public void setUp() {
        try {
            IFactoryDraw iFactoryDraw = new FactoryDraw();
            IFactoryVideoHandler iFactoryVideoHandler = new FactoryVideoHandler();
            IFactoryVideo iFactoryVideo = new FactoryVideo(iFactoryDraw, iFactoryVideoHandler);
            this.videoService = new VideoDao(iFactoryVideo);
            File file = new File(VIDEO_FOLDER + "/sample.mov");
            byte[] bytes = Files.readAllBytes(file.toPath());
            ConvertedVideoDTO convertedVideoDTO = new ConvertedVideoDTO("sample.mov", bytes);
            String path = VIDEO_FOLDER + "/test.mov";
            this.video = new Video(new VideoHandler(new Draw()), convertedVideoDTO, path);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void tearDown() {
        //TODO delete the records from the db
    }

    public void testInsert() {
        assertNotNull(this.videoService.insert(this.video));
    }


    public void testGetAll() {
        List<? extends IVideo> videoList = this.videoService.getAll();
        assertNotNull(videoList);
        assertFalse(videoList.isEmpty());
        for (IVideo video:videoList) {
            System.out.println(video);
        }
    }
}
