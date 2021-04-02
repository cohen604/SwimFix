package mainServer.Providers;

import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import DomainLogic.SwimmingErrorDetectors.IFactoryErrorDetectors;
import mainServer.Providers.Interfaces.IDetectProvider;

import java.util.List;
import java.util.Map;

public class DetectProvider implements IDetectProvider {

    private IFactoryErrorDetectors factoryErrorDetectors;

    public DetectProvider(IFactoryErrorDetectors factoryErrorDetectors) {
        this.factoryErrorDetectors = factoryErrorDetectors;
    }

    @Override
    public Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons) {
        return null;
    }

    @Override
    public Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons, ISwimmingPeriodTime periodTime) {

//        new HashMap<>();
//        for(int i =0; i<skeletons.size(); i++) {
//            ISwimmingSkeleton skeleton = skeletons.get(i);
//            List<SwimmingError> errors = new LinkedList<>();
//            for(SwimmingErrorDetector detector: errorDetectors) {
//                if (detectorsNames != null && !detectorsNames.contains(detector.getTag())) {
//                    detectorsNames.add(detector.getTag());
//                }
//                List<SwimmingError> detectorErrors = detector.detect(skeleton);
//                errors.addAll(detectorErrors);
//            }
//            if(!errors.isEmpty()) {
//                errorMap.put(i, errors);
//            }
//        }
        return null;
    }
}
