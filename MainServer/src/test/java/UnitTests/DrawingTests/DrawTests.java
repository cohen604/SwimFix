package UnitTests.DrawingTests;

import Domain.Drawing.Draw;
import Domain.Drawing.IDraw;
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
        double delta = 0.01;
        // Act
        _draw.drawCircle(frame, point, radius);
        // Assert
        for(int i=0; i<frame.height(); i++) {
            for(int j=0; j<frame.width(); j++) {
                double[] data = frame.get(i, j);
                if( (i == (int)point.getY() - radius && j == (int)point.getX())
                        || (i == (int)point.getY() + radius && j == (int)point.getX())
                        || (i == (int)point.getY() && j == (int)point.getX() - radius)
                        || (i == (int)point.getY() && j == (int)point.getX() + radius)) {
                    assertEquals(data.length, 3);
                    assertEquals(data[0], expectedB, delta);
                    assertEquals(data[1], expectedG, delta);
                    assertEquals(data[2], expectedR, delta);
                }
            }
        }
    }

    public void testDrawComplexCircle() {
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
        Iterator<Double> colorIterator = random.doubles(0, 255).iterator();
        double expectedB = colorIterator.next();
        double expectedG = colorIterator.next();
        double expectedR = colorIterator.next();
        double delta = 0.5;
        // Act
        _draw.drawCircle(frame, point, radius, 3, expectedR, expectedG, expectedB);
        // Assert
        for(int i=0; i<frame.height(); i++) {
            for(int j=0; j<frame.width(); j++) {
                double[] data = frame.get(i, j);
                if( (i == (int)point.getY() - radius && j == (int)point.getX())
                        || (i == (int)point.getY() + radius && j == (int)point.getX())
                        || (i == (int)point.getY() && j == (int)point.getX() - radius)
                        || (i == (int)point.getY() && j == (int)point.getX() + radius)) {
                    assertEquals(data.length, 3);
                    assertEquals(data[0], expectedB, delta);
                    assertEquals(data[1], expectedG, delta);
                    assertEquals(data[2], expectedR, delta);
                }
            }
        }
    }

    public void testDrawEclipse() {
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
        Iterator<Double> colorIterator = random.doubles(0, 255).iterator();
        double expectedB = colorIterator.next();
        double expectedG = colorIterator.next();
        double expectedR = colorIterator.next();
        double delta = 0.5;
        // Act
        _draw.drawEclipse(frame, radius, 0, 0, 360, point, expectedR, expectedG, expectedB, 1);
        // Assert
        for(int i=0; i<frame.height(); i++) {
            for(int j=0; j<frame.width(); j++) {
                double[] data = frame.get(i, j);
                if( (i == (int)point.getY() - radius && j == (int)point.getX())
                        || (i == (int)point.getY() + radius && j == (int)point.getX())
                        || (i == (int)point.getY() && j == (int)point.getX() - radius)
                        || (i == (int)point.getY() && j == (int)point.getX() + radius)) {
                    assertEquals(data.length, 3);
                    assertEquals(data[0], expectedB, delta);
                    assertEquals(data[1], expectedG, delta);
                    assertEquals(data[2], expectedR, delta);
                }
            }
        }
    }

    public void testDrawSimpleLine() {
        // Arrange
        Random random = new Random();
        Iterator<Integer> iterator = random.ints(10, 100)
                .iterator();
        int width = iterator.next();
        int height = iterator.next();
        Mat frame = setUpFrame(width, height);
        IPoint a = setUpRandomPoint(width, height);
        IPoint b = setUpRandomPoint(width, height);
        while(b.getX() < a.getX() || b.getY() < a.getY()) {
            b = setUpRandomPoint(width, height);
        }
        double expectedB = 255;
        double expectedG = 0;
        double expectedR = 0;
        double delta = 0.5;
        double slot = (b.getY() - a.getY()) / (b.getX() - a.getX());
        // Act
        _draw.drawLine(frame, a, b);
        // Assert
        for(int j=0; j<frame.width(); j++) {
            if( j >= a.getX() && j <= b.getX()) {
                double y = slot * (j - a.getX()) + a.getY();
                if(y>= a.getY() && y <= b.getY()) {
                    double[] data = frame.get((int)y, j);
                    assertEquals(data.length, 3);
                    assertEquals(data[0], expectedB, delta);
                    assertEquals(data[1], expectedG, delta);
                    assertEquals(data[2], expectedR, delta);
                }
            }
        }
    }

    public void testDrawComplexLine() {
        // Arrange
        Random random = new Random();
        Iterator<Integer> iterator = random.ints(10, 100)
                .iterator();
        int width = iterator.next();
        int height = iterator.next();
        Mat frame = setUpFrame(width, height);
        IPoint a = setUpRandomPoint(width, height);
        IPoint b = setUpRandomPoint(width, height);
        while(b.getX() < a.getX() || b.getY() < a.getY()) {
            b = setUpRandomPoint(width, height);
        }
        Iterator<Double> colorIterator = random.doubles(0, 255).iterator();
        double expectedB = colorIterator.next();
        double expectedG = colorIterator.next();
        double expectedR = colorIterator.next();
        double delta = 0.5;
        double slot = (b.getY() - a.getY()) / (b.getX() - a.getX());
        // Act
        _draw.drawLine(frame, a, b, expectedR, expectedG, expectedB, 1, 3);
        // Assert
        for(int j=0; j<frame.width(); j++) {
            if( j >= a.getX() && j <= b.getX()) {
                double y = slot * (j - a.getX()) + a.getY();
                if(y>= a.getY() && y <= b.getY()) {
                    double[] data = frame.get((int)y, j);
                    assertEquals(data.length, 3);
                    assertEquals(data[0], expectedB, delta);
                    assertEquals(data[1], expectedG, delta);
                    assertEquals(data[2], expectedR, delta);
                }
            }
        }
    }

    public void testDrawSwimmer() {
        // Arrange
        Random random = new Random();
        Iterator<Integer> iterator = random.ints(10, 100)
                .iterator();
        int width = iterator.next();
        int height = iterator.next();
        Mat frame = setUpFrame(width, height);
        ISwimmingSkeleton skeleton = setUpSwimmingSkeleton(width, height);
        double expectedB = 255;
        double expectedG = 0;
        double expectedR = 0;
        // Act
        _draw.drawSwimmer(frame, skeleton);
        // Assert
        checkIPoint(frame, skeleton.getHead(), expectedB, expectedG, expectedR);
        checkIPoint(frame, skeleton.getRightShoulder(), expectedB, expectedG, expectedR);
        checkIPoint(frame, skeleton.getRightElbow(), expectedB, expectedG, expectedR);
        checkIPoint(frame, skeleton.getRightWrist(), expectedB, expectedG, expectedR);
        checkIPoint(frame, skeleton.getLeftShoulder(), expectedB, expectedG, expectedR);
        checkIPoint(frame, skeleton.getLeftElbow(), expectedB, expectedG, expectedR);
        checkIPoint(frame, skeleton.getLeftWrist(), expectedB, expectedG, expectedR);
    }

    private void checkIPoint(Mat frame,
                             IPoint point,
                             double expectedB,
                             double expectedG,
                             double expectedR) {
        double delta = 0.5;
        int x = (int)point.getX();
        int y = (int)point.getY();
        double[] data = frame.get(y, x);
        assertEquals(data.length, 3);
        assertEquals(data[0], expectedB, delta);
        assertEquals(data[1], expectedG, delta);
        assertEquals(data[2], expectedR, delta);
    }

    public void testDrawLogo() {
        // Arrange
        int width = 20;
        int height = 40;
        Mat frame = setUpFrame(width, height, 255, 255, 255);
        int expectedWhite = 85;
        // Act
        _draw.drawLogo(frame);
        // Assert
        int resultWhite = 0;
        for(int i=0; i<frame.height(); i++) {
            for(int j=0; j<frame.width(); j++) {
                double[] data = frame.get(i, j);
                if(data[0] == 0) {
                    resultWhite++;
                }
            }
        }
        assertEquals(expectedWhite, resultWhite);
    }

    public void testDrawMessage() {
        // Arrange
        int width = 20;
        int height = 40;
        Mat frame = setUpFrame(width, height, 255, 255, 255);
        String logo = "Swim Analytics";
        double x = 10;
        double y = 30;
        int expectedWhite = 81;
        // Act
        _draw.drawMessage(frame, logo, x, y, 2);
        // Assert
        int resultWhite = 0;
        for(int i=0; i<frame.height(); i++) {
            for(int j=0; j<frame.width(); j++) {
                double[] data = frame.get(i, j);
                if(data[0] == 0) {
                    resultWhite++;
                }
            }
        }
        assertEquals(expectedWhite, resultWhite);
    }

    private Mat setUpFrame(int width, int height) {
        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        mat.setTo(new Scalar(0,0,0));
        return mat;
    }


    private Mat setUpFrame(int width, int height, int b, int g, int r) {
        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        mat.setTo(new Scalar(b,g,r));
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

    private ISwimmingSkeleton setUpSwimmingSkeleton(double width, double height) {
        Random random = new Random();
        Iterator<Double> widthIterator = random.doubles(width / 3, width / 2)
                .iterator();
        Iterator<Double> heightIterator = random.doubles(height / 3, height / 2)
                .iterator();
        double addX = 5;
        double addY = 5;
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
