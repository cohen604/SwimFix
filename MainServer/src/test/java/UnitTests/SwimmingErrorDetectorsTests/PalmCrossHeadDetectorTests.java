package UnitTests.SwimmingErrorDetectorsTests;

import Domain.SwimmingData.Errors.LeftPalmCrossHeadError;
import Domain.SwimmingData.Errors.RightPalmCrossHeadError;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import Domain.SwimmingData.SwimmingSkeletonGraph.SwimmingSkeleton;
import junit.framework.TestCase;
import mainServer.SwimmingErrorDetectors.*;
import org.junit.Before;

import java.util.LinkedList;
import java.util.List;

public class PalmCrossHeadDetectorTests extends TestCase {

    private PalmCrossHeadDetector detector;
    private ISwimmingSkeleton skeletonNoError;
    private ISwimmingSkeleton skeletonRightError;
    private ISwimmingSkeleton skeletonLeftError;
    private ISwimmingSkeleton skeletonRightLeftError;


    private void addPointToList(List<Double> list, double x, double y, double confidence) {
        list.add(x);
        list.add(y);
        list.add(confidence);
    }

    private void setUpSkeletonNoError() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2.5, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1.5, 1, 1); //Left Elbow
        addPointToList(points, 1.5, 1.5, 1); //Left Wrist
        this.skeletonNoError = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonRightError() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2.0, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1.5, 1, 1); //Left Elbow
        addPointToList(points, 1.5, 1.5, 1); //Left Wrist
        this.skeletonRightError = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonLeftError() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2.5, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1.5, 1, 1); //Left Elbow
        addPointToList(points, 2.0, 1.5, 1); //Left Wrist
        this.skeletonLeftError = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonRightLeftError() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2.0, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1.5, 1, 1); //Left Elbow
        addPointToList(points, 2.0, 1.5, 1); //Left Wrist
        this.skeletonRightLeftError = new SwimmingSkeleton(points);
    }

    @Before
    public void setUp(){
        IFactoryDraw iFactoryDraw = new FactoryDraw();
        IFactoryPalmCrossHeadError iFactoryPalmCrossHeadError = new FactoryPalmCrossHeadError(iFactoryDraw);
        this.detector = new PalmCrossHeadDetector(iFactoryPalmCrossHeadError);
        setUpSkeletonNoError();
        setUpSkeletonRightError();
        setUpSkeletonLeftError();
        setUpSkeletonRightLeftError();
    }

    public void testDetectNoError() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonNoError);
        assertTrue(errors.isEmpty());
    }

    public void testDetectRightError() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonRightError);
        assertEquals(1, errors.size());
        SwimmingError error = errors.get(0);
        assertTrue(error instanceof RightPalmCrossHeadError);
    }

    public void testDetectLeftError() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonLeftError);
        assertEquals(1, errors.size());
        SwimmingError error = errors.get(0);
        assertTrue(error instanceof LeftPalmCrossHeadError);
    }

    public void testDetectRightLeftErrors() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonRightLeftError);
        assertEquals(2, errors.size());
        // right side
        SwimmingError errorRight = errors.get(0);
        assertTrue(errorRight instanceof RightPalmCrossHeadError);
        // left side
        SwimmingError errorLeft = errors.get(1);
        assertTrue(errorLeft instanceof LeftPalmCrossHeadError);
    }

}
