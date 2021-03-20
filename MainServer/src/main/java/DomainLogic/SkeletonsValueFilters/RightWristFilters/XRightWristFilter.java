package DomainLogic.SkeletonsValueFilters.RightWristFilters;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class XRightWristFilter implements ISkeletonValueFilter {
    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsRightWrist()) {
            return skeleton.getRightWrist().getX();
        }
        return 0;
    }
}
