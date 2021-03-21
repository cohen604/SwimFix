package UnitTests.SwimmingTests;

import Domain.SwimmingData.SwimmingSkeletonComposition.SkeletonPoint;
import junit.framework.TestCase;
import org.junit.Assert;

public class SkeletonPointCompositionTests extends TestCase {


    public void testGetX() {
        // Arrange + Act
        SkeletonPoint point = new SkeletonPoint(2,2);
        double expected = 2.0;
        // Assert
        Assert.assertEquals(expected, point.getX(), 0.01);
    }

    public void testGetY() {
        // Arrange + Act
        SkeletonPoint point = new SkeletonPoint(2,2);
        double expected = 2.0;
        // Assert
        Assert.assertEquals(expected, point.getY(), 0.01);
    }

    public void testSetX() {
        // Arrange
        SkeletonPoint point = new SkeletonPoint(2,2);
        double expectedX = 5.0;
        double expectedY = 2.0;
        // Act
        point.setX(expectedX);
        // Assert
        Assert.assertEquals(expectedX, point.getX(),0.01);
        Assert.assertEquals(expectedY, point.getY(),0.01);
    }

    public void testSetY() {
        // Arrange
        SkeletonPoint point = new SkeletonPoint(2,2);
        double expectedX = 2.0;
        double expectedY = 5.0;
        // Act
        point.setY(expectedY);
        // Assert
        Assert.assertEquals(expectedX, point.getX(),0.01);
        Assert.assertEquals(expectedY, point.getY(),0.01);
    }

}
