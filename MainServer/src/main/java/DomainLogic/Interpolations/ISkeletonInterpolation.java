package DomainLogic.Interpolations;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;

/**
 * This interface defines how to interpolate over list of swimming skeletons
 */
public interface ISkeletonInterpolation {

    /***
     * The function receives a list of skeletons and interpolate over them
     * @param skeletons - the skeletons
     * @return the new swimming skeletons created after the interpolation
     */
    List<ISwimmingSkeleton> interpolate(List<ISwimmingSkeleton> skeletons);

}
