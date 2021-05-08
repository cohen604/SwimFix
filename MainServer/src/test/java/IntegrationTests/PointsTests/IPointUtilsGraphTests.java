package IntegrationTests.PointsTests;

import Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph.SkeletonPoint;
import UnitTests.PointsTests.IPointUtilsTests;

public class IPointUtilsGraphTests  extends IPointUtilsTests {

    @Override
    protected void setUpPointA(double x, double y) {
        pointA = new SkeletonPoint(x, y, 1);
    }

    @Override
    protected void setUpPointB(double x, double y) {
        pointB = new SkeletonPoint(x, y, 1);
    }
}
