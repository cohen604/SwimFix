package DomainLogic.SkeletonsValueFilters.RightWristFilters;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class YRightWristFilter implements ISkeletonValueFilter {

    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsRightShoulder()) {
            return skeleton.getRightShoulder().getY();
        }
        return 0;
    }
    
}
