package mainServer.Interpolations;

import Domain.SwimmingData.ISwimmingSkeleton;

import java.util.List;

public interface ISkeletonInterpolation {

    List<ISwimmingSkeleton> interpolate(List<ISwimmingSkeleton> skeletons);

}
