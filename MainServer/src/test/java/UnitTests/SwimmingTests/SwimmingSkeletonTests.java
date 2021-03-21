package UnitTests.SwimmingTests;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import java.util.List;

public abstract class SwimmingSkeletonTests extends TestCase {

    protected ISwimmingSkeleton fullSwimmingSkeleton;
    protected ISwimmingSkeleton emptySwimmingSkeleton;

    @Before
    public void setUp() {
        this.fullSwimmingSkeleton = getFullSwimmingSkeleton();
        this.emptySwimmingSkeleton = getEmptySwimmingSkeleton();
    }

    public abstract ISwimmingSkeleton getFullSwimmingSkeleton();

    public abstract ISwimmingSkeleton getEmptySwimmingSkeleton();

    public void testGetLinesFull() {
        //Arrange
        //Act
        List<Pair<IPoint, IPoint>> lines = this.fullSwimmingSkeleton.getLines();
        //Assert
        assertEquals(6 ,lines.size());
    }

    public void testGetLinesEmpty() {
        //Arrange
        //Act
        List<Pair<IPoint, IPoint>> lines = this.emptySwimmingSkeleton.getLines();
        //Assert
        assertEquals(0 ,lines.size());

    }

    public void testGetLinesWithHeadConfidenceLow() {
        List<Pair<IPoint, IPoint>> lines = this.emptySwimmingSkeleton.getLines();
        assertEquals(0 ,lines.size());
        assertFalse(this.emptySwimmingSkeleton.containsHead());
    }


    //TODO add 28 tests for contains Element and getElement

    //HEAD

    public void testContainsHead() {
        //Arrange
        //Act
        boolean hasHead = this.fullSwimmingSkeleton.containsHead();
        //Assert
        Assert.assertTrue(hasHead);
    }

    public void testContainsNoHead() {
        //Arrange
        //Act
        boolean hasHead = this.emptySwimmingSkeleton.containsHead();
        //Assert
        Assert.assertFalse(hasHead);
    }

    public void testGetHead() {
        //Arrange
        //Act
        IPoint head = this.fullSwimmingSkeleton.getHead();
        //Assert
        Assert.assertNotNull(head);
    }

    public void testGetNoHead() {
        //Arrange
        //Act
        IPoint head = this.emptySwimmingSkeleton.getHead();
        //Assert
        Assert.assertNull(head);
    }

    //RIGHT SHOULDER

    public void testContainsRightShoulder() {
        //Arrange
        //Act
        boolean hasRightShoulder = this.fullSwimmingSkeleton.containsRightShoulder();
        //Assert
        Assert.assertTrue(hasRightShoulder);
    }

    public void testContainsNoRightShoulder() {
        //Arrange
        //Act
        boolean hasRightShoulder = this.emptySwimmingSkeleton.containsRightShoulder();
        //Assert
        Assert.assertFalse(hasRightShoulder);
    }

    public void testGetRightShoulder() {
        //Arrange
        //Act
        IPoint rightShoulder = this.fullSwimmingSkeleton.getRightShoulder();
        //Assert
        Assert.assertNotNull(rightShoulder);
    }

    public void testGetNoRightShoulder() {
        //Arrange
        //Act
        IPoint rightShoulder = this.emptySwimmingSkeleton.getRightShoulder();
        //Assert
        Assert.assertNull(rightShoulder);
    }

    //RIGHT ELBOW
    public void testContainsRightElbow() {
        //Arrange
        //Act
        boolean hasRightElbow = this.fullSwimmingSkeleton.containsLeftElbow();
        //Assert
        Assert.assertTrue(hasRightElbow);
    }

    public void testContainsNoRightElbow() {
        //Arrange
        //Act
        boolean hasRightElbow = this.emptySwimmingSkeleton.containsLeftElbow();
        //Assert
        Assert.assertFalse(hasRightElbow);
    }

    public void testGetRightElbow() {
        //Arrange
        //Act
        IPoint rightElbow = this.fullSwimmingSkeleton.getRightElbow();
        //Assert
        Assert.assertNotNull(rightElbow);
    }

