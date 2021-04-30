package UnitTests.SkeletonTesting.SkeletonValueFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import DomainLogic.SkeletonsValueFilters.RightShoulderFilters.XRightShoulderFilter;
import DomainLogic.SkeletonsValueFilters.RightShoulderFilters.YRightShoulderFilter;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class RightShoulderValueFilterTests extends TestCase {

    private XRightShoulderFilter xRightShoulderFilter;
    private YRightShoulderFilter yRightShoulderFilter;
    private ISwimmingSkeleton swimmingSkeleton;
    private double x;
    private double y;

    @BeforeClass
    public void setUp() {
        xRightShoulderFilter = new XRightShoulderFilter();
        yRightShoulderFilter = new YRightShoulderFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
        x = 5;
        y = 10;
    }

    @AfterClass
    public void tearDown() {
    }

    public void testFilterXValueWhenExist() {
        when(swimmingSkeleton.containsRightShoulder()).thenReturn(true);
        when(swimmingSkeleton.getRightShoulder()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(x, xRightShoulderFilter.filter(swimmingSkeleton));
    }

    public void testFilterXValueWhenDoesntExist() {
        when(swimmingSkeleton.containsRightShoulder()).thenReturn(false);
        assertEquals(0.0, xRightShoulderFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenExist() {
        when(swimmingSkeleton.containsRightShoulder()).thenReturn(true);
        when(swimmingSkeleton.getRightShoulder()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(y, yRightShoulderFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenDoesntExist() {
        when(swimmingSkeleton.containsRightShoulder()).thenReturn(false);
        assertEquals(0.0, yRightShoulderFilter.filter(swimmingSkeleton));
    }

}



