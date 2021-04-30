package UnitTests.DrawingTests;

import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import junit.framework.TestCase;

import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DrawTests extends TestCase {

    public void testDrawSimpleCircle() {
        //TODO
    }

    public void testDrawComplexCircle() {
        //TODO
    }

    public void testDrawEclipse() {
        //TODO
    }

    public void testDrawSimpleLine() {
        //TODO
    }

    public void testDrawComplexLine() {
        //TODO
    }

    public void testDrawSwimmer() {
        //TODO
    }

    public void testDrawLogo() {
        //TODO
    }

    public void testDrawMessage() {
        //TODO
    }

    public void testDrawError() {
        //TODO
    }

    public void testDrawVerticalArrow() {
        //TODO
    }

    protected IPoint setUpPoint(double x, double y) {
        IPoint point = mock(IPoint.class);
        when(point.getX()).thenReturn(x);
        when(point.getY()).thenReturn(y);
        return point;
    }

    private IPoint setUpRandomPoint(double width, double height) {
        Random random = new Random();
        double x = random.doubles(0, width)
                .findFirst()
                .getAsDouble();
        double y = random.doubles(0, height)
                .findFirst()
                .getAsDouble();
        return setUpPoint(x, y);
    }

    protected ISwimmingSkeleton setUpSwimmingSkeleton(
            double xHead, double yHead,
            double xRightShoulder, double yRightShoulder,
            double xRightElbow, double yRightElbow,
            double xRightWrist, double yRightWrist,
            double xLeftShoulder, double yLeftShoulder,
            double xLeftElbow, double yLeftElbow,
            double xLeftWrist, double yLeftWrist) {
        return null;
    }

    private ISwimmingSkeleton setUpSwimmingSkeleton(double width, double height) {
        return null;
    }
}
