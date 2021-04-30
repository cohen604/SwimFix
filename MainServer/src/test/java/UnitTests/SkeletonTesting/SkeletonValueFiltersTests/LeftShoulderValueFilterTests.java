package UnitTests.SkeletonTesting.SkeletonValueFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import DomainLogic.SkeletonsValueFilters.LeftShoulderFilters.XLeftShoulderFilter;
import DomainLogic.SkeletonsValueFilters.LeftShoulderFilters.YLeftShoulderFilter;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class LeftShoulderValueFilterTests extends TestCase {

    private XLeftShoulderFilter xLeftShoulderFilter;
    private YLeftShoulderFilter yLeftShoulderFilter;
    private ISwimmingSkeleton swimmingSkeleton;
    private double x;
    private double y;

    @BeforeClass
    public void setUp() {
        xLeftShoulderFilter = new XLeftShoulderFilter();
        yLeftShoulderFilter = new YLeftShoulderFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
        x = 5;
        y = 10;
    }

    @AfterClass
    public void tearDown() {
    }

    public void testFilterXValueWhenExist() {
        when(swimmingSkeleton.containsLeftShoulder()).thenReturn(true);
        when(swimmingSkeleton.getLeftShoulder()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(x, xLeftShoulderFilter.filter(swimmingSkeleton));
    }

    public void testFilterXValueWhenDoesntExist() {
        when(swimmingSkeleton.containsLeftShoulder()).thenReturn(false);
        assertEquals(0.0, xLeftShoulderFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenExist() {
        when(swimmingSkeleton.containsLeftShoulder()).thenReturn(true);
        when(swimmingSkeleton.getLeftShoulder()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(y, yLeftShoulderFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenDoesntExist() {
        when(swimmingSkeleton.containsLeftShoulder()).thenReturn(false);
        assertEquals(0.0, yLeftShoulderFilter.filter(swimmingSkeleton));
    }

}



