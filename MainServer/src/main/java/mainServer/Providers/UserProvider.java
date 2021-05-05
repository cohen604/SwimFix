package mainServer.Providers;

import DTO.FeedbackVideoStreamer;
import DTO.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IUser;
import Domain.UserData.User;
import Storage.User.IUserDao;
import Storage.User.UserDao;
import mainServer.Providers.Interfaces.IUserProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserProvider implements IUserProvider {

    /**
     * The hash map key: user uid, the values is the user
     */
    ConcurrentHashMap<String, User> _users;
    IUserDao _dao;

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
    public boolean logout(UserDTO userDTO) {
        User user = _users.get(userDTO.getUid());
        if(user != null) {
            if(user.logout()){
                return _dao.update(user) != null;
            }
        }
        return false;
    }

    @Override
    public IUser getUser(UserDTO userDTO) {
        User user = _users.get(userDTO.getUid());
        if(user == null) {
            user = _dao.find(userDTO.getUid());
            if(user != null) {
                _users.putIfAbsent(user.getUid(), user);
            }
        }
        return user;
    }

    @Override
    public boolean addFeedbackToUser(IUser user, IFeedbackVideo feedbackVideo) {
        User current = _users.get(user.getUid());
        if (current != null
                && current.isLogged()
                && current.addFeedback(feedbackVideo)) {
            if (_dao.update(current) == null) {
                current.deleteFeedback(feedbackVideo.getPath());
            }
            else {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean reload() {
        _users = new ConcurrentHashMap<>();
        List<User> users = _dao.getAll();
        if(users == null) {
            return false;
        }
        for (User user: users) {
            user.logout();
            _dao.update(user);
            // no need to hold them in the cache only need to logout them and update
            //_users.put(user.getUid(), user);
        }
        return true;
    }

    /***
     * delete a feedback of a user
     * @param user - the user who own the feedback
     * @param feedbackPath - the path of the feedback to delete
     * @return - true if deleted, false if not
     */
    @Override
    public boolean deleteFeedbackByID(IUser user, String feedbackPath) {
        User current = _users.get(user.getUid());
        if (current != null
                && current.isLogged()) {
            IFeedbackVideo video = current.deleteFeedback(feedbackPath);
            if(video != null) {
                if (_dao.update(current) == null) {
                    current.addFeedback(video);
                }
                else {
                    return true;
                }
            }
        }
        return false;
    }


}
