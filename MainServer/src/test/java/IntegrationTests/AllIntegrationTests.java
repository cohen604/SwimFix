package IntegrationTests;
import IntegrationTests.PointsTests.PointsTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    PointsTests.class,
})

public class AllIntegrationTests {

}
