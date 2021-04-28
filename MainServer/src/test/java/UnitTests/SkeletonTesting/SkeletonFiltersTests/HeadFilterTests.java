package UnitTests.SkeletonTesting.SkeletonFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonFilters.HeadFilter;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class HeadFilterTests extends TestCase {

    private HeadFilter headFilter;
    private ISwimmingSkeleton swimmingSkeleton;

    @BeforeClass
    public void setUp() {
        headFilter = new HeadFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
    }

    public void testCheckContainValue() {
        when(swimmingSkeleton.containsHead()).thenReturn(true);
        assertTrue(headFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainValue() {
        when(swimmingSkeleton.containsHead()).thenReturn(false);
        assertFalse(headFilter.check(swimmingSkeleton));
    }

    public void testCheckWithNull() {
        assertFalse(headFilter.check(null));
    }

    public void testFilterWithNull() {
        assertNull(headFilter.filter(null));
    }

    public void testFilter() {
        when(swimmingSkeleton.containsHead()).thenReturn(true);
        assertNotNull(headFilter.filter(swimmingSkeleton));
    }
}
