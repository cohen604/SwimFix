package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.SwimmingError;

public interface IFactoryPalmCrossHeadError {

    SwimmingError createLeft();
    SwimmingError createRight();

}
