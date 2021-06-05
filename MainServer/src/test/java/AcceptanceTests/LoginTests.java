package AcceptanceTests;

import AcceptanceTests.Data.DataBase;
import Storage.User.UserDao;
import org.junit.After;
import org.junit.Before;

public class LoginTests extends AcceptanceTests{

    @Before
    public void setUp(){
        setUpBridge();

    }

    @After
    public void tearDown(){
        tearDownBridge();
        UserDao userDao = new UserDao();
    }

    public void testLoginSuccessful(){
        assertTrue(this.bridge.login(DataBase.Users[0][0], DataBase.Users[0][1], DataBase.Users[0][2]));
        this.bridge.logout(Integer.toString(0));
    }

    public void testLoginFailureAlreadyLoggedin(){
        this.bridge.login(DataBase.Users[0][0], DataBase.Users[0][1], DataBase.Users[0][2]);
        assertFalse(this.bridge.login(DataBase.Users[0][0], DataBase.Users[0][1], DataBase.Users[0][2]));
        this.bridge.logout(DataBase.Users[0][0]);
    }

}
