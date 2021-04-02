package DomainLogic.SkeletonsValueFilters.LeftWristFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class YLeftWristFilter implements ISkeletonValueFilter {

    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsLeftWrist()) {
            return skeleton.getLeftWrist().getY();
        }
        return 0;
    }
    
}
