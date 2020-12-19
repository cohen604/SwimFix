package Domain;
import Domain.Streaming.FeedbackVideo;

import java.util.List;
import java.util.Queue;

public class Swimmer extends State {

    private String uid;
    private List<FeedbackVideo> feedbackVideos;
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
