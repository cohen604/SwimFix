package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SwimmingError;
import Domain.SwimmingData.SwimmingSkeleton;

import java.util.List;

public interface SwimmingErrorDetector {

    /**
     * The function return a list of errors for that skeleton point
     * @param skeleton - the skeleton
     * @return list of points
     * @precondition skeleton is a valid skeleton
     */
    List<SwimmingError> detect(SwimmingSkeleton skeleton);

    String getTag();
}