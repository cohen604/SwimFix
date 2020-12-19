package ExternalSystemTests;

import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerReal;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class MLConnectionHandlerTest extends TestCase {

    private MLConnectionHandler mlConnectionHandler;
    private Video video;

    @Before
    public void setUp() {
        try {
            this.mlConnectionHandler = new MLConnectionHandlerReal("84.109.116.61", "5000");
            this.video = new Video("./src/test/java/TestingVideos/sample.mov",".mov");
            if(this.video.getVideo() == null) {
                fail();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @After
    public void tearDown() {
    }

    public void testGetSkeletons() {
        TaggedVideo taggedVideo = this.mlConnectionHandler.getSkeletons(this.video);
        if(taggedVideo == null) {
            fail();
        }
        //TODO
    }
}
