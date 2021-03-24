package DomainLogic.SkeletonFilters;

import Domain.SwimmingData.ISwimmingSkeleton;

public interface ISkeletonFilter {

    ISwimmingSkeleton filter(ISwimmingSkeleton skeleton);

    boolean check(ISwimmingSkeleton skeleton);
}
