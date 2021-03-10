package Storage.User;

import Domain.UserData.User;
import java.util.List;

//TODO need to think what can we do because mongo need a real instance to work with
public interface IUserDao {

    List<User> getAll();

    User insert(User value);

    User find(String id);

    User update(User value);
}
