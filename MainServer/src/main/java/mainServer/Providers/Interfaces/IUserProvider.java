package mainServer.Providers.Interfaces;
import DTO.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IUser;
import java.util.Collection;


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
}
