package DomainLogic.SkeletonsValueFilters.RightElbowFilters;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;

public class XRightElbowFilter implements ISkeletonValueFilter {
    @Override
    public double filter(ISwimmingSkeleton skeleton) {
        if(skeleton.containsRightElbow()) {
            return skeleton.getRightElbow().getX();
        }
        return 0;
    }
}
