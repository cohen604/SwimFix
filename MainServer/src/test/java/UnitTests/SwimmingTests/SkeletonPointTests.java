package UnitTests.SwimmingTests;

import Domain.SwimmingData.SkeletonPoint;
import junit.framework.TestCase;

public class SkeletonPointTests extends TestCase {

    private SkeletonPoint a = new SkeletonPoint(2,2, 1);
    private SkeletonPoint b = new SkeletonPoint(4,4, 1);

    public void testCalcDistance() {
        double expected = Math.sqrt(8);
        double result = a.calcDistance(b);
        assertEquals(expected, result);
    }

    public void testCalcSlope() {
        double expected = 1;
        double result = a.calcSlope(b);
        assertEquals(expected, result);
    }

    public void testDotProduct() {
        double expected = 16;
        double result = a.dotProduct(b);
        assertEquals(expected, result);
    }

    public void testGetSize() {
        double expected = Math.sqrt(8);
        double result = a.getSize();
        assertEquals(expected, result);
    }

    public void testNormalVec() {
        SkeletonPoint result = a.getNormalVec(b);
        double expectedX = 0.707;
        double expectedY = 0.707;
        double threshold = 0.001;
        assertTrue(Math.abs(expectedX - result.getX()) < threshold);
        assertTrue(Math.abs(expectedY - result.getY()) < threshold);
    }

    public void testAngleBetween() {
        SkeletonPoint a = new SkeletonPoint(1,1, 1);
        SkeletonPoint b = new SkeletonPoint(1,2, 1);
        double expected = 18.43;
        double result = a.getAngleBetween(b);
        double threshold = 0.01;
        assertTrue(Math.abs(result - expected) < threshold);
    }

    public void testIsConfident() {
        SkeletonPoint a = new SkeletonPoint(1,1, 1);
        assertTrue(a.isConfident());
        SkeletonPoint b = new SkeletonPoint(1,1, 0.0001);
        assertFalse(b.isConfident());
    }

}
