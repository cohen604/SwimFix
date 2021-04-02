package DomainLogic.Completions;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;

public interface ISkeletonsCompletion {

    List<ISwimmingSkeleton> complete(List<ISwimmingSkeleton> skeletons);
}
