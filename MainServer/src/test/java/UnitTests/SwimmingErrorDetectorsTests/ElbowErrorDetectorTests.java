package UnitTests.SwimmingErrorDetectorsTests;

import Domain.SwimmingData.SwimmingSkeleton;
import junit.framework.TestCase;
import mainServer.SwimmingErrorDetectors.ElbowErrorDetector;
import org.junit.Before;

import java.util.LinkedList;
import java.util.List;

public class ElbowErrorDetectorTests extends TestCase {


    private ElbowErrorDetector elbowErrorDetector;
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
        addPointToList(points, 0, 0, 1); //HEAD
        addPointToList(points, 0, 0, 1); //Right Shoulder
        addPointToList(points, 0, 0, 1); //Right Elbow
        addPointToList(points, 0, 0, 1); //Right Wrist
        addPointToList(points, 0, 0, 1); //Left Shoulder
        addPointToList(points, 0, 0, 1); //Left Elbow
        addPointToList(points, 0, 0, 1); //Left Wrist
        this.skeletonNoError = new SwimmingSkeleton(points);
    }

    private void setUpSkeletonRightError() {

    }

    private void setUpSkeletonLeftError() {

    }

    private void setUpSkeletonRightLeftErrors() {

    }



    @Before
    public void setUp() {
        this.elbowErrorDetector = new ElbowErrorDetector(90, 175);
        setUpNoErrorSkeleton();
        setUpSkeletonRightError();
        setUpSkeletonLeftError();
        setUpSkeletonRightLeftErrors();
    }



    public void testDetectRightError() {
        //TODO
    }

    public void testDetectLeftError() {
        //TODO
    }

    public void testDetectRightLeftErrors() {
        //TODO
    }

    public void testDetectNoError() {
        //TODO
    }
}
