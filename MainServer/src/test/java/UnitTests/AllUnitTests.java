package UnitTests;

import UnitTests.StreamingTests.VideoHandlerTest;
import UnitTests.StreamingTests.VideoTests;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        VideoHandlerTest.class,
        VideoTests.class,
})

public class AllUnitTests {

}
