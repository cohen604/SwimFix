package DomainLogic.SkeletonFilters;

import Domain.SwimmingData.ISwimmingSkeleton;

public class LeftElbowFilter implements ISkeletonFilter {

    @Override
    public ISwimmingSkeleton filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsLeftElbow()) {
            return skeleton;
        }
        return null;
    }

    @Override
    public boolean check(ISwimmingSkeleton skeleton) {
        return skeleton.containsLeftElbow();
    }
}
