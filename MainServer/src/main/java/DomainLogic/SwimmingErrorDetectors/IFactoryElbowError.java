package DomainLogic.SwimmingErrorDetectors;

import Domain.SwimmingData.SwimmingError;

public interface IFactoryElbowError {

    SwimmingError createLeft(double angle, boolean inside);
    SwimmingError createRight(double angle, boolean inside);

}
