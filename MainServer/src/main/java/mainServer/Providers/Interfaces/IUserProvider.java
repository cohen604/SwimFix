package mainServer.Providers.Interfaces;

import DTO.FeedbackVideoStreamer;
import DTO.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IUser;

import java.util.List;
import java.util.Map;

public interface IUserProvider {

    boolean login(UserDTO userDTO);

    boolean logout(UserDTO userDTO);

    IUser getUser(UserDTO userDTO);

    boolean addFeedbackToUser(IUser user, IFeedbackVideo feedbackVideo);

    boolean reload();

    Map<String, Map<String, FeedbackVideoStreamer>> filter_history
            (List<FeedbackVideoStreamer> history);

}
