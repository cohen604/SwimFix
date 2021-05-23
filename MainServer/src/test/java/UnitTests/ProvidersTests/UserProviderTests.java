package UnitTests.ProvidersTests;

import DTO.UserDTO;
import Domain.UserData.User;
import Storage.Swimmer.SwimmerDao;
import Storage.User.UserDao;
import junit.framework.TestCase;
import mainServer.Providers.UserProvider;
import org.junit.BeforeClass;

public class UserProviderTests extends TestCase {

    private UserDao userDao;
    private SwimmerDao swimmerDao;
    private UserProvider userProvider;
    private UserDTO userDTO;

    @BeforeClass
    public void setUp() {
        userDao = new UserDao();
        swimmerDao = new SwimmerDao();
        userProvider = new UserProvider(userDao, swimmerDao);
        String uid_1 = "1";
        String email_1 = "test@test.com";
        String name_1 = "test";
        userDTO = new UserDTO(uid_1, email_1, name_1);
    }

    public void removeUser(String id) {
        userDao.removeUser(id);
    }

    public void testFirstLoginSuccessful() {
        assertTrue(userProvider.login(userDTO));
        removeUser(userDTO.getUid());
    }

//    public void testLoginSuccessfulWhenUserExist() {
//        userDao.insert(new User(
//                userDTO.getUid(),
//                userDTO.getEmail(),
//                userDTO.getName()));
//        assertTrue(userProvider.login(userDTO));
//        removeUser(userDTO.getUid());
//    }


}
