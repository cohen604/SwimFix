package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.SwimmingError;

public interface IFactoryElbowError {

    SwimmingError createLeft(double angle);
    SwimmingError createRight(double angle);

}
