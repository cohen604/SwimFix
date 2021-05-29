package Domain.UserData;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IInvitation;
import Domain.UserData.Interfaces.ISwimmer;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Swimmer implements ISwimmer {

    private String _email;
    private ConcurrentHashMap<String, IFeedbackVideo> _feedbacks;
    private String _teamId;
    private ConcurrentHashMap<String, SwimmerInvitation> _pendingInvitations; //key => invitationsId
    private ConcurrentHashMap<String, SwimmerInvitation> _invitationHistory;

    private final Object _teamLock;

    public Swimmer(String email) {
         _email = email;
        _feedbacks = new ConcurrentHashMap<>();
        _teamId = null;
        _pendingInvitations = new ConcurrentHashMap<>();
        _invitationHistory = new ConcurrentHashMap<>();
        _teamLock = new Object();
    }

    public Swimmer(
            String email,
            List<IFeedbackVideo> feedbacks,
            String teamId,
            ConcurrentHashMap<String, SwimmerInvitation> pendingInvitations,
            ConcurrentHashMap<String, SwimmerInvitation> invitationHistory) {
        _email = email;
        _feedbacks = new ConcurrentHashMap<>();
        for (IFeedbackVideo feedbackVideo: feedbacks) {
            _feedbacks.put(feedbackVideo.getPath(), feedbackVideo);
        }
        _teamId = teamId;
        _pendingInvitations = pendingInvitations;
        _invitationHistory = invitationHistory;
        _teamLock = new Object();
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

    @Override
    public String getEmail() {
        return _email;
    }

    @Override
    public boolean addInvitation(Invitation invitation) {
        return _pendingInvitations.putIfAbsent(invitation.getId(), new SwimmerInvitation(invitation)) == null;
    }

    @Override
    public Collection<? extends IInvitation> getInvitations() {
        return _pendingInvitations.values();
    }

    @Override
    public Collection<? extends IInvitation> getInvitationsHistory() {
        return _invitationHistory.values();
    }

    @Override
    public int getNumberOfFeedbacks() {
        return _feedbacks.size();
    }


    public void deleteInvitation(Invitation invitation) {
        _pendingInvitations.remove(invitation.getId());
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

    public String getTeamId() {
        return _teamId;
    }

    public ConcurrentHashMap<String, SwimmerInvitation> getPendingInvitations() {
        return _pendingInvitations;
    }

    public ConcurrentHashMap<String, SwimmerInvitation> getInvitationHistory() {
        return _invitationHistory;
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

    public Invitation getInvitation(String invitationId) {
        return _pendingInvitations.get(invitationId);
    }

    public boolean approveInvitation(String invitationId) {
        SwimmerInvitation pending = _pendingInvitations.get(invitationId);
        return pending!=null
                && pending.approveInvitation()
                && addTeam(pending.getTeamId())
                && _pendingInvitations.remove(invitationId) != null
                && _invitationHistory.putIfAbsent(invitationId, pending) == null;
    }

    public boolean denyInvitation(String invitationId) {
        SwimmerInvitation pending = _pendingInvitations.get(invitationId);
        return pending!=null
                && pending.denyInvitation()
                && _pendingInvitations.remove(invitationId) != null
                && _invitationHistory.putIfAbsent(invitationId, pending) == null;
    }

    public boolean resetInvitation(String invitationId) {
        SwimmerInvitation history = _invitationHistory.get(invitationId);
        return history != null
                && history.resetInvitation()
                && _invitationHistory.remove(invitationId) != null
                && _pendingInvitations.putIfAbsent(invitationId, history) == null;
    }

    public boolean leaveTeam() {
        synchronized (_teamLock) {
            if (_teamId != null) {
                _teamId = null;
                return true;
            }
        }
        return false;
    }

    public boolean addTeam(String teamId) {
        synchronized (_teamLock) {
            if (_teamId == null) {
                _teamId = teamId;
                return true;
            }
        }
        return false;
    }
}
