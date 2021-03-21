package UnitTests.SwimmingTests;

import Domain.SwimmingData.*;
import Domain.SwimmingData.SwimmingSkeletonGraph.SwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.LinkedList;
import java.util.List;

public class SwimmingSkeletonGraphTests extends SwimmingSkeletonTests {

    private ISwimmingSkeleton swimmingSkeleton;
    private ISwimmingSkeleton swimmingSkeletonNoHead;

    private void addPointToList(List<Double> list, double x, double y, double confidence) {
        list.add(x);
        list.add(y);
        list.add(confidence);
    }

    private void setUpSwimmingSkeleton() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.5, 1); //Left Wrist
        this.swimmingSkeleton = new SwimmingSkeleton(points);
    }

    private void setUpSwimmingSkeletonHeadLow() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 0.00001); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.5, 1); //Left Wrist
        this.swimmingSkeletonNoHead = new SwimmingSkeleton(points);
    }

    @Override
    public ISwimmingSkeleton getFullSwimmingSkeleton() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.5, 1); //Left Wrist
        return new SwimmingSkeleton(points);
    }

    @Override
    public ISwimmingSkeleton getEmptySwimmingSkeleton() {
        return new SwimmingSkeleton();
    }

    public void testContainsKey() {
        //Arrange
        setUpSwimmingSkeleton();
        setUpSwimmingSkeletonHeadLow();
        //Act
        //Assert
        assertFalse(this.swimmingSkeletonNoHead.containsHead());
        assertTrue(this.swimmingSkeleton.containsRightShoulder());
        assertTrue(this.swimmingSkeleton.containsLeftShoulder());
    }

}
