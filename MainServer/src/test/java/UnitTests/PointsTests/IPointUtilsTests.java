package UnitTests.PointsTests;

import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph.SkeletonPoint;
import junit.framework.TestCase;

import static Domain.Points.IPointUtils.*;

public class IPointUtilsTests extends TestCase {

    private IPoint a = new SkeletonPoint(2,2, 1);
    private IPoint b = new SkeletonPoint(4,4, 1);

    public void testCalcDistance() {
        double expected = Math.sqrt(8);
        double result =  calcDistance(a,b);
        assertEquals(expected, result);
    }

    public void testCalcDistancePoints() {
        //TODO
    }

    public void testCalcDistanceValues() {
        //TODO
    }

    public void testCalcDistancePointsZero() {
        //TODO
    }

    public void testCalcDistanceValuesZero() {
        //TODO
    }

    public void testCalcSlope() {
        double expected = 1;
        double result = calcSlope(a, b);
        assertEquals(expected, result);
    }

    public void testDotProduct() {
        double expected = 16;
        double result = dotProduct(a, b);
        assertEquals(expected, result);
    }

    public void testGetSize() {
        double expected = Math.sqrt(8);
        double result = getSize(a);
        assertEquals(expected, result);
    }

    public void testNormalVec() {
        IPoint result = getNormalVec(a, b);
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
        double result = getAngleBetween(a,b);
        double threshold = 0.01;
        assertTrue(Math.abs(result - expected) < threshold);
    }

}
