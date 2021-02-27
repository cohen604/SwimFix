package UnitTests.SwimmingTests;

import Domain.SwimmingData.SwimmingSkeletonGraph.SkeletonPoint;
import junit.framework.TestCase;

public class SkeletonPointGraphTests extends TestCase {

    public void testIsConfident() {
        SkeletonPoint a = new SkeletonPoint(1,1, 1);
        assertTrue(a.isConfident());
        SkeletonPoint b = new SkeletonPoint(1,1, 0.0001);
        assertFalse(b.isConfident());
    }

}
