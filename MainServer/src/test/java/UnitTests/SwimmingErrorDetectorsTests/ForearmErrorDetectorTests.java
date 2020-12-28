package UnitTests.SwimmingErrorDetectorsTests;

import Domain.SwimmingData.Errors.LeftForearmError;
import Domain.SwimmingData.Errors.RightForearmError;
import Domain.SwimmingData.SwimmingError;
import Domain.SwimmingData.SwimmingSkeleton;
import junit.framework.TestCase;
import mainServer.SwimmingErrorDetectors.ForearmErrorDetector;
import org.junit.Before;

import java.util.LinkedList;
import java.util.List;

public class ForearmErrorDetectorTests extends TestCase {

    private final double LEFT_MIN_ANGLE = -10;
    private final double LEFT_MAX_ANGLE = 45;
    private final double RIGHT_MIN_ANGLE = -45;
    private final double RIGHT_MAX_ANGLE = 10;

    private ForearmErrorDetector detector;
    private SwimmingSkeleton skeletonNoError;
    private SwimmingSkeleton skeletonRightErrorMin;
    private SwimmingSkeleton skeletonRightErrorMax;
    private SwimmingSkeleton skeletonLeftErrorMin;
    private SwimmingSkeleton skeletonLeftErrorMax;
    private SwimmingSkeleton skeletonMinRightMinLeftErrors;
    private SwimmingSkeleton skeletonMinRightMaxLeftErrors;
    private SwimmingSkeleton skeletonMaxRightMinLeftErrors;
    private SwimmingSkeleton skeletonMaxRightMaxLeftErrors;

    private void addPointToList(List<Double> list, double x, double y, double confidence) {
        list.add(x);
        list.add(y);
        list.add(confidence);
    }

