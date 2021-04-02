package DomainLogic.SkeletonsValueFilters.LeftElbowFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class XLeftElbowFilter implements ISkeletonValueFilter {
    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsLeftElbow()) {
            return skeleton.getLeftElbow().getX();
        }
        return 0;
    }
}
