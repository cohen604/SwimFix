package DomainLogic.SwimmingErrorDetectors;

import Domain.SwimmingData.SwimmingError;

public interface IFactoryElbowError {

    SwimmingError createLeft(double maxAngle, double minAngle, double angle, boolean inside);
    SwimmingError createRight(double maxAngle, double minAngle, double angle, boolean inside);

}
