package DomainLogic.SkeletonFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

public class HeadFilter implements ISkeletonFilter {
    @Override
    public ISwimmingSkeleton filter(ISwimmingSkeleton skeleton) {
        if (check(skeleton)) {
            return skeleton;
        }
        return null;
    }

    @Override
    public boolean check(ISwimmingSkeleton skeleton) {
        return skeleton!=null && skeleton.containsHead();
    }
}