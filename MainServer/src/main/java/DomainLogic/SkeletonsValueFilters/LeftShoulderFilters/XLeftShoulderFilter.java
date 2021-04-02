package DomainLogic.SkeletonsValueFilters.LeftShoulderFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class XLeftShoulderFilter implements ISkeletonValueFilter {
    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsLeftShoulder()) {
            return skeleton.getLeftShoulder().getX();
        }
        return 0;
    }
}
