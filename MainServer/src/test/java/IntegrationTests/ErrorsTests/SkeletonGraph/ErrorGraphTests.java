package IntegrationTests.ErrorsTests.SkeletonGraph;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    LeftElbowErrorIntegrationTests.class,
    LeftForearmErrorIntegrationTests.class,
    LeftPalmCrossHeadErrorIntegrationTests.class,
    RightElbowErrorIntegrationTests.class,
    RightForearmErrorIntegrationTests.class,
    RightPalmCrossHeadErrorIntegrationTests.class,
})

public class ErrorGraphTests {
}
