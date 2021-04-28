package UnitTests.SkeletonTesting.SkeletonFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonFilters.LeftElbowFilter;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class LeftElbowFiltersTests extends TestCase {

    private LeftElbowFilter leftElbowFilter;
    private ISwimmingSkeleton swimmingSkeleton;

    @BeforeClass
    public void setUp() {
        leftElbowFilter = new LeftElbowFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
    }

    public void testCheckContainValue() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(true);
        assertTrue(leftElbowFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainValue() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(false);
        assertFalse(leftElbowFilter.check(swimmingSkeleton));
    }

    public void testCheckWithNull() {
        assertFalse(leftElbowFilter.check(null));
    }

    public void testFilterWithNull() {
        assertNull(leftElbowFilter.filter(null));
    }

    public void testFilter() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(true);
        assertNotNull(leftElbowFilter.filter(swimmingSkeleton));
    }
}
