package IntegrationTests.ErrorsTests;

import IntegrationTests.ErrorsTests.Factories.ErrorFactoriesTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    ErrorFactoriesTests.class,
})

public class ErrorTests {
}
