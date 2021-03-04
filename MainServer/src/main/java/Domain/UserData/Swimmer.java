package Domain.UserData;
import Domain.Streaming.FeedbackVideo;

import java.util.List;
import java.util.Queue;

public class Swimmer {

    private List<FeedbackVideo> _feedbacks;
    private Queue<Invitation> _invetations;

    private String _tag;

    public Swimmer() {
        _tag = "Swimmer";
    }

    public Swimmer(String tag) {
        _tag = tag;
    }

    public String getTag() {
        return _tag;
    }



}
