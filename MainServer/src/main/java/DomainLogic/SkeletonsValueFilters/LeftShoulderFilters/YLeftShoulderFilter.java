package DomainLogic.SkeletonsValueFilters.LeftShoulderFilters;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class YLeftShoulderFilter implements ISkeletonValueFilter {

    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsLeftShoulder()) {
            return skeleton.getLeftShoulder().getY();
        }
        return 0;
    }
    
}
