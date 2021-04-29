package UnitTests.InterpolationTests;

import Domain.Points.IPoint;
import Domain.Points.IPointUtils;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import DomainLogic.Interpolations.LinearInterpolation;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.LinkedList;
import java.util.List;

public class LinearInterpolationTests extends TestCase {

    private List<IPoint> pointListAfter;
    private List<IPoint> pointListBefore;
    private LinearInterpolation linearInterpolation;

    @BeforeClass
    public void setUp() {
        pointListAfter = new LinkedList<>();
        pointListBefore = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            IPoint point = new SkeletonPoint(i, i);
            pointListAfter.add(point);
            if (i % 2 == 0) {
                pointListBefore.add(point);
            }
            else {
                pointListBefore.add(null);
            }
        }
        linearInterpolation = new LinearInterpolation();
    }

    @AfterClass
    public void tearDown() {

    }

    public void testInterpolation() {
        List<IPoint> after = linearInterpolation.interpolate(pointListBefore);
        for (int i = 0; i < after.size(); i++) {
            assertEquals(after.get(i).getX(), pointListAfter.get(i).getX());
            assertEquals(after.get(i).getY(), pointListAfter.get(i).getY());
        }
    }

}
