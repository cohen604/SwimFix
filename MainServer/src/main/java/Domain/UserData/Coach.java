package Domain.UserData;

public class Coach {

    private Team _team;
    private String _tag;

    public Coach() {
        _tag = "Coach";
    }

    public Coach(String tag) {
        _tag = tag;
    }

    public String getTag() {
        return _tag;
    }

}
