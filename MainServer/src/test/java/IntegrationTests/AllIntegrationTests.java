package IntegrationTests;
import IntegrationTests.DrawingTests.DrawingTests;
import IntegrationTests.ErrorsTests.ErrorTests;
import IntegrationTests.PointsTests.PointsTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    PointsTests.class,
    DrawingTests.class,
    ErrorTests.class,
})

public class AllIntegrationTests {

}
    