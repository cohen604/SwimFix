package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SwimmingError;
import Domain.SwimmingData.SwimmingSkeleton;

import java.util.List;

public interface SwimmingErrorDetector {

    List<SwimmingError> detect(SwimmingSkeleton skeleton);
}
