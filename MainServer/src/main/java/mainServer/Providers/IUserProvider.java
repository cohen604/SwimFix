package mainServer.Providers;

import DTO.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IUser;

public interface IUserProvider {

    boolean login(UserDTO userDTO);

    IUser getUser(UserDTO userDTO);

    boolean addFeedbackToUser(IUser user, IFeedbackVideo feedbackVideo);
}
