package UnitTests.SwimmingErrorDetectorsTests;

import Domain.SwimmingData.Errors.LeftElbowError;
import Domain.SwimmingData.Errors.RightElbowError;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import Domain.SwimmingData.SwimmingSkeletonGraph.SwimmingSkeleton;
import junit.framework.TestCase;
import DomainLogic.SwimmingErrorDetectors.*;
import org.junit.Before;

import java.util.LinkedList;
import java.util.List;

public class ElbowErrorDetectorTests extends TestCase {

    private final double MIN_ANGLE = 90;
    private final double MAX_ANGLE = 175;
    private ElbowErrorDetector elbowErrorDetector;
    private ISwimmingSkeleton skeletonNoError;
    private ISwimmingSkeleton skeletonRightErrorMin;
    private ISwimmingSkeleton skeletonRightErrorMax;
    private ISwimmingSkeleton skeletonLeftErrorMin;
    private ISwimmingSkeleton skeletonLeftErrorMax;
    private ISwimmingSkeleton skeletonMinRightMinLeftErrors;
    private ISwimmingSkeleton skeletonMinRightMaxLeftErrors;
    private ISwimmingSkeleton skeletonMaxRightMinLeftErrors;
    private ISwimmingSkeleton skeletonMaxRightMaxLeftErrors;


    private void addPointToList(List<Double> list, double x, double y, double confidence) {
        list.add(x);
        list.add(y);
        list.add(confidence);
    }

