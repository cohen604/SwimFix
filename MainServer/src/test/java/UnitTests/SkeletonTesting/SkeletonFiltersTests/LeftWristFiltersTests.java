package UnitTests.SkeletonTesting.SkeletonFiltersTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonFilters.LeftWristFilter;
import junit.framework.TestCase;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

public class LeftWristFiltersTests extends TestCase {

    private LeftWristFilter leftWristFilter;
    private ISwimmingSkeleton swimmingSkeleton;

    @BeforeClass
    public void setUp() {
        leftWristFilter = new LeftWristFilter();
        swimmingSkeleton = mock(ISwimmingSkeleton.class);
    }

    public void testCheckContainValue() {
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(true);
        assertTrue(leftWristFilter.check(swimmingSkeleton));
    }

    public void testCheckNotContainValue() {
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(false);
        assertFalse(leftWristFilter.check(swimmingSkeleton));
    }

    public void testCheckWithNull() {
        assertFalse(leftWristFilter.check(null));
    }

    public void testFilterWithNull() {
        assertNull(leftWristFilter.filter(null));
    }

    public void testFilter() {
        when(swimmingSkeleton.containsLeftWrist()).thenReturn(true);
        assertNotNull(leftWristFilter.filter(swimmingSkeleton));
    }
}
