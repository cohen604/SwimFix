package UnitTests.StreamingTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        VideoHandlerTest.class,
        VideoTests.class,
        FeedbackTests.class,
        TaggedVideoTests.class,
})


public class StreamingTests {
}
