package UnitTests.DrawingTests;

import Domain.Drawing.Draw;
import Domain.Drawing.IDraw;
import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.util.Iterator;
import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DrawTests extends TestCase {

    private IDraw _draw;

    @Before
    public void setUp() {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        nu.pattern.OpenCV.loadShared();
        nu.pattern.OpenCV.loadLocally(); // Use in case loadShared() doesn't wor
        _draw = new Draw();
    }

    public void testDrawSimpleCircle() {
        // Arrange
        Random random = new Random();
        Iterator<Integer> iterator = random.ints(10, 100)
                .iterator();
        int width = iterator.next();
        int height = iterator.next();
        Mat frame = setUpFrame(width, height);
        IPoint point = setUpRandomPoint(width, height);
        int radius = random.ints(1, 10)
                .findFirst()
                .getAsInt();
        double expectedB = 0;
        double expectedG = 255;
        double expectedR = 0;
        // Act
        _draw.drawCircle(frame, point, radius);
        // Assert
        for(int i=0; i<frame.height(); i++) {
            for(int j=0; j<frame.width(); j++) {
                double[] data = frame.get(i, j);
                if(true) {

                }
                else {

                }
                for(int c=0;c< data.length; c++) {
                }
            }
        }
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


    private Mat setUpFrame(int width, int height) {
        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        mat.setTo(new Scalar(0,0,0));
        return mat;
    }

    private void printFrame(Mat frame) {
        for(int i=0; i<frame.height(); i++) {
            for(int j=0; j<frame.width(); j++) {
                double[] data = frame.get(i, j);
                System.out.print("[");
                for(int c=0;c< data.length; c++) {
                    System.out.print(data[c] + ", ");
                }
                System.out.print("] ");
            }
            System.out.println();
        }
    }

    protected IPoint setUpPoint(double x, double y) {
        IPoint point = mock(IPoint.class);
        when(point.getX()).thenReturn(x);
        when(point.getY()).thenReturn(y);
        return point;
    }

    private IPoint setUpRandomPoint(int width, int height) {
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
        IPoint head = setUpPoint(xHead, yHead);
        IPoint rightShoulder = setUpPoint(xRightShoulder, yRightShoulder);
        IPoint rightElbow = setUpPoint(xRightElbow, yRightElbow);
        IPoint rightWrist = setUpPoint(xRightWrist, yRightWrist);
        IPoint leftShoulder = setUpPoint(xLeftShoulder, yLeftShoulder);
        IPoint leftElbow = setUpPoint(xLeftElbow, yLeftElbow);
        IPoint leftWrist = setUpPoint(xLeftWrist, yLeftWrist);
        ISwimmingSkeleton skeleton = mock(ISwimmingSkeleton.class);
        when(skeleton.getHead()).thenReturn(head);
        when(skeleton.getRightShoulder()).thenReturn(rightShoulder);
        when(skeleton.getRightElbow()).thenReturn(rightElbow);
        when(skeleton.getRightWrist()).thenReturn(rightWrist);
        when(skeleton.getLeftShoulder()).thenReturn(leftShoulder);
        when(skeleton.getLeftElbow()).thenReturn(leftElbow);
        when(skeleton.getLeftWrist()).thenReturn(leftWrist);
        return skeleton;
    }

    private ISwimmingSkeleton setUpSwimmingSkeleton(int width, int height) {
        Random random = new Random();
        Iterator<Double> widthIterator = random.doubles(0, width).iterator();
        Iterator<Double> heightIterator = random.doubles(0, height).iterator();
        double xHead = widthIterator.next();
        double yHead = heightIterator.next();
        double xRightShoulder = widthIterator.next();
        double yRightShoulder = heightIterator.next();
        double xRightElbow = widthIterator.next();
        double yRightElbow = heightIterator.next();
        double xRightWrist = widthIterator.next();
        double yRightWrist = heightIterator.next();
        double xLeftShoulder = widthIterator.next();
        double yLeftShoulder = heightIterator.next();
        double xLeftElbow = widthIterator.next();
        double yLeftElbow = heightIterator.next();
        double xLeftWrist = widthIterator.next();
        double yLeftWrist = heightIterator.next();
        return setUpSwimmingSkeleton(
                    xHead, yHead,
                    xRightShoulder, yRightShoulder,
                    xRightElbow, yRightElbow,
                    xRightWrist, yRightWrist,
                    xLeftShoulder, yLeftShoulder,
                    xLeftElbow, yLeftElbow,
                    xLeftWrist, yLeftWrist);
    }
}
