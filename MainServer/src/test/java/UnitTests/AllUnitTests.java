package UnitTests;

import UnitTests.StreamingTests.VideoHandlerTest;
import UnitTests.StreamingTests.VideoTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        VideoHandlerTest.class,
        VideoTests.class,
        //VideoService.class,
})

public class AllUnitTests {

}
