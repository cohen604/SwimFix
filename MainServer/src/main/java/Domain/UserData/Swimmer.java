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

    public Swimmer() {
        _feedbacks = new ConcurrentHashMap<>();
    }

    public Swimmer(List<IFeedbackVideo> feedbacks) {
        _feedbacks = new ConcurrentHashMap<>();
        for (IFeedbackVideo feedbackVideo: feedbacks) {
            _feedbacks.put(feedbackVideo.getPath(), feedbackVideo);
        }
    }


    public boolean addFeedback(IFeedbackVideo feedbackVideo) {
        return _feedbacks.putIfAbsent(feedbackVideo.getPath(), feedbackVideo) == null;
    }

    public boolean deleteFeedback(String feedbackPath) {
        return _feedbacks.remove(feedbackPath) != null;
    }

    public Collection<IFeedbackVideo> getFeedbacks() {
        return _feedbacks.values();
    }

    public IFeedbackVideo get(String path) {
        return _feedbacks.get(path);
    }
}
