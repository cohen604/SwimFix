package DomainLogic.SkeletonsValueFilters.LeftWristFilters;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class YLeftWristFilter implements ISkeletonValueFilter {

    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsLeftShoulder()) {
            return skeleton.getLeftWrist().getY();
        }
        return 0;
    }
    
}
