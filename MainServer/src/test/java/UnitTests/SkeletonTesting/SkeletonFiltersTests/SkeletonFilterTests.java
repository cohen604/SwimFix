package UnitTests.SkeletonTesting.SkeletonFiltersTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    HeadFilterTests.class,
    LeftElbowFiltersTests.class,
    LeftWristFiltersTests.class,
    LeftShoulderFiltersTests.class,
    RightElbowFiltersTests.class,
    RightWristFiltersTests.class,
    RightShoulderFiltersTests.class,
    RightFilterTests.class,
    LeftFilterTests.class,
})

public class SkeletonFilterTests {
}

