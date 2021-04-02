package DomainLogic.SkeletonFilters;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

public interface ISkeletonFilter {

    ISwimmingSkeleton filter(ISwimmingSkeleton skeleton);

    boolean check(ISwimmingSkeleton skeleton);
}
