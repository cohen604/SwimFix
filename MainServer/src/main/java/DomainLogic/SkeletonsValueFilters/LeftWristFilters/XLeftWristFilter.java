package DomainLogic.SkeletonsValueFilters.LeftWristFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class XLeftWristFilter implements ISkeletonValueFilter {
    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsLeftWrist()) {
            return skeleton.getLeftWrist().getX();
        }
        return 0;
    }
}
