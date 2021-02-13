package ExternalSystemTests;

import Domain.Streaming.*;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerReal;
import junit.framework.TestCase;
import mainServer.SwimmingErrorDetectors.FactoryDraw;
import mainServer.SwimmingErrorDetectors.IFactoryDraw;
import org.junit.After;
import org.junit.Before;


public class MLConnectionHandlerTest extends TestCase {

    private MLConnectionHandler mlConnectionHandler;
    private IVideo video;

    @Before
    public void setUp() {
        try {
            this.mlConnectionHandler = new MLConnectionHandlerReal("84.109.116.61", "5000");
            IFactoryDraw iFactoryDraw = new FactoryDraw();
            IFactoryVideoHandler iFactoryVideoHandler = new FactoryVideoHandler();
            IFactoryVideo iFactoryVideo = new FactoryVideo(iFactoryDraw,iFactoryVideoHandler);
            this.video = iFactoryVideo.create("./src/test/java/TestingVideos/sample.mov",".mov");
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
