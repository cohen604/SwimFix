package ExternalSystemTests;

import Domain.Streaming.*;
import Domain.Drawing.Draw;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerReal;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.opencv.core.Mat;

import java.util.List;


public class MLConnectionHandlerTest extends TestCase {

    private MLConnectionHandler mlConnectionHandler;
    private IVideoHandler videoHandler;
    private IVideo video;

    @Before
    public void setUp() {
        try {
            //TODO mock the ivideoHandler
            //TODO mock the ivideo
            this.videoHandler = new VideoHandler(new Draw());
            this.mlConnectionHandler = new MLConnectionHandlerReal("84.109.116.61", "5000");
            IFactoryVideo iFactoryVideo = new FactoryVideo();
            this.video = iFactoryVideo.create("./src/test/java/TestingVideos/sample.mov",".mov");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @After
    public void tearDown() {
    }

    public void testGetSkeletons() {
        List<Mat> frames = this.videoHandler.getFrames(this.video.getPath());
        int size = frames.size();
        int height = frames.get(0).height();
        int width = frames.get(0).width();
        try {
            List<ISwimmingSkeleton> swimmingSkeletons = this.mlConnectionHandler.getSkeletons(this.video, size, height, width);
            if (swimmingSkeletons == null) {
                fail();
            }
        }
        catch (Exception e) {
            fail();
        }
        //TODO
    }
}
