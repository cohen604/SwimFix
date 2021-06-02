package mainServer.Providers;
import DTO.UserDTOs.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.Streaming.ITextualComment;
import Domain.Summaries.UsersSummary;
import Domain.UserData.*;
import Domain.UserData.Interfaces.ISwimmer;
import Domain.UserData.Interfaces.ITeam;
import Domain.UserData.Interfaces.IUser;
import Storage.Feedbacks.IFeedbackDao;
import Storage.Swimmer.ISwimmerDao;
import Storage.Team.ITeamDao;
import Storage.User.IUserDao;
import mainServer.Providers.Interfaces.IUserProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserProvider implements IUserProvider {

    /**
     * The hash map key: user uid, the values is the user
     */
    private ConcurrentHashMap<String, User> _users;
    private ConcurrentHashMap<String, Team> _teams;
    private IUserDao _userDao;
    private ISwimmerDao _swimmerDao;
    private ITeamDao _teamDao;

    public UserProvider(IUserDao dao, ISwimmerDao swimmerDao, ITeamDao teamDao) {
        _users = new ConcurrentHashMap<>();
        _teams = new ConcurrentHashMap<>();
        _userDao = dao;
        _swimmerDao = swimmerDao;
        _teamDao = teamDao;
    }

    @Override
    public void addSwimAnalyticsUser(UserDTO userDTO) {
        IUser user = getUser(userDTO);
        if(user == null) {
            User admin = new User(userDTO);
            admin.addAdmin();
            admin.addResearcher();

            _userDao.tryInsertThenUpdate(admin);
        }
        else {
            boolean isUpdated = false;
            if (!user.isAdmin()) {
                user.addAdmin();
                isUpdated = true;
            }
            if (!user.isResearcher()) {
                user.addResearcher();
                isUpdated = true;
            }
            if(isUpdated) {
                _userDao.update(_users.get(user.getUid()));
            }
        }
    }

    @Override
    public boolean login(UserDTO userDTO) {
        User user = _users.get(userDTO.getUid());
        if(user == null) {
            user = _userDao.find(userDTO.getUid());
            if(user == null) {
                user = _userDao.insert(new User(userDTO));
                if(user == null) {
                    return false;
                }
            }
            if(_users.putIfAbsent(user.getUid(), user) != null) {
                return false;
            }
        }
        if(user.login()) {
            return _userDao.update(user) != null;
        }
        return false;
    }

    @Override
    public boolean logout(UserDTO userDTO) {
        User user = _users.get(userDTO.getUid());
        if(user != null) {
            if(user.logout()){
                return _userDao.update(user) != null;
            }
        }
        return false;
    }

    @Override
    public IUser getUser(UserDTO userDTO) {
        User user = _users.get(userDTO.getUid());
        if(user == null) {
            user = _userDao.find(userDTO.getUid());
            if(user != null) {
                _users.putIfAbsent(user.getUid(), user);
            }
        }
        return user;
    }

    @Override
    public boolean addFeedbackToUser(IUser user, IFeedbackVideo feedbackVideo) {
        // TODO refactor this to FeedbackProvider to insert the feedback
        User current = _users.get(user.getUid());
        if (current != null
                && current.isLogged()
                && current.addFeedback(feedbackVideo)) {
            if (_swimmerDao.update(current.getSwimmer()) == null) {
                current.deleteFeedback(feedbackVideo.getPath());
            }
            else {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean reload() {
        _users = new ConcurrentHashMap<>();
        return _userDao.logoutAll();
    }

    /***
     * delete a feedback of a user
     * @param user - the user who own the feedback
     * @param feedbackPath - the path of the feedback to delete
     * @return - true if deleted, false if not
     */
    @Override
    public boolean deleteFeedbackByID(IUser user, String feedbackPath) {
        // TODO refactor this to FeedbackProvider to delete the feedback
        User current = _users.get(user.getUid());
        if (current != null && current.isLogged()) {
            IFeedbackVideo video = current.deleteFeedback(feedbackPath);
            if(video != null) {
                if (_swimmerDao.update(current.getSwimmer()) == null) {
                    current.addFeedback(video);
                }
                else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Collection<? extends IUser> findUsersThatNotAdmin(IUser user) {
        if(user.isLogged() && user.isAdmin()) {
            return _userDao.findUsersThatNotAdmin();
        }
        return null;
    }

    @Override
    public Collection<? extends IUser> findUsersThatNotResearcher(IUser user) {
        if(user.isLogged() && user.isAdmin()) {
            return _userDao.findUsersThatNotResearcher();
        }
        return null;
    }

    @Override
    public boolean addAdmin(IUser admin, IUser userToAdd) {
        User user = _users.get(userToAdd.getUid());
        if(admin.isLogged()
                && admin.isAdmin()
                && user !=null) {
            if(user.addAdmin()) {
                if(_userDao.update(user) != null) {
                    return true;
                }
                else  {
                    user.deleteAdmin();
                }
            }
        }
        return false;
    }

    @Override
    public boolean addResearcher(IUser admin, IUser userToAdd) {
        User user = _users.get(userToAdd.getUid());
        if(admin.isLogged()
                && admin.isAdmin()
                && user !=null) {
            if(user.addResearcher()) {
                if(_userDao.update(user) != null) {
                    return true;
                }
                else  {
                    user.deleteResearcher();
                }
            }
        }
        return false;
    }

    @Override
    public UsersSummary getSummary() {
        Long users = _userDao.countUsers();
        Long loggedUsers = _userDao.countLoggedUsers();
        Long swimmers = _userDao.countSwimmers();
        Long loggedSwimmers = _userDao.countLoggedSwimmers();
        Long coaches = _userDao.countCoaches();
        Long loggedCoaches = _userDao.countLoggedCoaches();
        Long admins = _userDao.countAdmins();
        Long loggedAdmins = _userDao.countLoggedAdmins();
        Long researchers = _userDao.countResearchers();
        Long loggedResearchers = _userDao.countLoggedResearchers();
        return new UsersSummary(
                users,
                loggedUsers,
                swimmers,
                loggedSwimmers,
                coaches,
                loggedCoaches,
                admins,
                loggedAdmins,
                researchers,
                loggedResearchers
        );
    }

    @Override
    public boolean addCoach(IUser user, String teamName) {
        User currentUser = _users.get(user.getUid());
        if(!currentUser.isCoach()
                && !_teamDao.isTeamExists(teamName)
                && user.addCoach(teamName)) {
            if(_userDao.update(currentUser) != null) {
                Team team = currentUser.getCoach().getTeam();
                _teams.putIfAbsent(teamName, team);
                return true;
            }
            else {
                user.deleteCoach();
            }

        }
        return false;
    }

    @Override
    public IUser findUser(String email) {
        User output = _userDao.findUserByEmail(email);
        if(output!=null) {
            _users.putIfAbsent(output.getUid(), output);
        }
        return output;
    }

    @Override
    public boolean sendInvitation(IUser user, IUser sendTo) {
        User current = _users.get(user.getUid());
        User userToSendTo = _users.get(sendTo.getUid());
        if(current!=null
                && userToSendTo !=null
                && current.isCoach()
                && current.isLogged()
                && userToSendTo.isSwimmer()) {
            Team team = current.getCoach().getTeam();
            if(_teams.putIfAbsent(team.getName(), team)!= null) {
                team = _teams.get(team.getName());
            }
            Swimmer swimmer = userToSendTo.getSwimmer();
            Invitation invitation = team.addInvitation(swimmer);
            if(invitation!=null) {
                if(_teamDao.update(team)!=null) {
                    if (_swimmerDao.update(swimmer) != null) {
                        return true;
                    } else {
                        team.deleteInvitation(invitation);
                        swimmer.deleteInvitation(invitation);
                        _teamDao.update(team);
                    }
                }
                else {
                    team.deleteInvitation(invitation);
                    swimmer.deleteInvitation(invitation);
                }
            }
        }
        return false;
    }

    @Override
    public boolean approveInvitation(IUser user, String invitationId) {
        User current = _users.get(user.getUid());
        if(current!=null
                && current.isSwimmer()
                && current.isLogged()) {
            Swimmer swimmer = current.getSwimmer();
            Invitation invitation = swimmer.getInvitation(invitationId);
            Team team = _teamDao.find(invitation.getTeamId());
            // take team from cache
            if (_teams.putIfAbsent(team.getName(), team) != null) {
                team = _teams.get(team.getName());
            }
            if (swimmer.approveInvitation(invitationId)) {
                if (team.addSwimmer(swimmer, invitationId, invitation)) {
                    if (_swimmerDao.update(swimmer) != null
                            && _teamDao.update(team) != null) {
                        return true;
                    } else {
                        team.removeSwimmer(swimmer);
                        swimmer.resetInvitation(invitationId);
                    }
                }
                else {
                    swimmer.resetInvitation(invitationId);
                }
            }
        }
        return false;
    }

    @Override
    public boolean denyInvitation(IUser user, String invitationId) {
        User current = _users.get(user.getUid());
        if(current!=null
                && current.isSwimmer()
                && current.isLogged()) {
            Swimmer swimmer = current.getSwimmer();
            Invitation invitation = swimmer.getInvitation(invitationId);
            if(swimmer.denyInvitation(invitationId)) {
                if(_swimmerDao.update(swimmer) != null) {
                    if(_teams.containsKey(invitation.getTeamId())) {
                        _teams.get(invitation.getTeamId()).updateInvitation(invitation);
                    }
                    return true;
                }
                else {
                    swimmer.resetInvitation(invitationId);
                }
            }
        }
        return false;
    }

    @Override
    public boolean leaveTeam(IUser user, String teamId) {
        User current = _users.get(user.getUid());
        if(current != null
                && current.isSwimmer()) {
            Swimmer swimmer = current.getSwimmer();
            Team team = _teams.get(teamId);
            if(team == null) {
                team = _teamDao.find(teamId);
                if(team == null) {
                    return false;
                }
                _teams.putIfAbsent(teamId, team);
            }
            if(swimmer.leaveTeam()) {
                if(team.removeSwimmer(swimmer)) {
                    if(_swimmerDao.update(swimmer) != null
                            && _teamDao.update(team) != null) {
                        return true;
                    }
                    else {
                        team.addSwimmer(swimmer);
                        swimmer.addTeam(teamId);
                    }
                }
                else {
                    swimmer.addTeam(teamId);
                }
            }
        }
        return false;
    }

    @Override
    public String getMyTeam(IUser iUser) {
        User user = _users.get(iUser.getUid());
        if(user!=null
                && user.isLogged()
                && user.isSwimmer()) {
            Swimmer swimmer = user.getSwimmer();
            return swimmer.getTeamId();
        }
        return null;
    }

    @Override
    public ITeam getCoachTeam(IUser iUser) {
        User user = _users.get(iUser.getUid());
        if(user!=null
                && user.isLogged()
                && user.isCoach()) {
            Team team = user.getCoach().getTeam();
//            Team team = _teamDao.find(coachTeam.getName());
            // take team from cache
            if (_teams.putIfAbsent(team.getName(), team) != null) {
                team = _teams.get(team.getName());
            }
            return team;
        }
        return null;
    }

    @Override
    public Set<Map.Entry<String, IFeedbackVideo>> coachGetFeedbacks(IUser coach, IUser swimmer) {
        User userCoach = _users.get(coach.getUid());
        User userSwimmer = _users.get(swimmer.getUid());
        if(userCoach != null
                && userSwimmer != null
                && userCoach.isLogged()
                && userCoach.isCoach()
                && userSwimmer.isSwimmer()) {
            Team team = userCoach.getCoach().getTeam();
            if(team.hasSwimmer(userSwimmer.getSwimmer())) {
                return userSwimmer.getSwimmer().getFeedbacksMap();
            }
        }
        return null;
    }

    @Override
    public IFeedbackVideo coachGetSwimmerFeedback(IUser iCoach, IUser iSwimmer, String feedbackKey) {
        User coach = _users.get(iCoach.getUid());
        User swimmer = _users.get(iSwimmer.getUid());
        if(coach!= null
                && swimmer != null
                && coach.isLogged()
                && coach.isCoach()
                && swimmer.isSwimmer()) {
            Team team = coach.getCoach().getTeam();
            ISwimmer sw = swimmer.getSwimmer();
            if (team.hasSwimmer(sw)) {
                return sw.getFeedback(feedbackKey);
            }
        }
        return null;
    }

    @Override
    public boolean coachAddCommentToFeedback(IUser iUserCoach, IUser iUserSwimmer, String feedbackKey, String commentText) {
        User userCoach = _users.get(iUserCoach.getUid());
        User userSwimmer = _users.get(iUserSwimmer.getUid());
        if(userCoach != null
                && userSwimmer != null
                && userCoach.isLogged()
                && userCoach.isCoach()
                && userSwimmer.isSwimmer()) {
            Team team = userCoach.getCoach().getTeam();
            Swimmer swimmer = userSwimmer.getSwimmer();
            if(team.hasSwimmer(swimmer)) {
                IFeedbackVideo feedbackVideo = swimmer.getFeedback(feedbackKey);
                if(feedbackVideo != null) {
                    ITextualComment comment = feedbackVideo.addComment(userCoach.getEmail(), commentText);
                    if(comment!=null && _swimmerDao.update(swimmer) != null) {
                        return true;
                    }
                    else if(comment!=null) {
                        feedbackVideo.removeComment(comment);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean coachRemoveSwimmerFromTeam(IUser iUserCoach, IUser iUserSwimmer) {
        User userCoach = _users.get(iUserCoach.getUid());
        User userSwimmer = _users.get(iUserSwimmer.getUid());
        if(userCoach != null
                && userSwimmer != null
                && userCoach.isLogged()
                && userCoach.isCoach()
                && userSwimmer.isSwimmer()) {
            Team team = userCoach.getCoach().getTeam();
            Swimmer swimmer = userSwimmer.getSwimmer();
            if(team.removeSwimmer(swimmer)) {
                if(swimmer.leaveTeam()) {
                    if(_swimmerDao.update(swimmer)!=null
                            && _teamDao.update(team)!=null) {
                        return true;
                    }
                    else {
                        swimmer.addTeam(team.getName());
                        team.addSwimmer(swimmer);
                    }
                }
                else {
                    team.addSwimmer(swimmer);
                }
            }
        }
        return false;
    }

}
