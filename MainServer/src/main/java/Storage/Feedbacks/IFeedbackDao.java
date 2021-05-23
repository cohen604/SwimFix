package Storage.Feedbacks;

import Domain.Streaming.FeedbackVideo;

import java.util.List;

public interface IFeedbackDao {

    List<FeedbackVideo> getAll();

    FeedbackVideo insert(FeedbackVideo value);

    FeedbackVideo find(String id);

    FeedbackVideo update(FeedbackVideo value);

    FeedbackVideo tryInsertThenUpdate(FeedbackVideo value);

    boolean removeFeedback(String id);
}
