package UnitTests;

import UnitTests.PeriodTimeTests.AllPeriodTimeTests;
import UnitTests.PointsTests.IPointUtilsTests;
import UnitTests.ProvidersTests.ProvidersTests;
import UnitTests.SkeletonFiltersTests.SkeletonFiltersTests;
import UnitTests.StreamingTests.*;
import UnitTests.SwimmingErrorDetectorsTests.SwimmingErrorDetectorsTests;
import UnitTests.SwimmingTests.SwimmingTests;
import UnitTests.UserTests.UsersTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    AllPeriodTimeTests.class,
    IPointUtilsTests.class,
    ProvidersTests.class,
    SkeletonFiltersTests.class,
    StreamingTests.class,
    SwimmingErrorDetectorsTests.class,
    SwimmingTests.class,
    UsersTests.class,
})

public class AllUnitTests {

}
