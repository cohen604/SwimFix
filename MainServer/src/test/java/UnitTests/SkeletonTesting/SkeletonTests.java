package UnitTests.SkeletonTesting;

import UnitTests.SkeletonTesting.SkeletonFiltersTests.SkeletonFilterTests;
import UnitTests.SkeletonTesting.SkeletonValueFiltersTests.SkeletonValueFilterTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        SkeletonValueFilterTests.class,
        SkeletonFilterTests.class,
})

public class SkeletonTests {
}
