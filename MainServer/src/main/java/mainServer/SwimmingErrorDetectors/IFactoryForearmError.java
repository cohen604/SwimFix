package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.SwimmingError;

public interface IFactoryForearmError {

    SwimmingError createLeft(double angle);
    SwimmingError createRight(double angle);

}
