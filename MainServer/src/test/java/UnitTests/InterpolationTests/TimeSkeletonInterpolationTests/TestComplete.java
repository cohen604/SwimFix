package UnitTests.InterpolationTests.TimeSkeletonInterpolationTests;

import DomainLogic.Interpolations.TimeSkeletonInterpolation.Complete;
import junit.framework.TestCase;
import org.junit.BeforeClass;

public class TestComplete extends TestCase {

    private Complete complete;

    @BeforeClass
    public void setUp() {
        this.complete = new Complete();
    }

    public void testIsHead() {
        assertFalse(complete.isHead());
    }

    public void testIsLeftElbow() {
        assertFalse(complete.isLeftElbow());
    }

    public void testLeftShoulder() {
        assertFalse(complete.isLeftShoulder());
    }

    public void testLeftWrist() {
        assertFalse(complete.isLeftWrist());
    }

    public void testIsRightElbow() {
        assertFalse(complete.isRightElbow());
    }

    public void testRightShoulder() {
        assertFalse(complete.isRightShoulder());
    }

    public void testIsRightWrist() {
        assertFalse(complete.isRightWrist());
    }

    public void testSetHeadTrue() {
        complete.setHead(true);
        assertTrue(complete.isHead());
    }

    public void testSetHeadFalse() {
        complete.setHead(false);
        assertFalse(complete.isHead());
    }

    public void testSetLeftWristTrue() {
        complete.setLeftWrist(true);
        assertTrue(complete.isLeftWrist());
    }

    public void testSetLeftWristFalse() {
        complete.setLeftWrist((false));
        assertFalse(complete.isLeftWrist());
    }

    public void testSetRightWristTrue() {
        complete.setRightWrist(true);
        assertTrue(complete.isRightWrist());
    }

    public void testSetRightWristFalse() {
        complete.setRightWrist(false);
        assertFalse(complete.isRightWrist());
    }

    public void testSetLeftElbowTrue() {
        complete.setLeftElbow(true);
        assertTrue(complete.isLeftElbow());
    }

    public void testSetLeftElbowFalse() {
        complete.setLeftElbow(false);
        assertFalse(complete.isLeftElbow());
    }

    public void testSetRightElbowTrue() {
        complete.setRightElbow(true);
        assertTrue(complete.isRightElbow());
    }

    public void testSetRightElbowFalse() {
        complete.setRightElbow(false);
        assertFalse(complete.isRightElbow());
    }

    public void testSetLeftShoulderTrue() {
        complete.setLeftShoulder(true);
        assertTrue(complete.isLeftShoulder());
    }

    public void testSetLeftShoulderFalse() {
        complete.setLeftShoulder(false);
        assertFalse(complete.isLeftShoulder());
    }

    public void testSetRightShoulderTrue() {
        complete.setRightShoulder(true);
        assertTrue(complete.isRightShoulder());
    }

    public void testSetRightShoulderFalse() {
        complete.setRightShoulder(false);
        assertFalse(complete.isRightShoulder());
    }



}
