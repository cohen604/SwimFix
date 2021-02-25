package mainServer.Interpolations;

import Domain.SwimmingData.SwimmingSkeleton;

import java.util.List;

public interface ISkeletonInterpolation {

    List<SwimmingSkeleton> interpolate(List<SwimmingSkeleton> skeletons);

}
