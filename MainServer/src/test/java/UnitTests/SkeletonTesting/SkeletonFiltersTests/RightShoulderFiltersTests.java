package UnitTests.SkeletonTesting.SkeletonFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonFilters.RightShoulderFilter;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class RightShoulderFiltersTests extends TestCase {

    private RightShoulderFilter RightShoulderFilter;
    private ISwimmingSkeleton swimmingSkeleton;

    @BeforeClass
    public void setUp() {
        RightShoulderFilter = new RightShoulderFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
    }

    public void testCheckContainValue() {
        when(swimmingSkeleton.containsRightShoulder()).thenReturn(true);
        assertTrue(RightShoulderFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainValue() {
        when(swimmingSkeleton.containsRightShoulder()).thenReturn(false);
        assertFalse(RightShoulderFilter.check(swimmingSkeleton));
    }

    public void testCheckWithNull() {
        assertFalse(RightShoulderFilter.check(null));
    }

    public void testFilterWithNull() {
        assertNull(RightShoulderFilter.filter(null));
    }

    public void testFilter() {
        when(swimmingSkeleton.containsRightShoulder()).thenReturn(true);
        assertNotNull(RightShoulderFilter.filter(swimmingSkeleton));
    }
}
