package UnitTests.PointsTests;

import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph.SkeletonPoint;
import junit.framework.TestCase;
import org.apache.commons.math3.exception.ZeroException;
import org.junit.Before;
import org.mockito.Mock;

import java.util.Random;

import static Domain.Points.IPointUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IPointUtilsTests extends TestCase {

    protected IPoint pointA;
    protected IPoint pointB;

    public void testCalcDistancePoints() {
        // Arrange
        setUpRandomPoints();
        double dx = pointA.getX() - pointB.getX();
        double dy = pointA.getY() - pointB.getY();
        double expected = Math.sqrt(dx * dx + dy * dy);
        double delta = 0.001;
        // Act
        double result =  calcDistance(pointA, pointB);
        // Assert
        assertEquals(expected, result, delta);
    }

    public void testCalcDistanceValues() {
        // Arrange
        Random random = new Random();
        double x0 = random.nextDouble();
        double y0 = random.nextDouble();
        double x1 = random.nextDouble();
        double y1 = random.nextDouble();
        double dx = x0 - x1;
        double dy = y0 - y1;
        double expected = Math.sqrt(dx * dx + dy * dy);
        double delta = 0.001;
        // Act
        double result = calcDistance(x0, x1, y0, y1);
        // Assert
        assertEquals(expected, result, delta);
    }

    public void testCalcDistancePointsZero() {
        // Arrange
        setUpPointA(0,0);
        setUpPointB(0,0);
        double expected = 0;
        double delta = 0.001;
        // Act
        double result = calcDistance(this.pointA, this.pointB);
        // Assert
        assertEquals(expected, result, delta);
    }

    public void testCalcDistanceValuesZero() {
        // Arrange
        double x0 = 0;
        double y0 = 0;
        double x1 = 0;
        double y1 = 0;
        double expected = 0;
        double delta = 0.001;
        // Act
        double result = calcDistance(x0, x1, y0, y1);
        // Assert
        assertEquals(expected, result, delta);
    }

    public void testCalcSlope() {
        // Arrange
        setUpRandomPoints();
        double dx = pointA.getX() - pointB.getX();
        double dy = pointA.getY() - pointB.getY();
        double delta = 0.001;
        try {
            double expected = dy / dx;
            // Act
            double result = calcSlope(pointB, pointA);
            // Assert
            assertEquals(expected, result, delta);
        }
        catch (ZeroException e) {
            assertEquals(dx, 0);
        }
        catch (Exception e) {
            fail();
        }
    }

    public void testDotProduct() {
        // Arrange
        setUpRandomPoints();
        double expected = pointA.getX() * pointB.getX() + pointA.getY() * pointB.getY();
        double delta = 0.01;
        // Act
        double result = dotProduct(pointA, pointB);
        // Assert
        assertEquals(expected, result, delta);
    }

    public void testGetSize() {
        // Arrange
        Random random = new Random();
        double x = random.nextDouble();
        double y = random.nextDouble();
        setUpPointA(x, y);
        double expected = Math.sqrt(x * x + y * y);
        double delta = 0.01;
        // Act
        double result = getSize(pointA);
        // Assert
        assertEquals(expected, result, delta);
    }

    public void testNormalVec() {
        // Arrange
        setUpRandomPoints();
        double dx = pointB.getX() - pointA.getX();
        double dy = pointB.getY() - pointA.getY();
        double size = Math.sqrt(dx * dx + dy * dy);
        try {
            double expectedX = dx / size;
            double expectedY = dy / size;
            double delta = 0.001;
            // Act
            IPoint result = getNormalVec(pointA, pointB);
            // Assert
            assertEquals(result.getX(), expectedX, delta);
            assertEquals(result.getY(), expectedY, delta);
        }
        catch (ZeroException e) {
            assertEquals(size, 0);
        }
        catch (Exception e) {
            fail();
        }
    }

    public void testGetVec() {
        // Arrange
        setUpRandomPoints();
        double delta = 0.001;
        double expectedX = pointB.getX() - pointA.getX();
        double expectedY = pointB.getY() - pointA.getY();
        // Act
        IPoint result = getVec(pointA, pointB);
        // Assert
        assertEquals(result.getX(), expectedX, delta);
        assertEquals(result.getY(), expectedY, delta);
    }

    public void testAngleBetween() {
        // Arrange
        setUpRandomPoints();
        double delta = 0.001;
        double top = pointA.getX() * pointB.getX() + pointA.getY() * pointB.getY();
        double sizeA = Math.sqrt(pointA.getX() * pointA.getX() + pointA.getY() * pointA.getY());
        double sizeB = Math.sqrt(pointB.getX() * pointB.getX() + pointB.getY() * pointB.getY());
        double bottom = sizeA * sizeB;
        try {
            double expected = Math.toDegrees(Math.acos(top / bottom));
            // Act
            double result = getAngleBetween(pointA, pointB);
            // Assert
            assertEquals(expected, result, delta);
        }
        catch (ZeroException e) {
            assertEquals(bottom, 0, delta);
        }
        catch (Exception e) {
            fail();
        }

    }

    public void testMulByScalar() {
        // Arrange
        Random random = new Random();
        double x = random.nextDouble();
        double y = random.nextDouble();
        setUpPointA(x, y);
        double scalar = random.nextDouble();
        double expectedX = x * scalar;
        double expectedY = y * scalar;
        double delta = 0.001;
        // Act
        IPoint result = mulByScalar(pointA, scalar);
        // Assert
        assertEquals(expectedX, result.getX(), delta);
        assertEquals(expectedY, result.getY(), delta);
    }

    public void testAddByScalar() {
        // Arrange
        Random random = new Random();
        double x = random.nextDouble();
        double y = random.nextDouble();
        setUpPointA(x, y);
        double scalarX = random.nextDouble();
        double scalarY = random.nextDouble();
        double expectedX = x + scalarX;
        double expectedY = y + scalarY;
        double delta = 0.001;
        // Act
        IPoint result = addByScalars(pointA, scalarX, scalarY);
        // Assert
        assertEquals(expectedX, result.getX(), delta);
        assertEquals(expectedY, result.getY(), delta);
    }

    public void testPivotVector() {
        // Arrange
        Random random = new Random();
        double x = random.nextDouble();
        double y = random.nextDouble();
        setUpPointA(x, y);
        double theta = Math.toRadians(random.nextDouble());
        double expectedX = x * Math.cos(theta) - y * Math.sin(theta);
        double expectedY = x * Math.sin(theta) + y * Math.cos(theta);
        double delta = 0.02;
        // Act
        IPoint result = pivotVector(pointA, theta);
        // Assert
        assertEquals(expectedX, result.getX(), delta);
        assertEquals(expectedY, result.getY(), delta);
    }

    /**
     * The function is set up for random points A and B
     */
    private void setUpRandomPoints() {
        Random random = new Random();
        double x = random.nextDouble();
        double y = random.nextDouble();
        setUpPointA(x,y);
        x = random.nextDouble();
        y = random.nextDouble();
        setUpPointB(x,y);
    }

    /**
     * The function is set up for point A as mock
     * @param x - the x value
     * @param y - the y value
     */
    protected void setUpPointA(double x, double y) {
        this.pointA = mock(IPoint.class);
        when(pointA.getX()).thenReturn(x);
        when(pointA.getY()).thenReturn(y);
    }

    /**
     * The function is set up for point B as mock
     * @param x - the x value
     * @param y - the y value
     */
    protected void setUpPointB(double x, double y) {
        this.pointB = mock(IPoint.class);
        when(pointB.getX()).thenReturn(x);
        when(pointB.getY()).thenReturn(y);
    }
}
