import AcceptanceTests.AllAcceptanceTests;
import ExternalSystemTests.AllExternalSystemTests;
import IntegrationTests.AllIntegrationTests;
import UnitTests.AllUnitTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        AllUnitTests.class,
        AllIntegrationTests.class,
        //TODO AllExternalSystemTests.class,
        AllAcceptanceTests.class,
})

public class AllTests {

}
