package DomainLogic.SkeletonsValueFilters.LeftElbowFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class YLeftElbowFilter implements ISkeletonValueFilter {

    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsLeftElbow()) {
            return skeleton.getLeftElbow().getY();
        }
        return 0;
    }

}
