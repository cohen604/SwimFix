package UnitTests.InterpolationTests.TimeSkeletonInterpolationTests;

import DomainLogic.Interpolations.TimeSkeletonInterpolation.*;
import junit.framework.TestCase;
import org.junit.BeforeClass;

public class UpdatersTests extends TestCase {

    private Complete complete;

    @BeforeClass
    public void setUp() {
        complete = new Complete();
    }

    public void testLeftWristUpdater() {
        LeftWristUpdater leftWristUpdater = new LeftWristUpdater();
        assertTrue(leftWristUpdater.update(complete).isLeftWrist());
    }

    public void testRightWristUpdater() {
        RightWristUpdater rightWristUpdater = new RightWristUpdater();
        assertTrue(rightWristUpdater.update(complete).isRightWrist());
    }

    public void testLeftElbowUpdater() {
        LeftElbowUpdater leftElbowUpdater = new LeftElbowUpdater();
        assertTrue(leftElbowUpdater.update(complete).isLeftElbow());
    }

    public void testRightElbowUpdater() {
        RightElbowUpdater rightElbowUpdater = new RightElbowUpdater();
        assertTrue(rightElbowUpdater.update(complete).isRightElbow());
    }

    public void testLeftShoulderUpdater() {
        LeftShoulderUpdater leftShoulderUpdater = new LeftShoulderUpdater();
        assertTrue(leftShoulderUpdater.update(complete).isLeftShoulder());
    }

    public void testRightShoulderUpdater() {
        RightShoulderUpdater rightShoulderUpdater = new RightShoulderUpdater();
        assertTrue(rightShoulderUpdater.update(complete).isRightShoulder());
    }


}
