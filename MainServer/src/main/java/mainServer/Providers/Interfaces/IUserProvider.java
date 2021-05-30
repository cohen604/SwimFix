package mainServer.Providers.Interfaces;
import DTO.UserDTOs.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.Summaries.UsersSummary;
import Domain.UserData.Interfaces.ITeam;
import Domain.UserData.Interfaces.IUser;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


public interface IUserProvider {

    void addSwimAnalyticsUser(UserDTO userDTO);

    boolean login(UserDTO userDTO);

    boolean logout(UserDTO userDTO);

    IUser getUser(UserDTO userDTO);

    boolean addFeedbackToUser(IUser user, IFeedbackVideo feedbackVideo);

    boolean reload();

    boolean deleteFeedbackByID(IUser user, String feedbackPath);

    Collection<? extends IUser> findUsersThatNotAdmin(IUser user);

    Collection<? extends IUser> findUsersThatNotResearcher(IUser user);

    boolean addAdmin(IUser admin, IUser userToAdd);

    boolean addResearcher(IUser admin, IUser userToAdd);

    UsersSummary getSummary();

    boolean addCoach(IUser user, String teamName);

    IUser findUser(String email);

    boolean sendInvitation(IUser user, IUser sendTo);

    boolean approveInvitation(IUser user, String invitationId);

    boolean denyInvitation(IUser user, String invitationId);

    boolean leaveTeam(IUser user, String teamId);

    String getMyTeam(IUser iUser);

    ITeam getCoachTeam(IUser iUser);

    Set<Map.Entry<String, IFeedbackVideo>> coachGetFeedbacks(IUser coach, IUser swimmer);

    IFeedbackVideo coachGetSwimmerFeedback(IUser iCoach, IUser iSwimmer, String feedbackKey);

    boolean coachAddCommentToFeedback(IUser iUserCoach, IUser iUserSwimmer, String feedbackKey, String commentText);
}

