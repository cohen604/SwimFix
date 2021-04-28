package UnitTests.SkeletonTesting.SkeletonValueFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import DomainLogic.SkeletonsValueFilters.HeadFilters.XHeadFilter;
import DomainLogic.SkeletonsValueFilters.HeadFilters.YHeadFilter;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class HeadValueFilterTests extends TestCase {

    private XHeadFilter xHeadFilter;
    private YHeadFilter yHeadFilter;
    private ISwimmingSkeleton swimmingSkeleton;
    private double x;
    private double y;

    @BeforeClass
    public void setUp() {
        xHeadFilter = new XHeadFilter();
        yHeadFilter = new YHeadFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
        x = 5;
        y = 10;
    }

    @AfterClass
    public void tearDown() {
    }

    public void testFilterXValueWhenExist() {
        when(swimmingSkeleton.containsHead()).thenReturn(true);
        when(swimmingSkeleton.getHead()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(x, xHeadFilter.filter(swimmingSkeleton));
    }

    public void testFilterXValueWhenDoesntExist() {
        when(swimmingSkeleton.containsHead()).thenReturn(false);
        assertEquals(0.0, xHeadFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenExist() {
        when(swimmingSkeleton.containsHead()).thenReturn(true);
        when(swimmingSkeleton.getHead()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(y, yHeadFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenDoesntExist() {
        when(swimmingSkeleton.containsHead()).thenReturn(false);
        assertEquals(0.0, yHeadFilter.filter(swimmingSkeleton));
    }

}
