package UnitTests.SkeletonTesting.SkeletonFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonFilters.RightWristFilter;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class RightWristFiltersTests extends TestCase {

    private RightWristFilter RightWristFilter;
    private ISwimmingSkeleton swimmingSkeleton;

    @BeforeClass
    public void setUp() {
        RightWristFilter = new RightWristFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
    }

    public void testCheckContainValue() {
        when(swimmingSkeleton.containsRightWrist()).thenReturn(true);
        assertTrue(RightWristFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainValue() {
        when(swimmingSkeleton.containsRightWrist()).thenReturn(false);
        assertFalse(RightWristFilter.check(swimmingSkeleton));
    }

    public void testCheckWithNull() {
        assertFalse(RightWristFilter.check(null));
    }

    public void testFilterWithNull() {
        assertNull(RightWristFilter.filter(null));
    }

    public void testFilter() {
        when(swimmingSkeleton.containsRightWrist()).thenReturn(true);
        assertNotNull(RightWristFilter.filter(swimmingSkeleton));
    }
}
