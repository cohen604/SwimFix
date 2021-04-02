package DomainLogic.SkeletonsValueFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

public interface ISkeletonValueFilter {

    double filter(ISwimmingSkeleton skeleton);

}
