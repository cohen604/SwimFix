package UnitTests.SkeletonTesting.SkeletonFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonFilters.RightElbowFilter;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class RightElbowFiltersTests extends TestCase {

    private RightElbowFilter RightElbowFilter;
    private ISwimmingSkeleton swimmingSkeleton;

    @BeforeClass
    public void setUp() {
        RightElbowFilter = new RightElbowFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
    }

    public void testCheckContainValue() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(true);
        assertTrue(RightElbowFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainValue() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(false);
        assertFalse(RightElbowFilter.check(swimmingSkeleton));
    }

    public void testCheckWithNull() {
        assertFalse(RightElbowFilter.check(null));
    }

    public void testFilterWithNull() {
        assertNull(RightElbowFilter.filter(null));
    }

    public void testFilter() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(true);
        assertNotNull(RightElbowFilter.filter(swimmingSkeleton));
    }
}
