package AcceptanceTests;

import org.junit.After;
import org.junit.Before;

public class UploadVideoForStreamerTest extends AcceptanceTests {

    private String[][] videos = new String[3][];
    private String[][] users = new String[2][];

    @Before
    public void setUp(){
        setUpBridge();
        this.videos[0] = new String[]{"/path/to/video1", "www.video1.stream.com"};
        this.videos[1] = new String[]{"/path/to/video2", "www.video2.stream.com"};
        this.videos[2] = new String[]{"/path/to/video3", "www.video3.stream.com"};

        this.users[0] = new String[]{"foo@bar.com", "foo"};
        this.users[1] = new String[]{"goo@bar.com", "goo"};
    }

    @After
    public void tearDown(){
        tearDownBridge();
    }

    public void testAddNewVideoSuccess(){
        this.bridge.login(Integer.toString(0), this.users[0][0], this.users[0][1]);
        assertTrue(this.bridge.uploadVideoForStreamer(Integer.toString(0), new byte[] {0x3}));
        this.bridge.logout(Integer.toString(0));

    }

    public void testAddNewVideoNotLoggedin(){
        assertFalse(this.bridge.uploadVideoForStreamer(Integer.toString(0), new byte[] {0x3}));
    }

}
