package UnitTests.SkeletonTesting.SkeletonValueFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import DomainLogic.SkeletonsValueFilters.RightWristFilters.XRightWristFilter;
import DomainLogic.SkeletonsValueFilters.RightWristFilters.YRightWristFilter;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class RightWristValueFilterTests extends TestCase {

    private XRightWristFilter xRightWristFilter;
    private YRightWristFilter yRightWristFilter;
    private ISwimmingSkeleton swimmingSkeleton;
    private double x;
    private double y;

    @BeforeClass
    public void setUp() {
        xRightWristFilter = new XRightWristFilter();
        yRightWristFilter = new YRightWristFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
        x = 5;
        y = 10;
    }

    @AfterClass
    public void tearDown() {
    }

    public void testFilterXValueWhenExist() {
        when(swimmingSkeleton.containsRightWrist()).thenReturn(true);
        when(swimmingSkeleton.getRightWrist()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(x, xRightWristFilter.filter(swimmingSkeleton));
    }

    public void testFilterXValueWhenDoesntExist() {
        when(swimmingSkeleton.containsRightWrist()).thenReturn(false);
        assertEquals(0.0, xRightWristFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenExist() {
        when(swimmingSkeleton.containsRightWrist()).thenReturn(true);
        when(swimmingSkeleton.getRightWrist()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(y, yRightWristFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenDoesntExist() {
        when(swimmingSkeleton.containsRightWrist()).thenReturn(false);
        assertEquals(0.0, yRightWristFilter.filter(swimmingSkeleton));
    }

}



