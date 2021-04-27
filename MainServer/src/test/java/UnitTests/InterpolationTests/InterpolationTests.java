package UnitTests.InterpolationTests;

import DomainLogic.Interpolations.SplineInterpolation;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        LinearInterpolationTests.class,
        MedianInterpolationTests.class,
        SpilneInterpolationTests.class,
})

public class InterpolationTests {
}
