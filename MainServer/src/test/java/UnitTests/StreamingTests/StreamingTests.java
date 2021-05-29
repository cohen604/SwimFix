package UnitTests.StreamingTests;

import Domain.Streaming.TextualComment;
import Domain.Streaming.VisualComment;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        VideoHandlerTest.class,
        VideoTests.class,
        FeedbackTests.class,
        TaggedVideoTests.class,
        FactoryFeedbackVideoTests.class,
        FactoryVideoHandlerTests.class,
        TextualCommentTests.class,
        VisualComentsTests.class,
})


public class StreamingTests {
}
