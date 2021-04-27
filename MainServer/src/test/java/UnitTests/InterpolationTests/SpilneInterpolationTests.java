package UnitTests.InterpolationTests;

import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import DomainLogic.Interpolations.SplineInterpolation;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.LinkedList;
import java.util.List;


public class SpilneInterpolationTests extends TestCase {

    private List<IPoint> pointListAfter;
    private List<IPoint> pointListBefore;
    private SplineInterpolation splineInterpolation;

    @BeforeClass
    public void setUp() {
        pointListAfter = new LinkedList<>();
        pointListBefore = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            IPoint point = new SkeletonPoint(i, i * i);
            pointListAfter.add(point);
            if (i % 2 == 0) {
                pointListBefore.add(point);
            }
            else {
                pointListBefore.add(null);
            }
        }
        splineInterpolation = new SplineInterpolation();
    }

    @AfterClass
    public void tearDown() {

    }

    public void testInterpolation() {
        List<IPoint> after = splineInterpolation.interpolate(pointListBefore);
        for (int i = 0; i < after.size() - 1; i++) {
            assertEquals(after.get(i).getX(), pointListAfter.get(i).getX());
            assertEquals(after.get(i).getY(), pointListAfter.get(i).getY());
        }
    }

    public void testInterpolation2() {
        int k = 10;
        for (int i = 10; i > 0; i--) {
            IPoint point = new SkeletonPoint(k, i * i);
            k++;
            pointListAfter.add(point);
            if (i % 2 == 0) {
                pointListBefore.add(point);
            }
            else {
                pointListBefore.add(null);
            }
        }
        List<IPoint> after = splineInterpolation.interpolate(pointListBefore);
        for (int i = 0; i < after.size() - 1; i++) {
            assertEquals(after.get(i).getX(), pointListAfter.get(i).getX());
            assertEquals(after.get(i).getY(), pointListAfter.get(i).getY());
        }
    }

}
