package DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Errors.Interfaces.SwimmingError;

import java.util.List;

public interface ISkeletonErrorDetector {

    /**
     * The function return a list of errors for that skeleton point
     * @param skeleton - the skeleton
     * @return list of points
     * @precondition skeleton is a valid skeleton
     */
    List<SwimmingError> detect(ISwimmingSkeleton skeleton);

    String getTag();
}
