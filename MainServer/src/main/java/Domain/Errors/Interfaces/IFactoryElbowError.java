package Domain.Errors.Interfaces;

import Domain.Errors.Interfaces.SwimmingError;

public interface IFactoryElbowError {

    SwimmingError createLeft(double maxAngle, double minAngle, double angle, boolean inside);
    SwimmingError createRight(double maxAngle, double minAngle, double angle, boolean inside);

}
