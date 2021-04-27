package UnitTests;

import UnitTests.InterpolationTests.InterpolationTests;
import UnitTests.StreamingTests.*;
import UnitTests.SwimmingErrorDetectorsTests.SwimmingErrorDetectorsTests;
import UnitTests.SwimmingTests.SwimmingTests;
import UnitTests.UserTests.UsersTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({

        StreamingTests.class,
        SwimmingErrorDetectorsTests.class,
        SwimmingTests.class,
        UsersTests.class,
        InterpolationTests.class,
})

public class AllUnitTests {

}
