package UnitTests.InterpolationTests;

import UnitTests.InterpolationTests.TimeSkeletonInterpolationTests.TimeInterpolationTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        LinearInterpolationTests.class,
        MedianInterpolationTests.class,
        SpilneInterpolationTests.class,
        TimeInterpolationTests.class,
})

public class InterpolationTests {
}
