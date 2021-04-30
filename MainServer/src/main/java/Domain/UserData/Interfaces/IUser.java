package Domain.UserData.Interfaces;

import Domain.Streaming.IFeedbackVideo;
import java.util.Collection;

public interface IUser {

    String getUid();

    String getEmail();

    String getName();

    boolean isLogged();

    boolean isSwimmer();

    boolean isResearcher();

    boolean isCoach();

    boolean isAdmin();

    boolean login();

    boolean logout();

    String getVideosPath();

    String getFeedbacksPath();

    String getSkeletonsPath();

    String getMLSkeletonsPath();

    String getReportsPath();

    String getDownloadsPath();

    Collection<IFeedbackVideo> getFeedbacks();

    boolean addFeedback(IFeedbackVideo feedbackVideo);

    boolean deleteFeedback(String feedbackPath);
}
