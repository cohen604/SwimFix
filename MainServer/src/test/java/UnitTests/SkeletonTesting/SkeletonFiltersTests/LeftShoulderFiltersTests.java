package UnitTests.SkeletonTesting.SkeletonFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonFilters.LeftShoulderFilter;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class LeftShoulderFiltersTests extends TestCase {

    private LeftShoulderFilter leftShoulderFilter;
    private ISwimmingSkeleton swimmingSkeleton;

    @BeforeClass
    public void setUp() {
        leftShoulderFilter = new LeftShoulderFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
    }

    public void testCheckContainValue() {
        when(swimmingSkeleton.containsLeftShoulder()).thenReturn(true);
        assertTrue(leftShoulderFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainValue() {
        when(swimmingSkeleton.containsLeftShoulder()).thenReturn(false);
        assertFalse(leftShoulderFilter.check(swimmingSkeleton));
    }

    public void testCheckWithNull() {
        assertFalse(leftShoulderFilter.check(null));
    }

    public void testFilterWithNull() {
        assertNull(leftShoulderFilter.filter(null));
    }

    public void testFilter() {
        when(swimmingSkeleton.containsLeftShoulder()).thenReturn(true);
        assertNotNull(leftShoulderFilter.filter(swimmingSkeleton));
    }
}
