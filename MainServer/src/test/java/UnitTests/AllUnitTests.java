package UnitTests;

import Domain.Streaming.FeedbackVideo;
import UnitTests.StreamingTests.FeedbackTests;
import UnitTests.StreamingTests.TaggedVideoTests;
import UnitTests.StreamingTests.VideoHandlerTest;
import UnitTests.StreamingTests.VideoTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        VideoHandlerTest.class,
        VideoTests.class,
        FeedbackTests.class,
        TaggedVideoTests.class,
})

public class AllUnitTests {

}
