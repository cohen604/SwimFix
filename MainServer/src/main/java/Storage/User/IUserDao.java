package Storage.User;

import Domain.UserData.User;
import java.util.List;

public interface IUserDao {

    List<User> getAll();

    User insert(User value);

    User find(String id);

    User update(User value);
}
