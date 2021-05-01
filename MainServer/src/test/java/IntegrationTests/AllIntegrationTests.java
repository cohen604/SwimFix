package IntegrationTests;
import IntegrationTests.DrawingTests.DrawingTests;
import IntegrationTests.PointsTests.PointsTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    PointsTests.class,
    DrawingTests.class,
})

public class AllIntegrationTests {

}
