package UnitTests.SkeletonTesting.SkeletonValueFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import DomainLogic.SkeletonsValueFilters.LeftElbowFilters.XLeftElbowFilter;
import DomainLogic.SkeletonsValueFilters.LeftElbowFilters.YLeftElbowFilter;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class LeftElbowValueFilterTests extends TestCase {

    private XLeftElbowFilter xLeftElbowFilter;
    private YLeftElbowFilter yLeftElbowFilter;
    private ISwimmingSkeleton swimmingSkeleton;
    private double x;
    private double y;

    @BeforeClass
    public void setUp() {
        xLeftElbowFilter = new XLeftElbowFilter();
        yLeftElbowFilter = new YLeftElbowFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
        x = 5;
        y = 10;
    }

    @AfterClass
    public void tearDown() {
    }

    public void testFilterXValueWhenExist() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(true);
        when(swimmingSkeleton.getLeftElbow()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(x, xLeftElbowFilter.filter(swimmingSkeleton));
    }

    public void testFilterXValueWhenDoesntExist() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(false);
        assertEquals(0.0, xLeftElbowFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenExist() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(true);
        when(swimmingSkeleton.getLeftElbow()).thenReturn(new SkeletonPoint(x, y));
        assertEquals(y, yLeftElbowFilter.filter(swimmingSkeleton));
    }

    public void testFilterYValueWhenDoesntExist() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(false);
        assertEquals(0.0, yLeftElbowFilter.filter(swimmingSkeleton));
    }

}


