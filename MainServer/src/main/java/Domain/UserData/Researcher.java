package Domain.UserData;

public class Researcher extends Swimmer {

    private String _tag;

    public Researcher() {
        _tag = "Researcher";
    }

    public Researcher(String tag) {
        _tag = tag;
    }

    public String getTag() {
        return _tag;
    }
}
