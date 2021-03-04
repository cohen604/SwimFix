package Domain.UserData;

import DTO.UserDTO;
import Domain.UserData.Interfaces.IUser;

import java.nio.file.Files;
import java.nio.file.Paths;

public class User implements IUser {

    private String uid;
    private String email;
    private String name;
    private boolean logged;

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
        this.logged = false;
        _swimmer = new Swimmer();
        _pathManager = new PathManager(email);
    }

    public User(String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.logged = false;
    }

    public User(String uid, String email, String name, boolean logged) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.logged = logged;
    }

    public User(String uid, String email, String name, boolean logged,
                Swimmer swimmer, Coach coach, Admin admin, Researcher researcher) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.logged = logged;
        this._swimmer = swimmer;
        this._coach = coach;
        this._admin = admin;
        this._researcher = researcher;
    }

    @Override
    public boolean login() {
        if(!logged) {
            logged = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean logout() {
        if(logged) {
            logged = false;
            return true;
        }
        return false;
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
        return logged;
    }

    @Override
    public String getVideosPath() {
        return _pathManager.getVideosPath();
    }

    @Override
    public String getFeedbacksPath() {
        return _pathManager.getFeedbacksPath();
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

}
