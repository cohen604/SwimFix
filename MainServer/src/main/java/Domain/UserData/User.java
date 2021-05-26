package Domain.UserData;

import DTO.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IUser;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
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

    private final Object _adminLock;
    private final Object _researcherLock;
    /***
     * Note: Only when register using this constructor
     * @param userDTO - user dto
     */
    public User(UserDTO userDTO) {
        this.uid = userDTO.getUid();
        this.email = userDTO.getEmail();
        this.name = userDTO.getName();
        this.logged = new AtomicBoolean(false);
        _swimmer = new Swimmer(email);
//        _coach = new Coach();
//        _researcher = new Researcher();
//        _admin = new Admin();
        _adminLock = new Object();
        _researcherLock = new Object();
        _pathManager = new PathManager(email, true);
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
        _adminLock = new Object();
        _researcherLock = new Object();
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

    @Override
    public Collection<LocalDateTime> getFeedbacksDays() {
        return _swimmer.getFeedbacksDays();
    }

    @Override
    public Collection<IFeedbackVideo> getFeedbacksOfDay(LocalDateTime day) {
        return _swimmer.getFeedbacksOfDay(day);
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
    public IFeedbackVideo deleteFeedback(String feedbackPath) {
        return _swimmer.deleteFeedback(feedbackPath);
    }

    @Override
    public boolean addAdmin() {
        synchronized (_adminLock) {
            if(_admin == null) {
                _admin = new Admin(email);
                return true;
            }
            return false;
        }
    }

    @Override
    public void deleteAdmin() {
        synchronized (_adminLock) {
            _admin = null;
        }
    }

    @Override
    public boolean addResearcher() {
        synchronized (_researcherLock) {
            if(_researcher == null) {
                _researcher = new Researcher(email);
                return true;
            }
            return false;
        }
    }

    @Override
    public void deleteResearcher() {
        synchronized (_researcherLock) {
            _researcher = null;
        }
    }
}
