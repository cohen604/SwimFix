package DomainLogic.SkeletonsValueFilters.RightShoulderFilters;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class YRightShoulderFilter implements ISkeletonValueFilter {

    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsRightShoulder()) {
            return skeleton.getRightShoulder().getY();
        }
        return 0;
    }
    
}
