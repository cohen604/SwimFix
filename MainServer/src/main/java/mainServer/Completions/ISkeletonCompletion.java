package mainServer.Completions;

import Domain.SwimmingData.ISwimmingSkeleton;

public interface ISkeletonCompletion {

    ISwimmingSkeleton complete(ISwimmingSkeleton skeleton);
}
