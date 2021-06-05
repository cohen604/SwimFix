package AcceptanceTests;

import AcceptanceTests.Bridge.AcceptanceTestsBridge;
import AcceptanceTests.Bridge.AcceptanceTestsProxy;
import AcceptanceTests.Data.DataBase;
import DTO.UserDTOs.UserDTO;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.PeriodTimeData.IPeriodTime;
import Domain.PeriodTimeData.PeriodTime;
import Domain.PeriodTimeData.SwimmingPeriodTime;
import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import Domain.UserData.User;
import Storage.Feedbacks.FeedbacksDao;
import Storage.User.UserDao;
import com.mongodb.MongoClientSettings;
import com.mongodb.Tag;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class AcceptanceTests extends TestCase {

    protected AcceptanceTestsBridge bridge;
    private UserDao userDao;
    private FeedbacksDao feedbacksDao;

    public AcceptanceTests() {
        //this.bridge = new AcceptanceTestsProxy();
        userDao = new UserDao();
        feedbacksDao = new FeedbacksDao();
    }

    public void setUpBridge() {
        this.bridge = new AcceptanceTestsProxy();
        this.populateDb();
    }

    public void tearDownBridge() {
        clearDb();
    }

    private void populateDb(){
        addUsers();
        addVideos();
    }

    private void addUsers(){
        for(String[] user: DataBase.Users){
            User u = new User(new UserDTO(user[0], user[1], user[2]));
            userDao.insert(u);
        }
    }

    private void addVideos(){
        Video v = new Video("/foo/bar/video", "mp4");
        TaggedVideo t = new TaggedVideo("/path/to/ml/skeleton", "/path/to/skeleton");
        HashMap<Integer, List<SwimmingError>> m = new HashMap<>();
        SwimmingPeriodTime p = new SwimmingPeriodTime(new LinkedList<>(), new LinkedList<>());
        FeedbackVideo feedbackVideo = new FeedbackVideo(v, t, m, "/path/to/feedback", p, new LinkedList<>());
        feedbacksDao.insert(feedbackVideo);
    }

    private void clearDb(){
        removeUsers();
    }

    private void removeUsers(){
        for(String[] user: DataBase.Users){
            userDao.removeUser(user[0]);
        }

    }
}
