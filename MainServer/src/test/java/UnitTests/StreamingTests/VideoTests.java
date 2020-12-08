package UnitTests.StreamingTests;

import DTO.ConvertedVideoDTO;
import Domain.Streaming.Video;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.nio.file.Files;

public class VideoTests extends TestCase {

    final private String VIDEO_FOLDER = "./src/test/java/TestingVideos";
    private ConvertedVideoDTO convertedVideoDTO;


    @Before
    public void setUp() {
        try {
            File file = new File(VIDEO_FOLDER + "/sample.mov");
            byte[] bytes = Files.readAllBytes(file.toPath());
            this.convertedVideoDTO = new ConvertedVideoDTO("sample.mov", bytes);
        } catch (Exception e ){
            System.out.println(e.getMessage());
            fail();
        }
    }

    @After
    public void tearDown() {
    }

    public void testCreateNewVideo() {
        try {
            Video video = new Video(this.convertedVideoDTO);
            //TODO compare bytes
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

    }
}
