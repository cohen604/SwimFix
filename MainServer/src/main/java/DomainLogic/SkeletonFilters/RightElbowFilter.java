package DomainLogic.SkeletonFilters;

import Domain.SwimmingData.ISwimmingSkeleton;

public class RightElbowFilter implements ISkeletonFilter {

    @Override
    public ISwimmingSkeleton filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsRightElbow()) {
            return skeleton;
        }
        return null;
    }

    @Override
    public boolean check(ISwimmingSkeleton skeleton) {
        return skeleton.containsRightElbow();
    }
}
