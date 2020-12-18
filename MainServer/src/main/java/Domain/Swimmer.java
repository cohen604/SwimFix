package Domain;
import java.util.Queue;

public class Swimmer extends State {

    private String uid;
    private Queue<Invitation> invitationQueue;

    public Swimmer(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    String getState() {
        return "swimmer";
    }
}
