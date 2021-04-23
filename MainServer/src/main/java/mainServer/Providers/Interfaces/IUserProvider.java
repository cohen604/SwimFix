package mainServer.Providers.Interfaces;

import DTO.FeedbackVideoStreamer;
import DTO.UserDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Interfaces.IUser;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface IUserProvider {

    boolean login(UserDTO userDTO);

    boolean logout(UserDTO userDTO);

    IUser getUser(UserDTO userDTO);

    boolean addFeedbackToUser(IUser user, IFeedbackVideo feedbackVideo);

    boolean reload();

    List<String> filterHistoryByDay(List<FeedbackVideoStreamer> history);

    Map<String, FeedbackVideoStreamer> filterHistoryByPool
            (List<FeedbackVideoStreamer> history, String day);

    boolean deleteFeedbackByID(UserDTO userDTO, String feedbackID);

}
