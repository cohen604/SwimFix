package DomainLogic.SkeletonFilters;

import Domain.SwimmingData.ISwimmingSkeleton;

public class LeftFilter implements ISkeletonFilter {

    @Override
    public ISwimmingSkeleton filter(ISwimmingSkeleton skeleton) {
        if(check(skeleton)) {
            return skeleton;
        }
        return null;
    }

    @Override
    public boolean check(ISwimmingSkeleton skeleton) {
        return skeleton.containsLeftShoulder()
                && skeleton.containsLeftElbow()
                && skeleton.containsLeftWrist();
    }
}
