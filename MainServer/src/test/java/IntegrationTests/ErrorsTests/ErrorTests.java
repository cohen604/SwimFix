package IntegrationTests.ErrorsTests;

import IntegrationTests.ErrorsTests.Factories.ErrorFactoriesTests;
import IntegrationTests.ErrorsTests.SkeletonComposition.ErrorCompositionTests;
import IntegrationTests.ErrorsTests.SkeletonComposition.LeftElbowErrorIntegrationTests;
import IntegrationTests.ErrorsTests.SkeletonGraph.ErrorGraphTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    ErrorFactoriesTests.class,
    ErrorCompositionTests.class,
    ErrorGraphTests.class,
})

public class ErrorTests {
}
