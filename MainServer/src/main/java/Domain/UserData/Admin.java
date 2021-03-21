package Domain.UserData;

public class Admin {

    private String _tag ;

    public Admin() {
        _tag  = "Admin";
    }

    public Admin(String tag) {
        _tag = tag;
    }

    public String getTag() {
        return _tag;
    }
}
