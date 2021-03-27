package DomainLogic.SkeletonFilters;

import Domain.SwimmingData.ISwimmingSkeleton;

public class LeftWristFilter implements ISkeletonFilter {

    @Override
    public ISwimmingSkeleton filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsLeftWrist()) {
            return skeleton;
        }
        return null;
    }

    @Override
    public boolean check(ISwimmingSkeleton skeleton) {
        return skeleton.containsLeftWrist();
    }
}
