package UnitTests.StreamingTests;

import Domain.Streaming.VideoHandler;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class VideoHandlerTest extends TestCase {

    final private String VIDEO_FOLDER = "./src/test/java/TestingVideos";
    private VideoHandler videoHandler;
    private File testVideo;
    private List<File> deleteList;
    private String testVideoType;

    @Before
    public void setUp() {
        this.videoHandler = new VideoHandler(".mov");
        this.testVideo = new File(VIDEO_FOLDER + "/sample.mov");
        this.testVideoType = ".mov";
        if(!testVideo.exists()) {
            fail();
        }
        this.deleteList = new LinkedList<>();

    }

    @After
    public void tearDown() {
        for (File file: deleteList) {
            file.delete();
        }
    }

    public void testSaveVideo() {
        try {
            byte[] bytes = Files.readAllBytes(this.testVideo.toPath());
            String path = VIDEO_FOLDER + "./test.mov";
            assertTrue(this.videoHandler.saveVideo(bytes, path));
            File file = new File(path);
            this.deleteList.add(file);
            assertTrue(file.exists());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
