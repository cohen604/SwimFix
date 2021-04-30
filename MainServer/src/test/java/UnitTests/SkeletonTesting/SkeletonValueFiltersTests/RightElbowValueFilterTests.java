package UnitTests.SkeletonTesting.SkeletonValueFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import DomainLogic.SkeletonsValueFilters.RightElbowFilters.XRightElbowFilter;
import DomainLogic.SkeletonsValueFilters.RightElbowFilters.YRightElbowFilter;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class RightElbowValueFilterTests extends TestCase {

    private XRightElbowFilter xRightElbowFilter;
    private YRightElbowFilter yRightElbowFilter;
    private ISwimmingSkeleton swimmingSkeleton;
    private double x;
    private double y;

    @BeforeClass
    public void setUp() {
        xRightElbowFilter = new XRightElbowFilter();
        yRightElbowFilter = new YRightElbowFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
        x = 5;
        y = 10;
    }

    @AfterClass
    public void tearDown() {
    }

    public void testFilterXValueWhenExist() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(true);
        when(swimmingSkeleton.getRightElbow()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(x, xRightElbowFilter.filter(swimmingSkeleton));
    }

    public void testFilterXValueWhenDoesntExist() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(false);
        assertEquals(0.0, xRightElbowFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenExist() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(true);
        when(swimmingSkeleton.getRightElbow()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(y, yRightElbowFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenDoesntExist() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(false);
        assertEquals(0.0, yRightElbowFilter.filter(swimmingSkeleton));
    }

}


