package DomainLogic.SwimmingErrorDetectors;

import Domain.Errors.Interfaces.SwimmingError;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ISkeletonErrorDetector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RegularDetector implements ISwimmingErrorDetector {

    private List<ISkeletonErrorDetector> detectors;

    public RegularDetector(List<ISkeletonErrorDetector> detectors) {
        this.detectors = detectors;
    }

    @Override
    public Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons) {
        Map<Integer, List<SwimmingError>> errorMap = new HashMap<>();
        for(int i =0; i<skeletons.size(); i++) {
            ISwimmingSkeleton skeleton = skeletons.get(i);
            List<SwimmingError> errors = new LinkedList<>();
            for(ISkeletonErrorDetector detector: this.detectors) {
                List<SwimmingError> detectorErrors = detector.detect(skeleton);
                errors.addAll(detectorErrors);
            }
            if(!errors.isEmpty()) {
                errorMap.put(i, errors);
            }
        }
        return errorMap;
    }
}