    public void testGetNoRightElbow() {
        //Arrange
        //Act
        IPoint rightElbow = this.emptySwimmingSkeleton.getRightElbow();
        //Assert
        Assert.assertNull(rightElbow);
    }

    //RIGHT WRIST

    public void testContainsRightWrist() {
        //Arrange
        //Act
        boolean hasRightWrist = this.fullSwimmingSkeleton.containsRightWrist();
        //Assert
        Assert.assertTrue(hasRightWrist);
    }

    public void testContainsNoRightWrist() {
        //Arrange
        //Act
        boolean hasRightWrist = this.emptySwimmingSkeleton.containsRightWrist();
        //Assert
        Assert.assertFalse(hasRightWrist);
    }

    public void testGetRightWrist() {
        //Arrange
        //Act
        IPoint rightWrist = this.fullSwimmingSkeleton.getRightWrist();
        //Assert
        Assert.assertNotNull(rightWrist);
    }

    public void testGetNoRightWrist() {
        //Arrange
        //Act
        IPoint rightWrist = this.emptySwimmingSkeleton.getRightWrist();
        //Assert
        Assert.assertNull(rightWrist);
    }

    //LEFT SHOULDER

    public void testContainsLeftShoulder() {
        //Arrange
        //Act
        boolean hasLeftShoulder = this.fullSwimmingSkeleton.containsLeftShoulder();
        //Assert
        Assert.assertTrue(hasLeftShoulder);
    }

    public void testContainsNoLeftShoulder() {
        //Arrange
        //Act
        boolean hasLeftShoulder = this.emptySwimmingSkeleton.containsLeftShoulder();
        //Assert
        Assert.assertFalse(hasLeftShoulder);
    }

    public void testGetLeftShoulder() {
        //Arrange
        //Act
        IPoint leftShoulder = this.fullSwimmingSkeleton.getLeftShoulder();
        //Assert
        Assert.assertNotNull(leftShoulder);
    }

    public void testGetNoLeftShoulder() {
        //Arrange
        //Act
        IPoint leftShoulder = this.emptySwimmingSkeleton.getLeftShoulder();
        //Assert
        Assert.assertNull(leftShoulder);
    }

    //RIGHT ELBOW

    public void testContainsLeftElbow() {
        //Arrange
        //Act
        boolean hasLeftElbow = this.fullSwimmingSkeleton.containsLeftElbow();
        //Assert
        Assert.assertTrue(hasLeftElbow);
    }

    public void testContainsNoLeftElbow() {
        //Arrange
        //Act
        boolean hasLeftElbow = this.emptySwimmingSkeleton.containsLeftElbow();
        //Assert
        Assert.assertFalse(hasLeftElbow);
    }

    public void testGetLeftElbow() {
        //Arrange
        //Act
        IPoint leftElbow = this.fullSwimmingSkeleton.getLeftElbow();
        //Assert
        Assert.assertNotNull(leftElbow);
    }

    public void testGetNoLeftElbow() {
        //Arrange
        //Act
        IPoint leftElbow = this.emptySwimmingSkeleton.getLeftElbow();
        //Assert
        Assert.assertNull(leftElbow);
    }

    //LEFT WRIST

    public void testContainsLeftWrist() {
        //Arrange
        //Act
        boolean hasLeftWrist = this.fullSwimmingSkeleton.containsLeftWrist();
        //Assert
        Assert.assertTrue(hasLeftWrist);
    }

    public void testContainsNoLeftWrist() {
        //Arrange
        //Act
        boolean hasLeftWrist = this.emptySwimmingSkeleton.containsLeftWrist();
        //Assert
        Assert.assertFalse(hasLeftWrist);
    }

    public void testGetLeftWrist() {
        //Arrange
        //Act
        IPoint leftWrist = this.fullSwimmingSkeleton.getLeftWrist();
        //Assert
        Assert.assertNotNull(leftWrist);
    }

    public void testGetNoLeftWrist() {
        //Arrange
        //Act
        IPoint leftWrist = this.emptySwimmingSkeleton.getLeftWrist();
        //Assert
        Assert.assertNull(leftWrist);
    }

}
