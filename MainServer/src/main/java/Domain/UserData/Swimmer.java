package Domain.UserData;
import Domain.Streaming.IFeedbackVideo;

import java.time.LocalDateTime;
import java.util.*;
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
        if (feedbackVideo == null) {
            return false;
        }
        return _feedbacks.putIfAbsent(feedbackVideo.getPath(), feedbackVideo) == null;
    }

    public IFeedbackVideo deleteFeedback(String feedbackPath) {
        return _feedbacks.remove(feedbackPath);
    }

    public Collection<IFeedbackVideo> getFeedbacks() {
        return _feedbacks.values();
    }

    public IFeedbackVideo get(String path) {
        return _feedbacks.get(path);
    }

    public Collection<LocalDateTime> getFeedbacksDays() {
        Map<LocalDateTime, List<IFeedbackVideo>> map = getFeedbacksDayMap();
        return map.keySet();
    }

    public Collection<IFeedbackVideo> getFeedbacksOfDay(LocalDateTime day) {
        Map<LocalDateTime, List<IFeedbackVideo>> map = getFeedbacksDayMap();
        return map.get(day);
    }

    private Map<LocalDateTime, List<IFeedbackVideo>> getFeedbacksDayMap() {
        Map<LocalDateTime, List<IFeedbackVideo>> output = new HashMap<>();
        for (IFeedbackVideo feedbackVideo: _feedbacks.values()) {
            LocalDateTime date = feedbackVideo.getDate();
            LocalDateTime dayDate = LocalDateTime.of(
                    date.getYear(),
                    date.getMonth(),
                    date.getDayOfMonth(), 0, 0);
            if(output.containsKey(dayDate)) {
                output.get(dayDate).add(feedbackVideo);
            }
            else {
                LinkedList<IFeedbackVideo> list = new LinkedList<>();
                list.add(feedbackVideo);
                output.put(date, list);
            }
        }
        return output;
    }

}
