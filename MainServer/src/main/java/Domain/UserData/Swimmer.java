package Domain.UserData;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.ISwimmer;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Swimmer implements ISwimmer {

    private String _email;
    private ConcurrentHashMap<String, IFeedbackVideo> _feedbacks;
    private String teamId;
    private ConcurrentHashMap<String, SwimmerInvitation> pendingInvitiations;
    private ConcurrentHashMap<String, Invitation> InvitiaionHistory;

    public Swimmer(String email) {
         _email = email;
        _feedbacks = new ConcurrentHashMap<>();
    }

    public Swimmer(String email, List<IFeedbackVideo> feedbacks) {
        _email = email;
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

    public String getEmail() {
        return _email;
    }

    public Collection<IFeedbackVideo> getFeedbacks() {
        return _feedbacks.values();
    }

    public IFeedbackVideo get(String path) {
        return _feedbacks.get(path);
    }

    public Collection<LocalDateTime> getFeedbacksDays() {
        Map<String, LocalDateTime> localDateMap = new HashMap<>();
        Map<LocalDateTime, List<IFeedbackVideo>> map = getFeedbacksDayMap(localDateMap);
        return map.keySet();
    }

    public Collection<IFeedbackVideo> getFeedbacksOfDay(LocalDateTime day) {
        Map<String, LocalDateTime> localDateMap = new HashMap<>();
        Map<LocalDateTime, List<IFeedbackVideo>> map = getFeedbacksDayMap(localDateMap);
        LocalDateTime date = localDateMap.get(dayToString(day));
        return map.get(date);
    }

    private Map<LocalDateTime, List<IFeedbackVideo>> getFeedbacksDayMap(Map<String, LocalDateTime> localDateMap) {
        Map<LocalDateTime, List<IFeedbackVideo>> output = new HashMap<>();
        for (IFeedbackVideo feedbackVideo: _feedbacks.values()) {
            LocalDateTime date = feedbackVideo.getDate();
            LocalDateTime dayDate = LocalDateTime.of(
                    date.getYear(),
                    date.getMonth(),
                    date.getDayOfMonth(), 0, 0);
            if(localDateMap.containsKey(dayToString(dayDate))) {
                LocalDateTime tmp = localDateMap.get(dayToString(dayDate));
                output.get(tmp).add(feedbackVideo);
            }
            else {
                LinkedList<IFeedbackVideo> list = new LinkedList<>();
                list.add(feedbackVideo);
                output.put(date, list);
                localDateMap.put(dayToString(dayDate), date);
            }
        }
        return output;
    }

    private String dayToString(LocalDateTime localDateTime) {
        return localDateTime.getYear() + "." +
                localDateTime.getMonth() + "." +
                localDateTime.getDayOfMonth();
    }
}
