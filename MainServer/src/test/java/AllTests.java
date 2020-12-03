import UnitTests.AllUnitTests;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
    public static Test suite() {
        TestSuite suite = new TestSuite("Tests");
        suite.addTest(AllUnitTests.suite());
        return suite;
    }
}
