package mainServer.Providers;
import DTO.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IUser;
import Domain.UserData.User;
import Storage.Swimmer.ISwimmerDao;
import Storage.User.IUserDao;
import mainServer.Providers.Interfaces.IUserProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserProvider implements IUserProvider {

    /**
     * The hash map key: user uid, the values is the user
     */
    private ConcurrentHashMap<String, User> _users;
    private IUserDao _dao;
    private ISwimmerDao _swimmerdao;

    public UserProvider(IUserDao dao, ISwimmerDao swimmerDao) {
        _users = new ConcurrentHashMap<>();
        _dao = dao;
        _swimmerdao = swimmerDao;
    }

    @Override
    public void addSwimAnalyticsUser(UserDTO userDTO) {
        IUser user = getUser(userDTO);
        if(!user.isAdmin()) {
            user.addAdmin();
        }
        if(!user.isResearcher()) {
            user.addResearcher();
        }
        _dao.update(_users.get(user.getUid()));
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
        // TODO refactor this to FeedbackProvider to insert the feedback
        User current = _users.get(user.getUid());
        if (current != null
                && current.isLogged()
                && current.addFeedback(feedbackVideo)) {
            if (_swimmerdao.update(current.getSwimmer()) == null) {
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
        // TODO refactor this to FeedbackProvider to delete the feedback
        User current = _users.get(user.getUid());
        if (current != null && current.isLogged()) {
            IFeedbackVideo video = current.deleteFeedback(feedbackPath);
            if(video != null) {
                if (_swimmerdao.update(current.getSwimmer()) == null) {
                    current.addFeedback(video);
                }
                else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Collection<? extends IUser> findUsersThatNotAdmin(IUser user) {
        if(user.isLogged() && user.isAdmin()) {
            return _dao.findUsersThatNotAdmin();
        }
        return null;
    }

    @Override
    public Collection<? extends IUser> findUsersThatNotResearcher(IUser user) {
        if(user.isLogged() && user.isAdmin()) {
            return _dao.findUsersThatNotResearcher();
        }
        return null;
    }

    @Override
    public boolean addAdmin(IUser admin, IUser userToAdd) {
        User user = _users.get(userToAdd.getUid());
        if(admin.isLogged()
                && admin.isAdmin()
                && user !=null) {
            if(user.addAdmin()) {
                if(_dao.update(user) != null) {
                    return true;
                }
                else  {
                    user.deleteAdmin();
                }
            }
        }
        return false;
    }

    @Override
    public boolean addResearcher(IUser admin, IUser userToAdd) {
        User user = _users.get(userToAdd.getUid());
        if(admin.isLogged()
                && admin.isAdmin()
                && user !=null) {
            if(user.addResearcher()) {
                if(_dao.update(user) != null) {
                    return true;
                }
                else  {
                    user.deleteResearcher();
                }
            }
        }
        return false;
    }


}