    private void setUpNoErrorSkeleton() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.5, 1); //Left Wrist
        this.skeletonNoError = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonRightErrorMin() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 2.5, 1, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.5, 1); //Left Wrist
        this.skeletonRightErrorMin = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonRightErrorMax() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.8, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.5, 1); //Left Wr
        this.skeletonRightErrorMax = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonRightError() {
        setUpSkeletonRightErrorMin();
        setUpSkeletonRightErrorMax();
    }

    private void setUpSkeletonLeftErrorMin() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1.2, 1, 1); //Left Elbow
        addPointToList(points, 0.9, 1.5, 1); //Left Wr
        this.skeletonLeftErrorMin = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonLeftErrorMax() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1.5, 1, 1); //Left Wrist
        this.skeletonLeftErrorMax = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonLeftError() {
        setUpSkeletonLeftErrorMin();
        setUpSkeletonLeftErrorMax();
    }

    private void setUpSkeletonMinRightMinLeftErrors() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 2.5, 1, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1.2, 1, 1); //Left Elbow
        addPointToList(points, 0.9, 1.5, 1); //Left Wrist
        this.skeletonMinRightMinLeftErrors = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonMinRightMaxLeftErrors() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 2.5, 1, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1.5, 1, 1); //Left Wrist
        this.skeletonMinRightMaxLeftErrors = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonMaxRightMinLeftErrors() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.8, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1.2, 1, 1); //Left Elbow
        addPointToList(points, 0.9, 1.5, 1); //Left Wrist
        this.skeletonMaxRightMinLeftErrors = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonMaxRightMaxLeftErrors() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.8, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1.5, 1, 1); //Left Wrist
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
        this.detector = new ForearmErrorDetector();
        setUpNoErrorSkeleton();
        setUpSkeletonRightError();
        setUpSkeletonLeftError();
        setUpSkeletonRightLeftErrors();
    }


    public void testDetectNoError() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonNoError);
        assertTrue(errors.isEmpty());
    }

    public void testDetectRightErrorMin() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonRightErrorMin);
        assertEquals(1, errors.size());
        SwimmingError error = errors.get(0);
        assertTrue(error instanceof RightForearmError);
        RightForearmError right = (RightForearmError) error;
        assertTrue(right.getAngle() < RIGHT_MIN_ANGLE);
    }

    public void testDetectRightErrorMax() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonRightErrorMax);
        assertEquals(1, errors.size());
        SwimmingError error = errors.get(0);
        assertTrue(error instanceof RightForearmError);
        RightForearmError right = (RightForearmError) error;
        assertTrue(right.getAngle() > RIGHT_MAX_ANGLE);
    }

    public void testDetectLeftErrorMin() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonLeftErrorMin);
        assertEquals(1, errors.size());
        SwimmingError error = errors.get(0);
        assertTrue(error instanceof LeftForearmError);
        LeftForearmError left = (LeftForearmError) error;
        assertTrue(left.getAngle() < LEFT_MIN_ANGLE);
    }

    public void testDetectLeftErrorMax() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonLeftErrorMax);
        assertEquals(1, errors.size());
        SwimmingError error = errors.get(0);
        assertTrue(error instanceof LeftForearmError);
        LeftForearmError left = (LeftForearmError) error;
        assertTrue(left.getAngle() > LEFT_MAX_ANGLE);
    }

    public void testDetectMinRightMinLeftErrors() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonMinRightMinLeftErrors);
        assertEquals(2, errors.size());
        // right side
        SwimmingError errorRight = errors.get(0);
        assertTrue(errorRight instanceof RightForearmError);
        RightForearmError right = (RightForearmError) errorRight;
        assertTrue(right.getAngle() < RIGHT_MIN_ANGLE);
        // left side
        SwimmingError errorLeft = errors.get(1);
        assertTrue(errorLeft instanceof LeftForearmError);
        LeftForearmError left = (LeftForearmError) errorLeft;
        assertTrue(left.getAngle() < LEFT_MIN_ANGLE);
    }

    public void testDetectMinRightMaxLeftErrors() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonMinRightMaxLeftErrors);
        assertEquals(2, errors.size());
        // right side
        SwimmingError errorRight = errors.get(0);
        assertTrue(errorRight instanceof RightForearmError);
        RightForearmError right = (RightForearmError) errorRight;
        assertTrue(right.getAngle() < RIGHT_MIN_ANGLE);
        // left side
        SwimmingError errorLeft = errors.get(1);
        assertTrue(errorLeft instanceof LeftForearmError);
        LeftForearmError left = (LeftForearmError) errorLeft;
        assertTrue(left.getAngle() > LEFT_MAX_ANGLE);
    }

    public void testDetectMaxRightMinLeftErrors() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonMaxRightMinLeftErrors);
        assertEquals(2, errors.size());
        // right side
        SwimmingError errorRight = errors.get(0);
        assertTrue(errorRight instanceof RightForearmError);
        RightForearmError right = (RightForearmError) errorRight;
        assertTrue(right.getAngle() > RIGHT_MAX_ANGLE);
        // left side
        SwimmingError errorLeft = errors.get(1);
        assertTrue(errorLeft instanceof LeftForearmError);
        LeftForearmError left = (LeftForearmError) errorLeft;
        assertTrue(left.getAngle() < LEFT_MIN_ANGLE);
    }

    public void testDetectMaxRightMaxLeftErrors() {
        List<SwimmingError> errors = this.detector.detect(this.skeletonMaxRightMaxLeftErrors);
        assertEquals(2, errors.size());
        // right side
        SwimmingError errorRight = errors.get(0);
        assertTrue(errorRight instanceof RightForearmError);
        RightForearmError right = (RightForearmError) errorRight;
        assertTrue(right.getAngle() > RIGHT_MAX_ANGLE);
        // left side
        SwimmingError errorLeft = errors.get(1);
        assertTrue(errorLeft instanceof LeftForearmError);
        LeftForearmError left = (LeftForearmError) errorLeft;
        assertTrue(left.getAngle() > LEFT_MAX_ANGLE);
    }

}
