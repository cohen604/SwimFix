package AcceptanceTests;

import AcceptanceTests.Bridge.AcceptanceTestsBridge;
import AcceptanceTests.Bridge.AcceptanceTestsProxy;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

public abstract class AcceptanceTests extends TestCase {

    protected AcceptanceTestsBridge bridge;

    public AcceptanceTests() {
        this.bridge = new AcceptanceTestsProxy();
    }

    public void setUpBridge() {
        this.bridge = new AcceptanceTestsProxy();
    }

    public void tearDownBridge() {

    }
}
