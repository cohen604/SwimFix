package Domain.UserData;
import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.IFeedbackVideo;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class Swimmer {

    private ConcurrentHashMap<String, IFeedbackVideo> _feedbacks;
    private Queue<Invitation> _invetations;

    private String _tag;

    public Swimmer() {
        _tag = "Swimmer";
        _feedbacks = new ConcurrentHashMap<>();
    }

    public Swimmer(String tag) {
        _tag = tag;
    }

    public String getTag() {
        return _tag;
    }


    public boolean addFeedback(IFeedbackVideo feedbackVideo) {
        return _feedbacks.putIfAbsent(feedbackVideo.getPath(), feedbackVideo) == null;
    }

    public boolean deleteFeedback(IFeedbackVideo feedbackVideo) {
        return _feedbacks.remove(feedbackVideo.getPath()) != null;
    }

    public Collection<IFeedbackVideo> getFeedbacks() {
        return _feedbacks.values();
    }
}
