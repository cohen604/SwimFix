package UnitTests.SkeletonTesting.SkeletonValueFiltersTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    HeadValueFilterTests.class,
    LeftElbowValueFilterTests.class,
    LeftShoulderValueFilterTests.class,
    LeftWristValueFilterTests.class,
    RightElbowValueFilterTests.class,
    RightShoulderValueFilterTests.class,
    RightWristValueFilterTests.class,
})

public class SkeletonValueFilterTests {
}
