package DomainLogic.SkeletonsValueFilters.RightWristFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class YRightWristFilter implements ISkeletonValueFilter {

    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsRightWrist()) {
            return skeleton.getRightWrist().getY();
        }
        return 0;
    }
    
}
