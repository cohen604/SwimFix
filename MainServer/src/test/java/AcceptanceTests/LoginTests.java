package AcceptanceTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class LoginTests extends AcceptanceTests{

    private String[][] users = new String[2][];

    @Before
    public void setUp(){
        setUpBridge();
        this.users[0] = new String[]{"foo@bar.com", "foo"};
        this.users[1] = new String[]{"goo@bar.com", "goo"};
    }

    @After
    public void tearDown(){
        tearDownBridge();
    }

    public void testLoginSuccessful(){
        assertTrue(this.bridge.login(Integer.toString(0), this.users[0][0], this.users[0][1]));
        this.bridge.logout(Integer.toString(0));
    }

    public void testLoginFailureAlreadyLoggedin(){
        this.bridge.login(Integer.toString(0), this.users[0][0], this.users[0][1]);
        assertFalse(this.bridge.login(Integer.toString(0), this.users[0][0], this.users[0][1]));
        this.bridge.logout(Integer.toString(0));
    }

}
