package UnitTests.Erros;

import Domain.Errors.Interfaces.SwimmingError;
import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.Before;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class ErrorDrawTests extends TestCase {

    private SwimmingError swimmingError;

    @Before
    protected void setUp() {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        nu.pattern.OpenCV.loadShared();
        nu.pattern.OpenCV.loadLocally(); // Use in case loadShared() doesn't work
        swimmingError = setUpSwimmingError();
    }

    protected abstract SwimmingError setUpSwimmingError();

    protected IPoint setUpPoint(double x, double y) {
        IPoint point = mock(IPoint.class);
        when(point.getX()).thenReturn(x);
        when(point.getY()).thenReturn(y);
        return point;
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
        List<Pair<IPoint, IPoint>> lines = new LinkedList<>();

        when(skeleton.getLines()).thenReturn(lines);
        List<IPoint> points = new LinkedList<>();
        points.add(head);
        points.add(rightShoulder);
        points.add(rightElbow);
        points.add(rightWrist);
        points.add(leftShoulder);
        points.add(leftElbow);
        points.add(leftWrist);
        when(skeleton.getPoints()).thenReturn(points);

        return skeleton;
    }

    public void testDrawBefore() {
        //Arrange
        Random random = new Random();
        Iterator<Integer> iterator = random.ints(80, 200)
                .iterator();
        int width = iterator.next();
        int height = iterator.next();
        Mat frame = setUpFrame(width, height);
        ISwimmingSkeleton skeleton = setUpSwimmingSkeleton(width, height);
        //Act
        swimmingError.drawBefore(frame, skeleton);
        //Assert
        checkFrame(frame);
    }

    public void testDrawAfter() {
        //Arrange
        Random random = new Random();
        Iterator<Integer> iterator = random.ints(80, 200)
                .iterator();
        int width = iterator.next();
        int height = iterator.next();
        Mat frame = setUpFrame(width, height);
        ISwimmingSkeleton skeleton = setUpSwimmingSkeleton(width, height);
        //Act
        swimmingError.drawAfter(frame, skeleton);
        //Assert
        checkFrame(frame);
    }

    private void checkFrame(Mat frame) {
        boolean foundDrawing = false;
        for(int i=0; i<frame.height() && !foundDrawing; i++) {
            for (int j=0; j<frame.width() && !foundDrawing; j++) {
                double[] data = frame.get(i,j);
                for(int c=0; c < data.length && !foundDrawing; c++) {
                    if(data[c] > 0) {
                        foundDrawing = true;
                    }
                }
            }
        }
        assertTrue(foundDrawing);
    }

    private Mat setUpFrame(int width, int height) {
        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        mat.setTo(new Scalar(0,0,0));
        return mat;
    }

    private ISwimmingSkeleton setUpSwimmingSkeleton(double width, double height) {
        Random random = new Random();
        Iterator<Double> widthIterator = random.doubles(width / 3, width / 2)
                .iterator();
        Iterator<Double> heightIterator = random.doubles(height / 3, height / 2)
                .iterator();
        double addX = 10;
        double addY = 10;
        double xHead = widthIterator.next();
        double yHead = heightIterator.next();
        double xRightShoulder = xHead + addX;
        double yRightShoulder = yHead + addY;
        double xRightElbow = xRightShoulder + addX;
        double yRightElbow = yRightShoulder + addY;
        double xRightWrist = xRightElbow + addX;
        double yRightWrist = yRightElbow + addY;
        double xLeftShoulder = xHead - addX;
        double yLeftShoulder = yHead - addY;
        double xLeftElbow = xLeftShoulder - addX;
        double yLeftElbow = yLeftShoulder - addY;
        double xLeftWrist = xLeftElbow - addX;
        double yLeftWrist = yLeftElbow - addY;
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
