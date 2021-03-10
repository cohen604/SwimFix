package Domain.UserData;

import DTO.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IUser;
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
        _pathManager = new PathManager(email);
    }

    public User(String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.logged = new AtomicBoolean(false);
    }

    public User(String uid, String email, String name, boolean logged) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.logged = new AtomicBoolean(logged);
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
     * todo - add comments
     * @param feedbackVideo
     * @return
     */
    public boolean addFeedback(IFeedbackVideo feedbackVideo) {
        return _swimmer.addFeedback(feedbackVideo);
    }

    /**
     * todo - add comments
     * @param feedbackVideo
     * @return
     */
    public boolean deleteFeedback(IFeedbackVideo feedbackVideo) {
        return _swimmer.deleteFeedback(feedbackVideo);
    }
}
