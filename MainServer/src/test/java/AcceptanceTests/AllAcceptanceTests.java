package AcceptanceTests;
import IntegrationTests.DrawingTests.DrawingTests;
import IntegrationTests.ErrorsTests.ErrorTests;
import IntegrationTests.PointsTests.PointsTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        LoginTests.class,
        ViewVideoTests.class,
        UploadVideoForStreamerTest.class,
})

public class AllAcceptanceTests {

}
