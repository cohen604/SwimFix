package mainServer.Completions;

import Domain.SwimmingData.ISwimmingSkeleton;

import java.util.List;

public interface ISkeletonsCompletion {

    List<ISwimmingSkeleton> complete(List<ISwimmingSkeleton> skeletons);
}
