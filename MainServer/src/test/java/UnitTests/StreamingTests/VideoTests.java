package UnitTests.StreamingTests;

import DTO.ConvertedVideoDTO;
import Domain.Streaming.Video;
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
            Video video = new Video(this.convertedVideoDTO, path);
            File file = new File(path);
            assertTrue(file.exists());
            deleteList.add(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

    }

}
