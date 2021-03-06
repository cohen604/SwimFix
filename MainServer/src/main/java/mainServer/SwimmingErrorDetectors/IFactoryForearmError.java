package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.SwimmingError;

public interface IFactoryForearmError {

    SwimmingError createLeft(double angle, boolean inside);
    SwimmingError createRight(double angle, boolean inside);

}
