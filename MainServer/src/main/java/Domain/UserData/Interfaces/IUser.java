package Domain.UserData.Interfaces;

import Domain.Streaming.IFeedbackVideo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

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

    Collection<LocalDateTime> getFeedbacksDays();

    Collection<IFeedbackVideo> getFeedbacksOfDay(LocalDateTime day);

    boolean addFeedback(IFeedbackVideo feedbackVideo);

    IFeedbackVideo deleteFeedback(String feedbackPath);

    boolean addAdmin();

    void deleteAdmin();

    boolean addResearcher();

    void deleteResearcher();

}
