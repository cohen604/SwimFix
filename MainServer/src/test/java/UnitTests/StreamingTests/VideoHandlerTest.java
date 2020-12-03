package UnitTests.StreamingTests;

import Domain.Streaming.VideoHandler;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
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

    @Test
    public void testSaveVideo() {
        try {
            byte[] bytes = Files.readAllBytes(this.testVideo.toPath());
            String path = VIDEO_FOLDER + "./test.mov";
            //TODO add test with null path and wrong path
            //TODO add test with null bytes
            assertTrue(this.videoHandler.saveVideo(bytes, path));
            File file = new File(path);
            this.deleteList.add(file);
            assertTrue(file.exists());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void testDeleteVideo() {
        try {
            String path = VIDEO_FOLDER + "./test.mov";
            FileOutputStream out = new FileOutputStream(path);
            out.write(new byte[1]);
            out.close();
            //TODO add test with null path and wrong path
            assertTrue(this.videoHandler.deleteVideo(path));
            File file = new File(path);
            assertFalse(file.exists());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    @Test
    public void testReadBytes() {
        try {
            byte[] bytes = new byte[3];
            bytes[0] = 98;
            bytes[1] = 99;
            String path = VIDEO_FOLDER + "./test.mov";
            FileOutputStream out = new FileOutputStream(path);
            out.write(bytes);
            out.close();
            this.deleteList.add(new File(path));
            // Test started
            //TODO add test with path null and wrong path not exists
            byte[] result = this.videoHandler.readVideo(path);
            assertEquals(bytes.length, result.length);
            for(int i=0; i< bytes.length; i++) {
                assertEquals(bytes[i], result[i]);
            }
        } catch (Exception e ){
            System.out.println(e.getMessage());
            fail();
        }
    }
}
