package Domain.UserData;

public class Coach {

    private String _email;
    private Team _team;

    public Coach(String _email, Team _team) {
        this._email = _email;
        this._team = _team;
    }

    public String getEmail() {
        return _email;
    }

    public Team getTeam() {
        return _team;
    }
}
