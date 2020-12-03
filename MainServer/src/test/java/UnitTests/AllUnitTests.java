package UnitTests;

import UnitTests.StreamingTests.VideoHandlerTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllUnitTests {
    public static Test suite() {
        TestSuite suite = new TestSuite("Unit Tests");
        suite.addTest(new VideoHandlerTest());
        return suite;
    }
}
