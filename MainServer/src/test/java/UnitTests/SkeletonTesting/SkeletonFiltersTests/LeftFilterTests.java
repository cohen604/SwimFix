package UnitTests.SkeletonTesting.SkeletonFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonFilters.LeftFilter;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class LeftFilterTests extends TestCase {

    private LeftFilter leftFilter;
    private ISwimmingSkeleton swimmingSkeleton;

    @BeforeClass
    public void setUp() {
        leftFilter = new LeftFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
    }

    public void testCheckContainValue() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(true);
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(true);
        assertTrue(leftFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainOnlyElbowValue() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(true);
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(false);
        assertFalse(leftFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainOnlyWristValue() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(false);
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(true);
        assertFalse(leftFilter.check(swimmingSkeleton));
    }

    public void testCheckWithNull() {
        assertFalse(leftFilter.check(null));
    }

    public void testFilterWithNull() {
        assertNull(leftFilter.filter(null));
    }

    public void testFilter() {
        when(swimmingSkeleton.containsLeftElbow()).thenReturn(true);
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(true);
        assertNotNull(leftFilter.filter(swimmingSkeleton));
    }
}

