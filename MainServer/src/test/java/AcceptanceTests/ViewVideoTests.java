package AcceptanceTests;

import org.junit.After;
import org.junit.Before;

public class ViewVideoTests extends  AcceptanceTests{

    private String[][] videos = new String[3][];
    private String[][] users = new String[2][];

    @Before
    public void setUp(){
        setUpBridge();
        this.videos[0] = new String[]{"/path/to/feedback", "www.video1.stream.com"};
        this.videos[1] = new String[]{"/path/to/video2", "www.video2.stream.com"};
        this.videos[2] = new String[]{"/path/to/video3", "www.video3.stream.com"};

        this.users[0] = new String[]{"foo@bar.com", "foo"};
        this.users[1] = new String[]{"goo@bar.com", "goo"};
    }

    @After
    public void tearDown(){
        tearDownBridge();
    }

    public void testGetCorrectStream(){
        String[] video = this.videos[0];
        this.bridge.login(Integer.toString(123231), this.users[0][0], this.users[0][1]);
        this.bridge.uploadVideoForStreamer("123231", new byte[]{0x3});
        assertTrue(this.bridge.streamFile(Integer.toString(123231), video[0]));
        this.bridge.logout(Integer.toString(123231));
    }

    public void testGetIncorrectSteam(){
        String[] video = this.videos[1];
        this.bridge.login(Integer.toString(0), this.users[0][0], this.users[0][1]);
        assertFalse(this.bridge.streamFile(Integer.toString(0), video[0]));
        this.bridge.logout(Integer.toString(0));
    }

    public void testNonExistingVideo(){
        String[] video = this.videos[2];
        this.bridge.login(Integer.toString(0), this.users[0][0], this.users[0][1]);
        assertNull(this.bridge.streamFile(Integer.toString(0), video[0]));
        this.bridge.logout(Integer.toString(0));
    }

    public void testGetStreamNotLoggedIn(){
        String[] video = this.videos[0];
        assertNull(this.bridge.streamFile(Integer.toString(0), video[0]));

    }
}
