package UnitTests.SwimmingErrorDetectorsTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ElbowErrorDetectorTests.class,
        ForearmErrorDetectorTests.class,
        PalmCrossHeadDetectorTests.class,
})

public class SwimmingErrorDetectorsTests {
}
