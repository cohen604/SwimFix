package DomainLogic.SkeletonFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

public class LeftShoulderFilter implements ISkeletonFilter {

    @Override
    public ISwimmingSkeleton filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsLeftShoulder()) {
            return skeleton;
        }
        return null;
    }

    @Override
    public boolean check(ISwimmingSkeleton skeleton) {
        return skeleton.containsLeftShoulder();
    }
}
