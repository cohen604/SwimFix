package Storage.User;

import Domain.UserData.User;

import java.util.Collection;
import java.util.List;

public interface IUserDao {

    List<User> getAll();

    User insert(User value);

    User find(String id);

    User update(User value);

    boolean removeUser(String id);

    Collection<User> findUsersThatNotAdmin();

    Collection<User> findUsersThatNotResearcher();

    Long countUsers();

    Long countLoggedUsers();

    Long countSwimmers();

    Long countLoggedSwimmers();

    Long countResearchers();

    Long countLoggedResearchers();

    Long countAdmins();

    Long countLoggedAdmins();

    Long countCoaches();

    Long countLoggedCoaches();

}
