package Domain.Errors.Interfaces;

import Domain.Errors.Interfaces.SwimmingError;

public interface IFactoryForearmError {

    SwimmingError createLeft(double angle, double maxAngle, double minAngle, boolean inside);
    SwimmingError createRight(double angle, double maxAngle, double minAngle, boolean inside);

}
