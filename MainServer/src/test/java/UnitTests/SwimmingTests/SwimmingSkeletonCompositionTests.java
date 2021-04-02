package UnitTests.SwimmingTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SwimmingSkeleton;

public class SwimmingSkeletonCompositionTests extends SwimmingSkeletonTests {


    @Override
    public ISwimmingSkeleton getFullSwimmingSkeleton() {
        SkeletonPoint point = new SkeletonPoint(10,10);
        return new SwimmingSkeleton(point, point, point, point, point, point, point);
    }

    @Override
    public ISwimmingSkeleton getEmptySwimmingSkeleton() {
        return new SwimmingSkeleton();
    }
}
