package UnitTests.SkeletonTesting.SkeletonValueFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import DomainLogic.SkeletonsValueFilters.LeftWristFilters.XLeftWristFilter;
import DomainLogic.SkeletonsValueFilters.LeftWristFilters.YLeftWristFilter;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class LeftWristValueFilterTests extends TestCase {

    private XLeftWristFilter xLeftWristFilter;
    private YLeftWristFilter yLeftWristFilter;
    private ISwimmingSkeleton swimmingSkeleton;
    private double x;
    private double y;

    @BeforeClass
    public void setUp() {
        xLeftWristFilter = new XLeftWristFilter();
        yLeftWristFilter = new YLeftWristFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
        x = 5;
        y = 10;
    }

    @AfterClass
    public void tearDown() {
    }

    public void testFilterXValueWhenExist() {
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(true);
        when(swimmingSkeleton.getLeftWrist()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(x, xLeftWristFilter.filter(swimmingSkeleton));
    }

    public void testFilterXValueWhenDoesntExist() {
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(false);
        assertEquals(0.0, xLeftWristFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenExist() {
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(true);
        when(swimmingSkeleton.getLeftWrist()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(y, yLeftWristFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenDoesntExist() {
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(false);
        assertEquals(0.0, yLeftWristFilter.filter(swimmingSkeleton));
    }

}



