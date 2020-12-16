package UnitTests.Storage;

import DTO.ConvertedVideoDTO;
import Domain.Streaming.Video;
import Storage.VideoDao;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class VideoServiceTest extends TestCase {

    final private String VIDEO_FOLDER = "./src/test/java/TestingVideos";
    private VideoDao videoService;

    private Video video;

    @Before
    public void setUp() {
        try {
            this.videoService = new VideoDao();
            File file = new File(VIDEO_FOLDER + "/sample.mov");
            byte[] bytes = Files.readAllBytes(file.toPath());
            ConvertedVideoDTO convertedVideoDTO = new ConvertedVideoDTO("sample.mov", bytes);
            String path = VIDEO_FOLDER + "/test.mov";
            this.video = new Video(convertedVideoDTO, path);
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
        List<Video> videoList = this.videoService.getAll();
        assertNotNull(videoList);
        assertFalse(videoList.isEmpty());
        for (Video video:videoList) {
            System.out.println(video);
        }
    }
}
