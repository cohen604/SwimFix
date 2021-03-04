package Domain.UserData;

import DTO.UserDTO;
import Domain.UserData.Interfaces.IUser;

public class User implements IUser {

    private String uid;
    private String email;
    private String name;
    private boolean logged;

    private Swimmer _swimmer;
    private Coach _coach;
    private Admin _admin;
    private Researcher _researcher;

    public User(UserDTO userDTO) {
        this.uid = userDTO.getUid();
        this.email = userDTO.getEmail();
        this.name = userDTO.getName();
        this.logged = false;
        _swimmer = new Swimmer();
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

    public boolean login() {
        if(!logged) {
            logged = true;
            return true;
        }
        return false;
    }

    public boolean logout() {
        if(logged) {
            logged = false;
            return true;
        }
        return false;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean isLogged() {
        return logged;
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
