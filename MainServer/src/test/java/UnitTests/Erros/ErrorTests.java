package UnitTests.Erros;

import UnitTests.DrawingTests.DrawTests;
import UnitTests.DrawingTests.FactoryDrawTests;
import UnitTests.Erros.Factories.ErrorFactoriesTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    ErrorFactoriesTests.class,
    LeftElbowErrorTests.class,
    LeftForearmErrorTests.class,
    LeftPalmCrossHeadErrorTests.class,
    RightElbowErrorTests.class,
    RightForearmErrorTests.class,
    RightPalmCrossHeadErrorTests.class,
})

public class ErrorTests {
}
