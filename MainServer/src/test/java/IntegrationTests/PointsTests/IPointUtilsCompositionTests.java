package IntegrationTests.PointsTests;

import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import UnitTests.PointsTests.IPointUtilsTests;

public class IPointUtilsCompositionTests extends IPointUtilsTests {

    @Override
    protected void setUpPointA(double x, double y) {
        pointA = new SkeletonPoint(x, y);
    }

    @Override
    protected void setUpPointB(double x, double y) {
        pointB = new SkeletonPoint(x, y);
    }
}
