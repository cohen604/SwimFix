package UnitTests.StreamingTests;

import Domain.Streaming.VideoHandler;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

public class VideoHandlerTest extends TestCase {

    private VideoHandler videoHandler;
    private File testVideo;
    private String testVideoType;
    final private String VIDEO_FOLDER = "./src/test/java/TestingVideos";

    @Before
    public void setUp() {
        this.testVideo = new File(VIDEO_FOLDER + "/sample.mov");
        this.testVideoType = ".mov";
        if(!testVideo.exists()) {
            fail();
        }
        this.videoHandler = new VideoHandler(".mov");
    }

    @After
    public void tearDown() {

    }

    public void testSaveVideo() {
        try {
            byte[] bytes = Files.readAllBytes(this.testVideo.toPath());
            String path = VIDEO_FOLDER + "./test.mov";
            assertTrue(this.videoHandler.saveVideo(bytes, path));
            File file = new File(path);
            assertTrue(file.exists());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
