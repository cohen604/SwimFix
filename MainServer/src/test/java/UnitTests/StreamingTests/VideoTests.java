package UnitTests.StreamingTests;

import DTO.ConvertedVideoDTO;
import Domain.Streaming.Video;
import Domain.Streaming.VideoHandler;
import Domain.SwimmingData.Draw;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class VideoTests extends TestCase {

    final private String VIDEO_FOLDER = "./src/test/java/TestingVideos";
    private ConvertedVideoDTO convertedVideoDTO;
    private List<File> deleteList;
    private Video video;

    @Before
    public void setUp() {
        try {
            deleteList = new LinkedList<>();
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
        for(File file: deleteList) {
            file.delete();
        }
    }

    public void testCreateNewVideo() {
        try {
            String path = VIDEO_FOLDER + "/test.mov";
            Video video = new Video(new VideoHandler(new Draw()), this.convertedVideoDTO, path);
            File file = new File(video.getPath());
            assertTrue(file.exists());
            deleteList.add(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

    public void testIsVideoExitsVideoPathEmpty() {
        try {
            Video video = new Video(new VideoHandler(new Draw()), this.convertedVideoDTO, "");
            assertFalse(video.isVideoExists());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    public void testIsVideoExitsVideoPathNull() {
        try {
            Video video = new Video(new VideoHandler(new Draw()), this.convertedVideoDTO, null);
            assertFalse(video.isVideoExists());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * The function is a setUp for a video
     */
    private void setUpVideo() {
        String path = VIDEO_FOLDER + "/test.mov";
        this.video = new Video(new VideoHandler(new Draw()), this.convertedVideoDTO, path);
        File file = new File(path);
        deleteList.add(file);
    }

    /**
     * The function is a setUp for a video
     */
    private void setUpVideoNotExits() {
        this.video = new Video(new VideoHandler(new Draw()), this.convertedVideoDTO, "");
    }

    public void testGetHeight() {
        setUpVideo();
        int h = video.getHeight();
        assertEquals(480, h);
    }

    public void testGetHeightVideoNotExits() {
        setUpVideoNotExits();
        int h = video.getHeight();
        assertEquals(-1, h);
    }

    public void testGetWidth() {
        setUpVideo();
        int w = video.getWidth();
        assertEquals(640, w);
    }
    public void testGetWidthVideoNotExits() {
        setUpVideoNotExits();
        int w = video.getWidth();
        assertEquals(-1, w);
    }

    public void testGetPath() {
        setUpVideo();
        String path = video.getPath();
        assertNotNull(path);
        File file = new File(path);
        assertTrue(file.exists());
    }

    public void testGetPathVideoNotExits() {
        setUpVideoNotExits();
        String path = video.getPath();
        assertNull(path);
    }

    public void testGetType() {
        setUpVideo();
        String type = video.getVideoType();
        assertNotNull(type);
        assertEquals(this.convertedVideoDTO.getVideoType(), type);
    }

    public void testGetTypeVideoNotExits() {
        setUpVideoNotExits();
        String type = video.getVideoType();
        assertNull(type);
    }

    public void testGetVideoListBytes() {
        setUpVideo();
        List<byte[]> bytes = video.getVideo();
        assertNotNull(bytes);
    }

    public void testGetVideoListBytesVideoNotExits() {
        setUpVideoNotExits();
        List<byte[]> bytes = video.getVideo();
        assertNull(bytes);
    }
}
