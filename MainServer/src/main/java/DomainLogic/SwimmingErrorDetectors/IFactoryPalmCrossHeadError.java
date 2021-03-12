package DomainLogic.SwimmingErrorDetectors;

import Domain.SwimmingData.SwimmingError;

public interface IFactoryPalmCrossHeadError {

    SwimmingError createLeft(boolean inside);
    SwimmingError createRight(boolean inside);

}
