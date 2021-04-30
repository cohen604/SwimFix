package Domain.UserData;

import DTO.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IUser;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public class User implements IUser {

    private String uid;
    private String email;
    private String name;
    private AtomicBoolean logged;

    private PathManager _pathManager;
    private Swimmer _swimmer;
    private Coach _coach;
    private Admin _admin;
    private Researcher _researcher;

    /***
     * Note: Only when register using this constructor
     * @param userDTO - user dto
     */
    public User(UserDTO userDTO) {
        this.uid = userDTO.getUid();
        this.email = userDTO.getEmail();
        this.name = userDTO.getName();
        this.logged = new AtomicBoolean(false);
        _swimmer = new Swimmer();
        _coach = new Coach();
        _researcher = new Researcher();
        _admin = new Admin();   
        _pathManager = new PathManager(email, true);
    }

    public User(String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.logged = new AtomicBoolean(false);
        _pathManager = new PathManager(email, false);
    }

    public User(String uid, String email, String name, boolean logged) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.logged = new AtomicBoolean(logged);
        _pathManager = new PathManager(email, false);
    }

    public User(String uid, String email, String name, boolean logged,
                Swimmer swimmer, Coach coach, Admin admin, Researcher researcher) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.logged = new AtomicBoolean(logged);
        this._swimmer = swimmer;
        this._coach = coach;
        this._admin = admin;
        this._researcher = researcher;
        _pathManager = new PathManager(email, false);
    }

    @Override
    public boolean login() {
        return logged.compareAndSet(false, true);
    }

    @Override
    public boolean logout() {
        return logged.compareAndSet(true, false);
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isLogged() {
        return logged.get();
    }

    @Override
    public boolean isSwimmer() {
        return _swimmer != null;
    }

    @Override
    public boolean isResearcher() {
        return _researcher != null;
    }

    @Override
    public boolean isCoach() {
        return _coach != null;
    }

    @Override
    public boolean isAdmin() {
        return _admin != null;
    }

    @Override
    public String getVideosPath() {
        return _pathManager.getVideosPath();
    }

    @Override
    public String getFeedbacksPath() {
        return _pathManager.getFeedbacksPath();
    }

    @Override
    public String getSkeletonsPath() {
        return _pathManager.getSkeletonsPath();
    }

    @Override
    public String getMLSkeletonsPath() {
        return _pathManager.getMLSkeletonsPath();
    }

    @Override
    public String getReportsPath() {
        return _pathManager.getReportsPath();
    }

    @Override
    public String getDownloadsPath() {
        return _pathManager.getDownloadsPath();
    }

    @Override
    public Collection<IFeedbackVideo> getFeedbacks() {
        return _swimmer.getFeedbacks();
    }

    public Swimmer getSwimmer() {
        return _swimmer;
    }

    public Coach getCoach() {
        return _coach;
    }

    public Admin getAdmin() {
        return _admin;
    }

    public Researcher getResearcher() {
        return _researcher;
    }

    /**
     * add a feedback to user
     * @param feedbackVideo - the feedback to add
     * @return - true if added, false if not
     */
    @Override
    public boolean addFeedback(IFeedbackVideo feedbackVideo) {
        return _swimmer.addFeedback(feedbackVideo);
    }

    /**
     * delete a feedback from a user
     * @param feedbackPath- the path of the feedback to delete
     * @return - true if deleted, false if not
     */
    @Override
    public boolean deleteFeedback(String feedbackPath) {
        return _swimmer.deleteFeedback(feedbackPath);
    }
}
