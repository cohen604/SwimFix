package DomainLogic.SwimmingErrorDetectors;

import Domain.SwimmingData.SwimmingError;

public interface IFactoryForearmError {

    SwimmingError createLeft(double angle, double maxAngle, double minAngle, boolean inside);
    SwimmingError createRight(double angle, double maxAngle, double minAngle, boolean inside);

}
