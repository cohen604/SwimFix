package DomainLogic.SkeletonsValueFilters.RightShoulderFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class XRightShoulderFilter implements ISkeletonValueFilter {
    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsRightShoulder()) {
            return skeleton.getRightShoulder().getX();
        }
        return 0;
    }
}
