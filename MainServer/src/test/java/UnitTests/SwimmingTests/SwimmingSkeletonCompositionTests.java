package UnitTests.SwimmingTests;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingSkeletonComposition.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SwimmingSkeleton;

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
