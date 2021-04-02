package mainServer.Providers;

import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Errors.Interfaces.SwimmingError;
import DomainLogic.SwimmingErrorDetectors.IFactoryErrorDetectors;
import DomainLogic.SwimmingErrorDetectors.ISwimmingErrorDetector;
import DomainLogic.SwimmingErrorDetectors.ISwimmingTimeErrorDetector;
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
        ISwimmingErrorDetector detector = factoryErrorDetectors.createErrorDetector();
        return detector.detect(skeletons);
    }

    @Override
    public Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons, ISwimmingPeriodTime periodTime) {
        Map<Integer, List<SwimmingError>> errorsNoTime = detect(skeletons);
        ISwimmingTimeErrorDetector detector = factoryErrorDetectors.createTimeErrorDetector();
        Map<Integer, List<SwimmingError>> errorsTime = detector.detect(skeletons, periodTime);
        //combine two maps to one map
        for(int index: errorsNoTime.keySet()) {
            if(errorsTime.containsKey(index)) {
                List<SwimmingError> errors = errorsTime.get(index);
                errors.addAll(errorsNoTime.get(index));
            }
            else {
                errorsTime.put(index, errorsNoTime.get(index));
            }
        }
        return errorsTime;
    }
}
