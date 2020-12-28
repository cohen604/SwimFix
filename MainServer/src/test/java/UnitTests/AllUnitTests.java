package UnitTests;

import UnitTests.StreamingTests.*;
import UnitTests.SwimmingErrorDetectorsTests.SwimmingErrorDetectorsTests;
import UnitTests.SwimmingTests.SwimmingTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        StreamingTests.class,
        SwimmingTests.class,
        SwimmingErrorDetectorsTests.class,
})

public class AllUnitTests {

}
