package DomainLogic.SwimmingErrorDetectors;

import Domain.Errors.Interfaces.SwimmingError;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;
import java.util.Map;

public interface ISwimmingErrorDetector {

    Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons);
}
