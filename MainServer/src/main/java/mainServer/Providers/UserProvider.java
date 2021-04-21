package mainServer.Providers;

import DTO.FeedbackVideoStreamer;
import DTO.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IUser;
import Domain.UserData.User;
import Storage.User.IUserDao;
import Storage.User.UserDao;
import mainServer.Providers.Interfaces.IUserProvider;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        if (current != null && current.isLogged()) {
            current.addFeedback(feedbackVideo);
            if (_dao.update(current) == null) {
                current.deleteFeedback(feedbackVideo);
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


    /**
     * filter list of FeedbackVideoStreamer to a map.
     * Map is type of <String: date of swim, Map<String: hour of swim, FeedbackVideoStreamer>>
     * Example: path: TestingVideos/example/2021-03-31-19-39-18.mov
     *          date: 2021-03-31
     *          hour: 19
     * @param history - list of FeedbackVideoStreamer
     * @return - map of FeedbackVideoStreamer by date and hour
     */
    @Override
    public List<String> filterHistoryByDay
            (List<FeedbackVideoStreamer> history ) {
        List<String> days = new LinkedList<>();
        for (FeedbackVideoStreamer feedbackVideoStreamer : history) {
            String path = feedbackVideoStreamer.getPath();
            int index_of_dot = path.lastIndexOf('.');
            String date = path.substring(index_of_dot - 19, index_of_dot - 9);
            days.add(date);
        }
        return days;
    }


    /**
     * filter list of FeedbackVideoStreamer to a map.
     * Map is type of <String: date of swim, Map<String: hour of swim, FeedbackVideoStreamer>>
     * Example: path: TestingVideos/example/2021-03-31-19-39-18.mov
     *          date: 2021-03-31
     *          hour: 19
     * @param history - list of FeedbackVideoStreamer
     * @return - map of FeedbackVideoStreamer by date and hour
     */
    @Override
    public Map <String, FeedbackVideoStreamer> filterHistoryByPool
    (List<FeedbackVideoStreamer> history, String day) {
        Map <String, FeedbackVideoStreamer> hour_map = new HashMap<>();
        for (FeedbackVideoStreamer feedbackVideoStreamer : history) {
            String path = feedbackVideoStreamer.getPath();
            int index_of_dot = path.lastIndexOf('.');
            String date = path.substring(index_of_dot - 19, index_of_dot - 9);
            if (date.equals(day)) {
                String hour = path.substring(index_of_dot - 8, index_of_dot - 6);
                hour_map.put(hour, feedbackVideoStreamer);
            }
        }
        return hour_map;
    }

}
