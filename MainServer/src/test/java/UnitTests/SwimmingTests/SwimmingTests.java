package UnitTests.SwimmingTests;

import UnitTests.PointsTests.IPointUtilsTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        SwimmingSkeletonGraphTests.class,
        SkeletonPointGraphTests.class,
        SkeletonPointCompositionTests.class,
        SwimmingSkeletonCompositionTests.class,
})

public class SwimmingTests {
}
