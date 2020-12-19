package AcceptanceTests;

import DTO.ActionResult;
import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoStreamer;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class UploadVideoForStreamerTest extends AcceptanceTests {

    final private String VIDEO_FOLDER = "./src/test/java/TestingVideos";
    private ConvertedVideoDTO convertedVideoDTO;
    private List<File> deleteList;

    @Before
    public void setUp() {
        try {
            setUpBridge();
            deleteList = new LinkedList<>();
            File file = new File(VIDEO_FOLDER + "/sample.mov");
            byte[] bytes = Files.readAllBytes(file.toPath());
            this.convertedVideoDTO = new ConvertedVideoDTO("/test1.mov", bytes);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void tearDown() {
        tearDownBridge();
    }

    public void testUploadVideoForStreamerSuccess() {
        ActionResult<FeedbackVideoStreamer> result = this.bridge.uploadVideoForStreamer(this.convertedVideoDTO);
        assertNotNull(result);
        FeedbackVideoStreamer feedbackVideoStreamer = result.getValue();
        assertNotNull(feedbackVideoStreamer);
    }

}
