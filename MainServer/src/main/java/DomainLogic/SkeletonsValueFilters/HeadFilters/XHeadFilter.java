package DomainLogic.SkeletonsValueFilters.HeadFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class XHeadFilter implements ISkeletonValueFilter {
    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsHead()) {
            return skeleton.getHead().getX();
        }
        return 0;
    }
}
