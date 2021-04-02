package DomainLogic.SkeletonsValueFilters.HeadFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class YHeadFilter implements ISkeletonValueFilter {
    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsHead()) {
            return skeleton.getHead().getY();
        }
        return 0;
    }
}
