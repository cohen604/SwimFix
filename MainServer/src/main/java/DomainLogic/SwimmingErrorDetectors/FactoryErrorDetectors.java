package DomainLogic.SwimmingErrorDetectors;

import Domain.Errors.Interfaces.IFactoryElbowError;
import Domain.Errors.Interfaces.IFactoryForearmError;
import Domain.Errors.Interfaces.IFactoryPalmCrossHeadError;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ElbowErrorDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ForearmErrorDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ISkeletonErrorDetector;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.PalmCrossHeadDetector;

import java.util.LinkedList;
import java.util.List;

public class FactoryErrorDetectors implements IFactoryErrorDetectors {

    private IFactoryElbowError factoryElbowError;
    private IFactoryPalmCrossHeadError factoryPalmCrossHeadError;
    private IFactoryForearmError factoryForearmError;

    public FactoryErrorDetectors(IFactoryElbowError factoryElbowError,
                                 IFactoryPalmCrossHeadError factoryPalmCrossHeadError,
                                 IFactoryForearmError factoryForearmError) {
        this.factoryElbowError = factoryElbowError;
        this.factoryPalmCrossHeadError = factoryPalmCrossHeadError;
        this.factoryForearmError = factoryForearmError;
    }

    @Override
    public ISwimmingTimeErrorDetector createTimeErrorDetector() {
        List<ISkeletonErrorDetector> detectors = new LinkedList<>();
        detectors.add(new ElbowErrorDetector(factoryElbowError, 90, 175));
        detectors.add(new ForearmErrorDetector(factoryForearmError, -10, 45));

        return new MaxDetector(detectors);
    }

    @Override
    public ISwimmingErrorDetector createErrorDetector() {
        List<ISkeletonErrorDetector> detectors = new LinkedList<>();
        detectors.add(new PalmCrossHeadDetector(factoryPalmCrossHeadError));
        return new RegularDetector(detectors);
    }
}