    private void setUpNoErrorSkeleton() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 1.5, 0.5, 1); //HEAD
        addPointToList(points, 2, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2.3, 1.5, 1); //Right Wrist
        addPointToList(points, 1, 0.5, 1); //Left Shoulder
        addPointToList(points, 0.5, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.6, 1); //Left Wrist
        this.skeletonNoError = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonRightErrorMin() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 1.5, 0.5, 1); //HEAD
        addPointToList(points, 2, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2, 1, 1); //Right Wrist
        addPointToList(points, 1, 0.5, 1); //Left Shoulder
        addPointToList(points, 0.5, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.6, 1); //Left Wrist
        this.skeletonRightErrorMin = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonRightErrorMax() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 1.5, 0.5, 1); //HEAD
        addPointToList(points, 2, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 3.5, 1.5, 1); //Right Wrist
        addPointToList(points, 1, 0.5, 1); //Left Shoulder
        addPointToList(points, 0.5, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.6, 1); //Left Wrist
        this.skeletonRightErrorMax = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonRightError() {
        setUpSkeletonRightErrorMin();
        setUpSkeletonRightErrorMax();
    }

    private void setUpSkeletonLeftErrorMin() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 1.5, 0.5, 1); //HEAD
        addPointToList(points, 2, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2.3, 1.5, 1); //Right Wrist
        addPointToList(points, 1, 0.5, 1); //Left Shoulder
        addPointToList(points, 0.5, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.15, 1); //Left Wrist
        this.skeletonLeftErrorMin = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonLeftErrorMax() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 1.5, 0.5, 1); //HEAD
        addPointToList(points, 2, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2.3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.3, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 0.05, 1.6, 1); //Left Wrist
        this.skeletonLeftErrorMax = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonLeftError() {
        setUpSkeletonLeftErrorMin();
        setUpSkeletonLeftErrorMax();
    }

    private void setUpSkeletonMinRightMinLeftErrors() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 1.5, 0.5, 1); //HEAD
        addPointToList(points, 2, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2, 1, 1); //Right Wrist
        addPointToList(points, 1, 0.5, 1); //Left Shoulder
        addPointToList(points, 0.5, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.15, 1); //Left Wrist
        this.skeletonMinRightMinLeftErrors = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonMinRightMaxLeftErrors() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 1.5, 0.5, 1); //HEAD
        addPointToList(points, 2, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2, 1, 1); //Right Wrist
        addPointToList(points, 1, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 0.05, 1.6, 1); //Left Wrist
        this.skeletonMinRightMaxLeftErrors = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonMaxRightMinLeftErrors() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 1.5, 0.5, 1); //HEAD
        addPointToList(points, 2, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1, 0.5, 1); //Left Shoulder
        addPointToList(points, 0.5, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.15, 1); //Left Wrist
        this.skeletonMaxRightMinLeftErrors = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonMaxRightMaxLeftErrors() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 1.5, 0.5, 1); //HEAD
        addPointToList(points, 2, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 0.05, 1.6, 1); //Left Wrist
        this.skeletonMaxRightMaxLeftErrors = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonRightLeftErrors() {
        setUpSkeletonMinRightMinLeftErrors();
        setUpSkeletonMinRightMaxLeftErrors();
        setUpSkeletonMaxRightMinLeftErrors();
        setUpSkeletonMaxRightMaxLeftErrors();
    }

    @Before
    public void setUp() {
        IFactoryDraw iFactoryDraw = new FactoryDraw();
        IFactoryElbowError iFactoryElbowError = new FactoryElbowError(iFactoryDraw);
        this.elbowErrorDetector = new ElbowErrorDetector(iFactoryElbowError,90, 175);
        setUpNoErrorSkeleton();
        setUpSkeletonRightError();
        setUpSkeletonLeftError();
        setUpSkeletonRightLeftErrors();
    }


    public void testDetectNoError() {
        List<SwimmingError> errors = this.elbowErrorDetector.detect(this.skeletonNoError);
        assertTrue(errors.isEmpty());
    }

    public void testDetectMinRightError() {
        List<SwimmingError> errors = this.elbowErrorDetector.detect(this.skeletonRightErrorMin);
        assertEquals(1, errors.size());
        SwimmingError error = errors.get(0);
        assertTrue(error instanceof RightElbowError);
        RightElbowError right = (RightElbowError)error;
        assertTrue(right.getAngle() < MIN_ANGLE);
    }

    public void testDetectMaxRightError() {
        List<SwimmingError> errors = this.elbowErrorDetector.detect(this.skeletonRightErrorMax);
        assertEquals(1, errors.size());
        SwimmingError error = errors.get(0);
        assertTrue(error instanceof RightElbowError);
        RightElbowError right = (RightElbowError)error;
        assertTrue(right.getAngle() > MAX_ANGLE);
    }

    public void testDetectMinLeftError() {
        List<SwimmingError> errors = this.elbowErrorDetector.detect(this.skeletonLeftErrorMin);
        assertEquals(1, errors.size());
        SwimmingError error = errors.get(0);
        assertTrue(error instanceof LeftElbowError);
        LeftElbowError right = (LeftElbowError)error;
        assertTrue(right.getAngle() < MIN_ANGLE);
    }

    public void testDetectMaxLeftError() {
        List<SwimmingError> errors = this.elbowErrorDetector.detect(this.skeletonLeftErrorMax);
        assertEquals(1, errors.size());
        SwimmingError error = errors.get(0);
        assertTrue(error instanceof LeftElbowError);
        LeftElbowError right = (LeftElbowError)error;
        assertTrue(right.getAngle() > MAX_ANGLE);
    }

    public void testDetectMinRightMinLeftErrors() {
        List<SwimmingError> errors = this.elbowErrorDetector.detect(this.skeletonMinRightMinLeftErrors);
        assertEquals(2, errors.size());
        // right side check
        SwimmingError errorRight = errors.get(0);
        assertTrue(errorRight instanceof RightElbowError);
        RightElbowError right = (RightElbowError)errorRight ;
        assertTrue(right.getAngle() < MIN_ANGLE);
        // left side check
        SwimmingError errorLeft = errors.get(1);
        assertTrue(errorLeft instanceof LeftElbowError);
        LeftElbowError left = (LeftElbowError)errorLeft;
        assertTrue(left.getAngle() < MIN_ANGLE);
    }

    public void testDetectMinRightMaxLeftErrors() {
        List<SwimmingError> errors = this.elbowErrorDetector.detect(this.skeletonMinRightMaxLeftErrors);
        assertEquals(2, errors.size());
        // right side check
        SwimmingError errorRight = errors.get(0);
        assertTrue(errorRight instanceof RightElbowError);
        RightElbowError right = (RightElbowError)errorRight ;
        assertTrue(right.getAngle() < MIN_ANGLE);
        // left side check
        SwimmingError errorLeft = errors.get(1);
        assertTrue(errorLeft instanceof LeftElbowError);
        LeftElbowError left = (LeftElbowError)errorLeft;
        assertTrue(left.getAngle() > MAX_ANGLE);
    }

    public void testDetectMaxRightMinLeftErrors() {
        List<SwimmingError> errors = this.elbowErrorDetector.detect(this.skeletonMaxRightMinLeftErrors);
        assertEquals(2, errors.size());
        // right side check
        SwimmingError errorRight = errors.get(0);
        assertTrue(errorRight instanceof RightElbowError);
        RightElbowError right = (RightElbowError)errorRight ;
        assertTrue(right.getAngle() > MAX_ANGLE);
        // left side check
        SwimmingError errorLeft = errors.get(1);
        assertTrue(errorLeft instanceof LeftElbowError);
        LeftElbowError left = (LeftElbowError)errorLeft;
        assertTrue(left.getAngle() < MIN_ANGLE);
    }

    public void testDetectMaxRightMaxLeftErrors() {
        List<SwimmingError> errors = this.elbowErrorDetector.detect(this.skeletonMaxRightMaxLeftErrors);
        assertEquals(2, errors.size());
        // right side check
        SwimmingError errorRight = errors.get(0);
        assertTrue(errorRight instanceof RightElbowError);
        RightElbowError right = (RightElbowError)errorRight ;
        assertTrue(right.getAngle() > MAX_ANGLE);
        // left side check
        SwimmingError errorLeft = errors.get(1);
        assertTrue(errorLeft instanceof LeftElbowError);
        LeftElbowError left = (LeftElbowError)errorLeft;
        assertTrue(left.getAngle() > MAX_ANGLE);
    }
}
