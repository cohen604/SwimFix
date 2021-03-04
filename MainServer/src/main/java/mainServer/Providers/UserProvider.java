package mainServer.Providers;

import DTO.UserDTO;
import Domain.UserData.Interfaces.IUser;
import Domain.UserData.User;
import Storage.User.UserDao;

import java.util.concurrent.ConcurrentHashMap;

public class UserProvider implements IUserProvider {

    ConcurrentHashMap<String, User> _users;
    UserDao _dao;

    public UserProvider(UserDao dao) {
        _users = new ConcurrentHashMap<>();
        _dao = dao;
    }

    @Override
    public boolean login(UserDTO userDTO) {
        User user = _users.get(userDTO.getUid());
        if(user == null) {
            user = _dao.find(userDTO.getUid());
            if(user == null) {
                user = _dao.insert(new User(userDTO));
                if(user == null) {
                    return false;
                }
            }
            if(_users.putIfAbsent(user.getUid(), user) != null) {
                return false;
            }
        }
        if(user.login()) {
            return _dao.update(user) != null;
        }
        return false;
    }

    @Override
    public IUser getUser(UserDTO userDTO) {
        return _users.get(userDTO.getUid());
    }
}
