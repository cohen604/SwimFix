package UnitTests.SkeletonTesting.SkeletonFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonFilters.RightFilter;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class RightFilterTests extends TestCase {

    private RightFilter rightFilter;
    private ISwimmingSkeleton swimmingSkeleton;

    @BeforeClass
    public void setUp() {
        rightFilter = new RightFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
    }

    public void testCheckContainValue() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(true);
        when(swimmingSkeleton.containsRightWrist()).thenReturn(true);
        assertTrue(rightFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainOnlyElbowValue() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(true);
        when(swimmingSkeleton.containsRightWrist()).thenReturn(false);
        assertFalse(rightFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainOnlyWristValue() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(false);
        when(swimmingSkeleton.containsRightWrist()).thenReturn(true);
        assertFalse(rightFilter.check(swimmingSkeleton));
    }

    public void testCheckWithNull() {
        assertFalse(rightFilter.check(null));
    }

    public void testFilterWithNull() {
        assertNull(rightFilter.filter(null));
    }

    public void testFilter() {
        when(swimmingSkeleton.containsRightElbow()).thenReturn(true);
        when(swimmingSkeleton.containsRightWrist()).thenReturn(true);
        assertNotNull(rightFilter.filter(swimmingSkeleton));
    }
}
